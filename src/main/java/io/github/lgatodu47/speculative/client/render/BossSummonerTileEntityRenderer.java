package io.github.lgatodu47.speculative.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.lgatodu47.speculative.common.tile.BossSummonerTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.BeaconTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.BeaconTileEntity;

import java.util.List;

public class BossSummonerTileEntityRenderer extends TileEntityRenderer<BossSummonerTileEntity> {
    public BossSummonerTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(BossSummonerTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        long time = tileEntityIn.getWorld().getGameTime();
        List<BossSummonerTileEntity.ExtendedBeamSegment> list = tileEntityIn.getBeamSegments();
        int totalHeight = 0;

        for (int i = 0; i < list.size(); ++i) {
            BeaconTileEntity.BeamSegment beam = list.get(i);
            BeaconTileEntityRenderer.renderBeamSegment(matrixStackIn, bufferIn, BeaconTileEntityRenderer.TEXTURE_BEACON_BEAM, partialTicks, 1.0f, time, totalHeight, i == list.size() - 1 ? 1024 : beam.getHeight(), beam.getColors(), 0.2F, 0.25F);
            totalHeight += beam.getHeight();
        }
    }
}
