package com.ChalkerCharles.morecolorful.client.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemGlintVertexConsumer implements VertexConsumer {
    private final VertexConsumer first;
    private final VertexConsumer second;
    private boolean renderGlint;

    public ItemGlintVertexConsumer(VertexConsumer first, VertexConsumer second) {
        if (first == second) {
            throw new IllegalArgumentException("Duplicate delegates");
        } else {
            this.first = first;
            this.second = second;
        }
    }

    public void setRenderGlint(boolean glint) {
        this.renderGlint = glint;
    }

    @Override
    public VertexConsumer addVertex(float pX, float pY, float pZ) {
        this.first.addVertex(pX, pY, pZ);
        this.second.addVertex(pX, pY, pZ);
        return this;
    }

    @Override
    public VertexConsumer setColor(int pRed, int pGreen, int pBlue, int pAlpha) {
        this.first.setColor(pRed, pGreen, pBlue, pAlpha);
        this.second.setColor(pRed, pGreen, pBlue, pAlpha);
        return this;
    }

    @Override
    public VertexConsumer setUv(float pU, float pV) {
        this.first.setUv(pU, pV);
        this.second.setUv(pU, pV);
        return this;
    }

    @Override
    public VertexConsumer setUv1(int pU, int pV) {
        this.first.setUv1(pU, pV);
        this.second.setUv1(pU, pV);
        return this;
    }

    @Override
    public VertexConsumer setUv2(int pU, int pV) {
        this.first.setUv2(pU, pV);
        this.second.setUv2(pU, pV);
        return this;
    }

    @Override
    public VertexConsumer setNormal(float pNormalX, float pNormalY, float pNormalZ) {
        this.first.setNormal(pNormalX, pNormalY, pNormalZ);
        this.second.setNormal(pNormalX, pNormalY, pNormalZ);
        return this;
    }

    @Override
    public void addVertex(
            float pX,
            float pY,
            float pZ,
            int pColor,
            float pU,
            float pV,
            int pPackedOverlay,
            int pPackedLight,
            float pNormalX,
            float pNormalY,
            float pNormalZ) {
        if (this.renderGlint) {
            this.first.addVertex(pX, pY, pZ, pColor, pU, pV, pPackedOverlay, pPackedLight, pNormalX, pNormalY, pNormalZ);
        }
        this.second.addVertex(pX, pY, pZ, pColor, pU, pV, pPackedOverlay, pPackedLight, pNormalX, pNormalY, pNormalZ);
    }
}
