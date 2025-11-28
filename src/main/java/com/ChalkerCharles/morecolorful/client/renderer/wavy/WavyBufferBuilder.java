package com.ChalkerCharles.morecolorful.client.renderer.wavy;

import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Vec3i;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@OnlyIn(Dist.CLIENT)
public class WavyBufferBuilder extends BufferBuilder {
    private final WavyVertices.Default vertices;
    private int index;

    public WavyBufferBuilder(ByteBufferBuilder pBuffer, VertexFormat.Mode pMode, VertexFormat pFormat, WavyVertices.Default vertices) {
        super(pBuffer, pMode, pFormat);
        this.vertices = vertices;
    }

    @Override
    public void putBulkData(PoseStack.Pose pPose, BakedQuad pQuad, float[] pBrightness, float pRed, float pGreen, float pBlue, float pAlpha, int[] pLightmap, int pPackedOverlay, boolean pReadAlpha) {
        int[] vertices = pQuad.getVertices();
        Vec3i vec3i = pQuad.getDirection().getNormal();
        Matrix4f matrix4f = pPose.pose();
        Vector3f vector3f = pPose.transformNormal(vec3i.getX(), vec3i.getY(), vec3i.getZ(), new Vector3f());
        int j = vertices.length >> 3;
        int k = (int)(pAlpha * 255.0F);

        try (MemoryStack memorystack = MemoryStack.stackPush()) {
            ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormat.BLOCK.getVertexSize());
            IntBuffer intbuffer = bytebuffer.asIntBuffer();

            for (int l = 0; l < j; l++) {
                intbuffer.clear();
                intbuffer.put(vertices, l << 3, 8);
                float f = bytebuffer.getFloat(0);
                float f1 = bytebuffer.getFloat(4);
                float f2 = bytebuffer.getFloat(8);
                float f3;
                float f4;
                float f5;
                if (pReadAlpha) {
                    float f6 = (float)(bytebuffer.get(12) & 255);
                    float f7 = (float)(bytebuffer.get(13) & 255);
                    float f8 = (float)(bytebuffer.get(14) & 255);
                    f3 = f6 * pBrightness[l] * pRed;
                    f4 = f7 * pBrightness[l] * pGreen;
                    f5 = f8 * pBrightness[l] * pBlue;
                } else {
                    f3 = pBrightness[l] * pRed * 255.0F;
                    f4 = pBrightness[l] * pGreen * 255.0F;
                    f5 = pBrightness[l] * pBlue * 255.0F;
                }

                int vertexAlpha = pReadAlpha ? (int)((pAlpha * (float) (bytebuffer.get(15) & 255) / 255.0F) * 255) : k;
                int i1 = FastColor.ARGB32.color(vertexAlpha, (int)f3, (int)f4, (int)f5);
                int j1 = applyBakedLighting(pLightmap[l], bytebuffer);
                float f10 = bytebuffer.getFloat(16);
                float f9 = bytebuffer.getFloat(20);
                Vector3f vector3f1 = matrix4f.transformPosition(f, f1, f2, new Vector3f());
                applyBakedNormals(vector3f, bytebuffer, pPose.normal());
                float pX = vector3f1.x, pY = vector3f1.y, pZ = vector3f1.z;
                long wave = RenderUtils.getWaveData(f, f1, f2);
                if (wave != 0L) {
                    this.vertices.addVertex(index, wave, pX, pY, pZ);
                }
                this.index += 32;
                this.addVertex(pX, pY, pZ, i1, f10, f9, pPackedOverlay, j1, vector3f.x, vector3f.y, vector3f.z);
            }
        }
    }

    @Override
    public VertexConsumer addVertex(float pX, float pY, float pZ) {
        long wave = RenderUtils.getFluidWaveData(pX, pY, pZ);
        if (wave != 0L) {
            this.vertices.addVertex(index, wave, pX, pY, pZ);
        }
        this.index += 32;
        return super.addVertex(pX, pY, pZ);
    }
}
