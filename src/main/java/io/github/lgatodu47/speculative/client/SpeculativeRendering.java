package io.github.lgatodu47.speculative.client;

import io.github.lgatodu47.speculative.client.screens.BossSummonerScreen;
import io.github.lgatodu47.speculative.client.screens.SpeculoosSummonerScreen;
import io.github.lgatodu47.speculative.client.render.BossSummonerTileEntityRenderer;
import io.github.lgatodu47.speculative.client.render.SpeculoPigRenderer;
import io.github.lgatodu47.speculative.client.render.SpeculoTNTRenderer;
import io.github.lgatodu47.speculative.client.screens.CentrifugeScreen;
import io.github.lgatodu47.speculative.client.screens.NuclearWorkbenchScreen;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.init.SpeculativeContainerTypes;
import io.github.lgatodu47.speculative.common.init.SpeculativeEntityTypes;
import io.github.lgatodu47.speculative.common.init.SpeculativeTileEntityTypes;
import io.github.lgatodu47.speculative.util.SpeculativeWoodTypes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public final class SpeculativeRendering {
    public static void registerBlockRenderLayers() {
        RenderTypeLookup.setRenderLayer(SpeculativeBlocks.SPECULO_TREE_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SpeculativeBlocks.SPECULO_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SpeculativeBlocks.SPECULO_WOOD_STANDING_SIGN.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SpeculativeBlocks.SPECULO_WOOD_WALL_SIGN.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SpeculativeBlocks.MANGO_BUSH.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SpeculativeBlocks.GREENSTONE_TORCH.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SpeculativeBlocks.GREENSTONE_WALL_TORCH.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SpeculativeBlocks.TOURMALINE_TREE_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SpeculativeBlocks.SPECULO_BOSS_SUMMONER.get(), RenderType.getCutout());
    }

    public static void registerContainerScreens() {
        ScreenManager.registerFactory(SpeculativeContainerTypes.SPECULOOS_SUMMONER.get(), SpeculoosSummonerScreen::new);
        ScreenManager.registerFactory(SpeculativeContainerTypes.CENTRIFUGE_CONTAINER.get(), CentrifugeScreen::new);
        ScreenManager.registerFactory(SpeculativeContainerTypes.BOSS_SUMMONER.get(), BossSummonerScreen::new);
        ScreenManager.registerFactory(SpeculativeContainerTypes.NUCLEAR_WORKBENCH.get(), NuclearWorkbenchScreen::new);
    }

    public static void registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(SpeculativeEntityTypes.SPECULO_PIG.get(), SpeculoPigRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SpeculativeEntityTypes.SPECULO_TNT.get(), SpeculoTNTRenderer::new);
    }

    public static void registerTileEntityRenderers() {
        ClientRegistry.bindTileEntityRenderer(SpeculativeTileEntityTypes.BOSS_SUMMONER.get(), BossSummonerTileEntityRenderer::new);
    }

    public static void addWoodTypes() {
        Atlases.addWoodType(SpeculativeWoodTypes.SPECULO_WOOD);
        Atlases.addWoodType(SpeculativeWoodTypes.TOURMALINE_WOOD);
    }
}
