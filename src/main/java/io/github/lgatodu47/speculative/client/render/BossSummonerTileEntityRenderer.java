package io.github.lgatodu47.speculative.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.lgatodu47.speculative.common.block.entity.BossSummonerBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;

import java.util.List;

public class BossSummonerTileEntityRenderer implements BlockEntityRenderer<BossSummonerBlockEntity> {
    public BossSummonerTileEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(BossSummonerBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        long time = tileEntityIn.getLevel().getGameTime();
        List<BossSummonerBlockEntity.ExtendedBeamSegment> list = tileEntityIn.getBeamSegments();
        int totalHeight = 0;

        for (int i = 0; i < list.size(); ++i) {
            BeaconBlockEntity.BeaconBeamSection beam = list.get(i);
            BeaconRenderer.renderBeaconBeam(matrixStackIn, bufferIn, BeaconRenderer.BEAM_LOCATION, partialTicks, 1.0f, time, totalHeight, i == list.size() - 1 ? 1024 : beam.getHeight(), beam.getColor(), 0.2F, 0.25F);
            totalHeight += beam.getHeight();
        }
    }
}
