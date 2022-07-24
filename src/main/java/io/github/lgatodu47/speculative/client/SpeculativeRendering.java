package io.github.lgatodu47.speculative.client;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.client.models.SpeculativeModelLayers;
import io.github.lgatodu47.speculative.client.renderers.*;
import io.github.lgatodu47.speculative.client.screens.BossSummonerScreen;
import io.github.lgatodu47.speculative.client.screens.CentrifugeScreen;
import io.github.lgatodu47.speculative.client.screens.NuclearWorkbenchScreen;
import io.github.lgatodu47.speculative.client.screens.SpeculoosSummonerScreen;
import io.github.lgatodu47.speculative.common.init.*;
import io.github.lgatodu47.speculative.util.SpeculativeBoatType;
import io.github.lgatodu47.speculative.util.SpeculativeWoodTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = Speculative.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SpeculativeRendering {
    public static void registerBlockRenderLayers() {
        setRenderLayers(RenderType.cutout(),
                SpeculativeBlocks.SPECULO_FLOWER,
                SpeculativeBlocks.MANGO_BUSH,
                SpeculativeBlocks.SPECULO_TREE_SAPLING,
                SpeculativeBlocks.TOURMALINE_TREE_SAPLING,
                SpeculativeBlocks.POTTED_SPECULO_FLOWER,
                SpeculativeBlocks.POTTED_SPECULO_TREE_SAPLING,
                SpeculativeBlocks.POTTED_TOURMALINE_TREE_SAPLING,

                SpeculativeBlocks.SPECULO_WOOD_STANDING_SIGN,
                SpeculativeBlocks.SPECULO_WOOD_WALL_SIGN,
                SpeculativeBlocks.SPECULO_WOOD_DOOR,
                SpeculativeBlocks.SPECULO_WOOD_TRAPDOOR,

                SpeculativeBlocks.TOURMALINE_WOOD_STANDING_SIGN,
                SpeculativeBlocks.TOURMALINE_WOOD_WALL_SIGN,
                SpeculativeBlocks.TOURMALINE_WOOD_DOOR,
                SpeculativeBlocks.TOURMALINE_WOOD_TRAPDOOR,

                SpeculativeBlocks.GREENSTONE_TORCH,
                SpeculativeBlocks.GREENSTONE_WALL_TORCH,
                SpeculativeBlocks.GREENSTONE_LANTERN,
                SpeculativeBlocks.SPECULO_BOSS_SUMMONER
        );
    }

    @SafeVarargs
    private static void setRenderLayers(RenderType type, RegistryObject<Block>... blocks) {
        Arrays.stream(blocks).map(RegistryObject::get).forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, type));
    }

    public static void registerContainerScreens() {
        MenuScreens.register(SpeculativeMenuTypes.SPECULOOS_SUMMONER.get(), SpeculoosSummonerScreen::new);
        MenuScreens.register(SpeculativeMenuTypes.CENTRIFUGE_CONTAINER.get(), CentrifugeScreen::new);
        MenuScreens.register(SpeculativeMenuTypes.BOSS_SUMMONER.get(), BossSummonerScreen::new);
        MenuScreens.register(SpeculativeMenuTypes.NUCLEAR_WORKBENCH.get(), NuclearWorkbenchScreen::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        registerEntityRenderers(event::registerEntityRenderer);
        registerBlockEntityRenderers(event::registerBlockEntityRenderer);
    }

    private static void registerEntityRenderers(EntityRendererRegistry registry) {
        registry.register(SpeculativeEntityTypes.SPECULO_PIG.get(), SpeculoPigRenderer::new);
        registry.register(SpeculativeEntityTypes.SPECULO_TNT.get(), SpeculoTNTRenderer::new);
        registry.register(SpeculativeEntityTypes.SPECULATIVE_BOAT.get(), SpeculativeBoatRenderer::new);
        registry.register(SpeculativeEntityTypes.ANCIENT_LORD.get(), AncientLordRenderer::new);
    }

    private static void registerBlockEntityRenderers(BlockEntityRendererRegistry registry) {
        registry.register(SpeculativeBlockEntityTypes.BOSS_SUMMONER.get(), BossSummonerBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        LayerDefinition boatLayer = BoatModel.createBodyModel();

        for(ResourceLocation id : SpeculativeBoatType.names()) {
            event.registerLayerDefinition(new ModelLayerLocation(new ResourceLocation(id.getNamespace(), "boat/" + id.getPath()), "main"), () -> boatLayer);
        }

        SpeculativeModelLayers.registerModelLayers(event);
    }

    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(SpeculativeParticleTypes.GREEN_FLAME.get(), FlameParticle.Provider::new);
    }

    public static void addWoodTypes() {
        Sheets.addWoodType(SpeculativeWoodTypes.SPECULO_WOOD);
        Sheets.addWoodType(SpeculativeWoodTypes.TOURMALINE_WOOD);
    }

    @FunctionalInterface
    private interface EntityRendererRegistry {
        <T extends Entity> void register(EntityType<? extends T> type, EntityRendererProvider<T> provider);
    }

    @FunctionalInterface
    private interface BlockEntityRendererRegistry {
        <T extends BlockEntity> void register(BlockEntityType<? extends T> type, BlockEntityRendererProvider<T> renderer);
    }
}
