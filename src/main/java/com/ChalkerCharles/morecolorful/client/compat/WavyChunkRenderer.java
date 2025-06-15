package com.ChalkerCharles.morecolorful.client.compat;

import com.mojang.blaze3d.systems.RenderSystem;
import net.caffeinemc.mods.sodium.client.SodiumClientMod;
import net.caffeinemc.mods.sodium.client.gl.device.CommandList;
import net.caffeinemc.mods.sodium.client.gl.device.DrawCommandList;
import net.caffeinemc.mods.sodium.client.gl.device.MultiDrawBatch;
import net.caffeinemc.mods.sodium.client.gl.device.RenderDevice;
import net.caffeinemc.mods.sodium.client.gl.tessellation.GlIndexType;
import net.caffeinemc.mods.sodium.client.gl.tessellation.GlPrimitiveType;
import net.caffeinemc.mods.sodium.client.gl.tessellation.GlTessellation;
import net.caffeinemc.mods.sodium.client.gl.tessellation.TessellationBinding;
import net.caffeinemc.mods.sodium.client.model.quad.properties.ModelQuadFacing;
import net.caffeinemc.mods.sodium.client.render.chunk.ChunkRenderMatrices;
import net.caffeinemc.mods.sodium.client.render.chunk.LocalSectionIndex;
import net.caffeinemc.mods.sodium.client.render.chunk.SharedQuadIndexBuffer;
import net.caffeinemc.mods.sodium.client.render.chunk.data.SectionRenderDataStorage;
import net.caffeinemc.mods.sodium.client.render.chunk.data.SectionRenderDataUnsafe;
import net.caffeinemc.mods.sodium.client.render.chunk.lists.ChunkRenderList;
import net.caffeinemc.mods.sodium.client.render.chunk.lists.ChunkRenderListIterable;
import net.caffeinemc.mods.sodium.client.render.chunk.region.RenderRegion;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderInterface;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.translucent_sorting.SortBehavior;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexType;
import net.caffeinemc.mods.sodium.client.render.viewport.CameraTransform;
import net.caffeinemc.mods.sodium.client.util.BitwiseMath;
import net.caffeinemc.mods.sodium.client.util.UInt32;
import net.caffeinemc.mods.sodium.client.util.iterator.ByteIterator;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Pointer;

import java.util.Iterator;

@OnlyIn(Dist.CLIENT)
public class WavyChunkRenderer extends WavyShaderChunkRenderer{
    private final MultiDrawBatch batch;
    private final SharedQuadIndexBuffer sharedIndexBuffer;
    private static final int MODEL_UNASSIGNED = ModelQuadFacing.UNASSIGNED.ordinal();
    private static final int MODEL_POS_X = ModelQuadFacing.POS_X.ordinal();
    private static final int MODEL_POS_Y = ModelQuadFacing.POS_Y.ordinal();
    private static final int MODEL_POS_Z = ModelQuadFacing.POS_Z.ordinal();
    private static final int MODEL_NEG_X = ModelQuadFacing.NEG_X.ordinal();
    private static final int MODEL_NEG_Y = ModelQuadFacing.NEG_Y.ordinal();
    private static final int MODEL_NEG_Z = ModelQuadFacing.NEG_Z.ordinal();

    public WavyChunkRenderer(RenderDevice device, ChunkVertexType vertexType) {
        super(device, vertexType);
        this.batch = new MultiDrawBatch(ModelQuadFacing.COUNT * 256 + 1);
        this.sharedIndexBuffer = new SharedQuadIndexBuffer(device.createCommandList(), SharedQuadIndexBuffer.IndexType.INTEGER);
    }

