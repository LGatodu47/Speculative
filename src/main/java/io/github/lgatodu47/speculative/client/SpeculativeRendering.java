package io.github.lgatodu47.speculative.client;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.client.render.BossSummonerTileEntityRenderer;
import io.github.lgatodu47.speculative.client.render.SpeculoPigRenderer;
import io.github.lgatodu47.speculative.client.render.SpeculoTNTRenderer;
import io.github.lgatodu47.speculative.client.screens.BossSummonerScreen;
import io.github.lgatodu47.speculative.client.screens.CentrifugeScreen;
import io.github.lgatodu47.speculative.client.screens.NuclearWorkbenchScreen;
import io.github.lgatodu47.speculative.client.screens.SpeculoosSummonerScreen;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.init.SpeculativeMenuTypes;
import io.github.lgatodu47.speculative.common.init.SpeculativeEntityTypes;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlockEntityTypes;
import io.github.lgatodu47.speculative.util.SpeculativeWoodTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Speculative.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SpeculativeRendering {
    public static void registerBlockRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(SpeculativeBlocks.SPECULO_TREE_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SpeculativeBlocks.SPECULO_FLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SpeculativeBlocks.SPECULO_WOOD_STANDING_SIGN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SpeculativeBlocks.SPECULO_WOOD_WALL_SIGN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SpeculativeBlocks.MANGO_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SpeculativeBlocks.GREENSTONE_TORCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SpeculativeBlocks.GREENSTONE_WALL_TORCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SpeculativeBlocks.TOURMALINE_TREE_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SpeculativeBlocks.SPECULO_BOSS_SUMMONER.get(), RenderType.cutout());
    }

    public static void registerContainerScreens() {
        MenuScreens.register(SpeculativeMenuTypes.SPECULOOS_SUMMONER.get(), SpeculoosSummonerScreen::new);
        MenuScreens.register(SpeculativeMenuTypes.CENTRIFUGE_CONTAINER.get(), CentrifugeScreen::new);
        MenuScreens.register(SpeculativeMenuTypes.BOSS_SUMMONER.get(), BossSummonerScreen::new);
        MenuScreens.register(SpeculativeMenuTypes.NUCLEAR_WORKBENCH.get(), NuclearWorkbenchScreen::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        registerEntityRenderers(event);
        registerBlockEntityRenderers(event);
    }

    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SpeculativeEntityTypes.SPECULO_PIG.get(), SpeculoPigRenderer::new);
        event.registerEntityRenderer(SpeculativeEntityTypes.SPECULO_TNT.get(), SpeculoTNTRenderer::new);
    }

    private static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(SpeculativeBlockEntityTypes.BOSS_SUMMONER.get(), BossSummonerTileEntityRenderer::new);
    }

    public static void addWoodTypes() {
        Sheets.addWoodType(SpeculativeWoodTypes.SPECULO_WOOD);
        Sheets.addWoodType(SpeculativeWoodTypes.TOURMALINE_WOOD);
    }
}
