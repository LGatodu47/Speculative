package io.github.lgatodu47.speculative.client.render;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.entity.SpeculoPigEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.util.ResourceLocation;

public class SpeculoPigRenderer extends MobRenderer<SpeculoPigEntity, PigModel<SpeculoPigEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Speculative.MODID, "textures/entity/speculo_pig.png");

    public SpeculoPigRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PigModel<>(), 0.5F);
    }

    @Override
    public ResourceLocation getEntityTexture(SpeculoPigEntity entity) {
        return TEXTURE;
    }
}
