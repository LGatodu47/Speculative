package io.github.lgatodu47.speculative.client.render;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.entity.SpeculoPigEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.PigModel;
import net.minecraft.resources.ResourceLocation;

public class SpeculoPigRenderer extends MobRenderer<SpeculoPigEntity, PigModel<SpeculoPigEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Speculative.MODID, "textures/entity/speculo_pig.png");

    public SpeculoPigRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new PigModel<>(ctx.bakeLayer(ModelLayers.PIG)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(SpeculoPigEntity entity) {
        return TEXTURE;
    }
}
