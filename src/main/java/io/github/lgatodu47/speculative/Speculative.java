package io.github.lgatodu47.speculative;

import io.github.lgatodu47.speculative.client.SpeculativeRendering;
import io.github.lgatodu47.speculative.client.dri.SpeculativeDimensionRenderInfo;
import io.github.lgatodu47.speculative.common.event.SpeculativeBiomeLoading;
import io.github.lgatodu47.speculative.common.init.*;
import io.github.lgatodu47.speculative.server.commands.DevCommand;
import io.github.lgatodu47.speculative.util.DimensionUtil;
import io.github.lgatodu47.speculative.util.SpeculativeWoodTypes;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Speculative.MODID)
public class Speculative {
    public static final String MODID = "speculative";
    public static final Logger LOGGER = LogManager.getLogger();

    public Speculative() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::doClientStuff);
        bus.addGenericListener(Item.class, this::registerBlockItems);

        SpeculativeSounds.SOUNDS.register(bus);
        SpeculativeParticleTypes.PARTICLE_TYPES.register(bus);
        SpeculativeRecipeSerializers.RECIPE_SERIALIZER.register(bus);
        SpeculativeEffects.EFFECTS.register(bus);
        SpeculativeBlocks.BLOCKS.register(bus);
        SpeculativeFluids.FLUIDS.register(bus);
        SpeculativeItems.ITEMS.register(bus);
        SpeculativeTileEntityTypes.TILE_ENTITY_TYPES.register(bus);
        SpeculativeContainerTypes.CONTAINER_TYPES.register(bus);
        SpeculativeEntityTypes.ENTITY_TYPES.register(bus);
        SpeculativePlacerTypes.registerAll(bus);
        SpeculativeStructures.STRUCTURES.register(bus);
        SpeculativeBiomes.BIOMES.register(bus);
        SpeculativeWorldTypes.WORLD_TYPES.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SpeculativePlacerTypes.Trunk.init();
            DimensionUtil.registerDimensionWorldInfo(SpeculativeWorlds.SPECULO_WORLD);
            SpeculativeStructures.setupStructures();
            SpeculativeStructures.Configured.registerConfiguredStructures();
            SpeculativeBiomes.addTypes();
            SpeculativeWoodTypes.registerValidBlocks();
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(SpeculativeRendering::addWoodTypes);
        SpeculativeRendering.registerBlockRenderLayers();
        SpeculativeRendering.registerContainerScreens();
        SpeculativeRendering.registerEntityRenderers();
        SpeculativeRendering.registerTileEntityRenderers();
        SpeculativeDimensionRenderInfo.registerDimensionRenderInfo();
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        DevCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        SpeculativeBiomeLoading.addFeatures(event);
    }

    private void registerBlockItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        SpeculativeBlocks.BLOCKS.getEntries()
                .stream()
                .map(RegistryObject::get)
                .filter(block -> !(block instanceof FlowingFluidBlock || block instanceof StandingSignBlock || block instanceof WallSignBlock || block instanceof TorchBlock))
                .forEach(block -> registry.register(new BlockItem(block, new Item.Properties().group(tab())).setRegistryName(block.getRegistryName())));
    }

    private static final Lazy<ItemGroup> GROUP = Lazy.of(() -> new ItemGroup("speculative.display_name") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(SpeculativeBlocks.SPECULO_ORE.get());
        }
    });

    public static ItemGroup tab() {
        return GROUP.get();
    }
}