    @Override
    public void render(ChunkRenderMatrices matrices, CommandList commandList, ChunkRenderListIterable renderLists, TerrainRenderPass renderPass, CameraTransform camera) {
        super.begin(renderPass);
        boolean useBlockFaceCulling = SodiumClientMod.options().performance.useBlockFaceCulling;
        boolean useIndexedTessellation = isTranslucentRenderPass(renderPass);
        ChunkShaderInterface shader = this.activeProgram.getInterface();
        shader.setProjectionMatrix(matrices.projection());
        shader.setModelViewMatrix(matrices.modelView());
        ((WavyShaderInterface) shader).gameTime.set(RenderSystem.getShaderGameTime());
        Iterator<ChunkRenderList> iterator = renderLists.iterator(renderPass.isTranslucent());

        while (iterator.hasNext()) {
            ChunkRenderList renderList = iterator.next();
            RenderRegion region = renderList.getRegion();
            SectionRenderDataStorage storage = region.getStorage(renderPass);
            if (storage != null) {
                fillCommandBuffer(this.batch, region, storage, renderList, camera, renderPass, useBlockFaceCulling);
                if (!this.batch.isEmpty()) {
                    if (!useIndexedTessellation) {
                        this.sharedIndexBuffer.ensureCapacity(commandList, this.batch.getIndexBufferSize());
                    }

                    GlTessellation tessellation;
                    if (useIndexedTessellation) {
                        tessellation = this.prepareIndexedTessellation(commandList, region);
                    } else {
                        tessellation = this.prepareTessellation(commandList, region);
                    }

                    setModelMatrixUniforms(shader, region, camera);
                    executeDrawBatch(commandList, tessellation, this.batch);
                }
            }
        }

        super.end(renderPass);
    }

    @SuppressWarnings("removal")
    private static boolean isTranslucentRenderPass(TerrainRenderPass renderPass) {
        return renderPass.isTranslucent() && SodiumClientMod.options().debug.getSortBehavior() != SortBehavior.OFF;
    }

    private static void fillCommandBuffer(MultiDrawBatch batch, RenderRegion renderRegion, SectionRenderDataStorage renderDataStorage, ChunkRenderList renderList, CameraTransform camera, TerrainRenderPass pass, boolean useBlockFaceCulling) {
        batch.clear();
        ByteIterator iterator = renderList.sectionsWithGeometryIterator(pass.isTranslucent());
        if (iterator != null) {
            int originX = renderRegion.getChunkX();
            int originY = renderRegion.getChunkY();
            int originZ = renderRegion.getChunkZ();

            while(iterator.hasNext()) {
                int sectionIndex = iterator.nextByteAsInt();
                long pMeshData = renderDataStorage.getDataPointer(sectionIndex);
                int chunkX = originX + LocalSectionIndex.unpackX(sectionIndex);
                int chunkY = originY + LocalSectionIndex.unpackY(sectionIndex);
                int chunkZ = originZ + LocalSectionIndex.unpackZ(sectionIndex);
                int slices;
                if (useBlockFaceCulling) {
                    slices = getVisibleFaces(camera.intX, camera.intY, camera.intZ, chunkX, chunkY, chunkZ);
                } else {
                    slices = ModelQuadFacing.ALL;
                }

                slices &= SectionRenderDataUnsafe.getSliceMask(pMeshData);
                if (slices != 0) {
                    if (pass.isTranslucent()) {
                        addIndexedDrawCommands(batch, pMeshData, slices);
                    } else {
                        addNonIndexedDrawCommands(batch, pMeshData, slices);
                    }
                }
            }

        }
    }

    private static void addNonIndexedDrawCommands(MultiDrawBatch batch, long pMeshData, int mask) {
        long pElementPointer = batch.pElementPointer;
        long pBaseVertex = batch.pBaseVertex;
        long pElementCount = batch.pElementCount;
        int size = batch.size;

        for(int facing = 0; facing < ModelQuadFacing.COUNT; ++facing) {
            MemoryUtil.memPutInt(pBaseVertex + ((long) size << 2), (int)SectionRenderDataUnsafe.getVertexOffset(pMeshData, facing));
            MemoryUtil.memPutInt(pElementCount + ((long) size << 2), (int)SectionRenderDataUnsafe.getElementCount(pMeshData, facing));
            MemoryUtil.memPutAddress(pElementPointer + ((long) size << Pointer.POINTER_SHIFT), 0L);
            size += mask >> facing & 1;
        }

        batch.size = size;
    }

