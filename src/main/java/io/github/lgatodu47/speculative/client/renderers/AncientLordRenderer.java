package io.github.lgatodu47.speculative.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.client.models.SpeculativeModelLayers;
import io.github.lgatodu47.speculative.common.entity.AncientLordEntity;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AncientLordRenderer extends MobRenderer<AncientLordEntity, VillagerModel<AncientLordEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Speculative.MODID, "textures/entity/ancient_lord");

    public AncientLordRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new VillagerModel<>(SpeculativeModelLayers.ANCIENT_LORD.bake(ctx)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(AncientLordEntity pEntity) {
        return TEXTURE;
    }

    @Override
    protected void scale(AncientLordEntity pLivingEntity, PoseStack stack, float pPartialTickTime) {
        float f = 0.9375F;
        stack.scale(f, f, f);
    }
}
