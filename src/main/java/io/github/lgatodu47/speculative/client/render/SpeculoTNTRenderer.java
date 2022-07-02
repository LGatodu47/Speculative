package io.github.lgatodu47.speculative.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.entity.SpeculoTNTEntity;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TNTMinecartRenderer;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class SpeculoTNTRenderer extends EntityRenderer<SpeculoTNTEntity> {
    public SpeculoTNTRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0.5f;
    }

    @Override
    public void render(SpeculoTNTEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.5D, 0.0D);
        if ((float) entityIn.getFuse() - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float) entityIn.getFuse() - partialTicks + 1.0F) / 10.0F;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            f = f * f;
            f = f * f;
            float scaleFactor = 1.0F + f * 0.3F;
            matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
        }

        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-90.0F));
        matrixStackIn.translate(-0.5D, -0.5D, 0.5D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));

        TNTMinecartRenderer.renderTntFlash(SpeculativeBlocks.SPECULO_TNT.get().getDefaultState(), matrixStackIn, bufferIn, packedLightIn, entityIn.getFuse() / 5 % 2 == 0);

        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(SpeculoTNTEntity entity) {
        return PlayerContainer.LOCATION_BLOCKS_TEXTURE;
    }
}