    private static void addIndexedDrawCommands(MultiDrawBatch batch, long pMeshData, int mask) {
        long pElementPointer = batch.pElementPointer;
        long pBaseVertex = batch.pBaseVertex;
        long pElementCount = batch.pElementCount;
        int size = batch.size;
        long elementOffset = SectionRenderDataUnsafe.getBaseElement(pMeshData);

        for(int facing = 0; facing < ModelQuadFacing.COUNT; ++facing) {
            long vertexOffset = SectionRenderDataUnsafe.getVertexOffset(pMeshData, facing);
            long elementCount = SectionRenderDataUnsafe.getElementCount(pMeshData, facing);
            MemoryUtil.memPutInt(pBaseVertex + ((long) size << 2), UInt32.uncheckedDowncast(vertexOffset));
            MemoryUtil.memPutInt(pElementCount + ((long) size << 2), UInt32.uncheckedDowncast(elementCount));
            MemoryUtil.memPutAddress(pElementPointer + ((long) size << Pointer.POINTER_SHIFT), elementOffset << 2);
            elementOffset += elementCount;
            size += mask >> facing & 1;
        }

        batch.size = size;
    }

    private static int getVisibleFaces(int originX, int originY, int originZ, int chunkX, int chunkY, int chunkZ) {
        int boundsMinX = chunkX << 4;
        int boundsMaxX = boundsMinX + 16;
        int boundsMinY = chunkY << 4;
        int boundsMaxY = boundsMinY + 16;
        int boundsMinZ = chunkZ << 4;
        int boundsMaxZ = boundsMinZ + 16;
        int planes = 1 << MODEL_UNASSIGNED;
        planes |= BitwiseMath.greaterThan(originX, boundsMinX - 3) << MODEL_POS_X;
        planes |= BitwiseMath.greaterThan(originY, boundsMinY - 3) << MODEL_POS_Y;
        planes |= BitwiseMath.greaterThan(originZ, boundsMinZ - 3) << MODEL_POS_Z;
        planes |= BitwiseMath.lessThan(originX, boundsMaxX + 3) << MODEL_NEG_X;
        planes |= BitwiseMath.lessThan(originY, boundsMaxY + 3) << MODEL_NEG_Y;
        planes |= BitwiseMath.lessThan(originZ, boundsMaxZ + 3) << MODEL_NEG_Z;
        return planes;
    }

    private static void setModelMatrixUniforms(ChunkShaderInterface shader, RenderRegion region, CameraTransform camera) {
        float x = getCameraTranslation(region.getOriginX(), camera.intX, camera.fracX);
        float y = getCameraTranslation(region.getOriginY(), camera.intY, camera.fracY);
        float z = getCameraTranslation(region.getOriginZ(), camera.intZ, camera.fracZ);
        shader.setRegionOffset(x, y, z);
    }

    private static float getCameraTranslation(int chunkBlockPos, int cameraBlockPos, float cameraPos) {
        return (float)(chunkBlockPos - cameraBlockPos) - cameraPos;
    }

    private GlTessellation prepareTessellation(CommandList commandList, RenderRegion region) {
        RenderRegion.DeviceResources resources = region.getResources();
        GlTessellation tessellation = resources.getTessellation();
        if (tessellation == null) {
            tessellation = this.createRegionTessellation(commandList, resources, true);
            resources.updateTessellation(commandList, tessellation);
        }

        return tessellation;
    }

    private GlTessellation prepareIndexedTessellation(CommandList commandList, RenderRegion region) {
        RenderRegion.DeviceResources resources = region.getResources();
        GlTessellation tessellation = resources.getIndexedTessellation();
        if (tessellation == null) {
            tessellation = this.createRegionTessellation(commandList, resources, false);
            resources.updateIndexedTessellation(commandList, tessellation);
        }

        return tessellation;
    }

    private GlTessellation createRegionTessellation(CommandList commandList, RenderRegion.DeviceResources resources, boolean useSharedIndexBuffer) {
        return commandList.createTessellation(GlPrimitiveType.TRIANGLES, new TessellationBinding[]{
                TessellationBinding.forVertexBuffer(resources.getGeometryBuffer(), this.vertexFormat.getShaderBindings()),
                TessellationBinding.forElementBuffer(useSharedIndexBuffer ? this.sharedIndexBuffer.getBufferObject() : resources.getIndexBuffer())
        });
    }

    private static void executeDrawBatch(CommandList commandList, GlTessellation tessellation, MultiDrawBatch batch) {
        try (DrawCommandList drawCommandList = commandList.beginTessellating(tessellation)) {
            drawCommandList.multiDrawElementsBaseVertex(batch, GlIndexType.UNSIGNED_INT);
        }

    }

    @Override
    public void delete(CommandList commandList) {
        super.delete(commandList);
        this.sharedIndexBuffer.delete(commandList);
        this.batch.delete();
    }
}
