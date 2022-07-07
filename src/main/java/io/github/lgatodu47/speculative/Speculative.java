package io.github.lgatodu47.speculative;

import io.github.lgatodu47.speculative.client.SpeculativeRendering;
import io.github.lgatodu47.speculative.client.dse.SpeculativeDimensionSpecialEffects;
import io.github.lgatodu47.speculative.common.init.*;
import io.github.lgatodu47.speculative.server.commands.DevCommand;
import io.github.lgatodu47.speculative.util.DimensionUtil;
import io.github.lgatodu47.speculative.util.SpeculativeWoodTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
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
        SpeculativeRecipeSerializers.SERIALIZERS.register(bus);
        SpeculativeRecipeSerializers.RECIPE_TYPES.register(bus);
        SpeculativeMobEffects.MOB_EFFECTS.register(bus);
        SpeculativeBlocks.BLOCKS.register(bus);
        SpeculativeFluids.FLUIDS.register(bus);
        SpeculativeItems.ITEMS.register(bus);
        SpeculativeBlockEntityTypes.BLOCK_ENTITY_TYPES.register(bus);
        SpeculativeMenuTypes.MENU_TYPES.register(bus);
        SpeculativeEntityTypes.ENTITY_TYPES.register(bus);
        SpeculativePlacerTypes.registerAll(bus);
        SpeculativeStructures.STRUCTURES.register(bus);
        SpeculativeStructures.STRUCTURE_PIECES.register(bus);
        SpeculativeBiomes.BIOMES.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DimensionUtil.registerDimensionWorldInfo(SpeculativeWorlds.SPECULO_WORLD);
            SpeculativeStructures.setupStructures();
            SpeculativeStructures.Configured.registerConfiguredStructures();
            SpeculativeBiomes.addTypes();
            SpeculativeWoodTypes.registerValidBlocks();
            SpeculativeConfiguredFeatures.init();
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(SpeculativeRendering::addWoodTypes);
        SpeculativeRendering.registerBlockRenderLayers();
        SpeculativeRendering.registerContainerScreens();
        SpeculativeDimensionSpecialEffects.registerDimensionRenderInfo();
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        DevCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        SpeculativeConfiguredFeatures.onBiomeLoad(event);
    }

    private void registerBlockItems(RegistryEvent.Register<Item> event) {
        SpeculativeBlocks.BLOCKS.getEntries()
                .stream()
                .map(RegistryObject::get)
                .filter(block -> !(block instanceof LiquidBlock || block instanceof StandingSignBlock || block instanceof WallSignBlock || block instanceof TorchBlock))
                .forEach(block -> event.getRegistry().register(new BlockItem(block, new Item.Properties().tab(tab())).setRegistryName(block.getRegistryName())));
    }

    private static final Lazy<CreativeModeTab> TAB = Lazy.of(() -> new CreativeModeTab("speculative.display_name") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(SpeculativeBlocks.SPECULO_ORE.get());
        }
    });

    public static CreativeModeTab tab() {
        return TAB.get();
    }
}
