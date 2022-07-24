package io.github.lgatodu47.speculative;

import io.github.lgatodu47.speculative.client.SpeculativeClientProxy;
import io.github.lgatodu47.speculative.client.SpeculativeRendering;
import io.github.lgatodu47.speculative.client.dse.SpeculativeDimensionSpecialEffects;
import io.github.lgatodu47.speculative.common.SpeculativeCommonProxy;
import io.github.lgatodu47.speculative.common.init.*;
import io.github.lgatodu47.speculative.common.item.SpeculativeBoatItem;
import io.github.lgatodu47.speculative.data.DataGenerationContext;
import io.github.lgatodu47.speculative.data.loot.SpeculativeLootTableProvider;
import io.github.lgatodu47.speculative.data.tags.SpeculativeBlockTagsProvider;
import io.github.lgatodu47.speculative.data.tags.SpeculativeItemTagsProvider;
import io.github.lgatodu47.speculative.server.SpeculativeServerProxy;
import io.github.lgatodu47.speculative.server.commands.SpeculativeCommand;
import io.github.lgatodu47.speculative.util.DimensionUtil;
import io.github.lgatodu47.speculative.util.SpeculativeWoodTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Speculative.MODID)
public class Speculative {
    public static final String MODID = "speculative";
    public static final Logger LOGGER = LogManager.getLogger();
    private static Speculative instance;

    private final SpeculativeCommonProxy proxy;

    public Speculative() {
        this.proxy = DistExecutor.safeRunForDist(() -> SpeculativeClientProxy::new, () -> SpeculativeServerProxy::new);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::doClientStuff);
        bus.addListener(this::gatherData);

        SpeculativeSounds.SOUNDS.register(bus);
        SpeculativeParticleTypes.PARTICLE_TYPES.register(bus);
        SpeculativeRecipeSerializers.SERIALIZERS.register(bus);
        SpeculativeRecipeSerializers.RECIPE_TYPES.register(bus);
        SpeculativeMobEffects.MOB_EFFECTS.register(bus);
        SpeculativeBlocks.BLOCKS.register(bus);
        SpeculativeBlocks.BLOCK_ITEMS.register(bus);
        SpeculativeFluids.FLUIDS.register(bus);
        SpeculativeItems.ITEMS.register(bus);
        SpeculativeBlockEntityTypes.BLOCK_ENTITY_TYPES.register(bus);
        SpeculativeMenuTypes.MENU_TYPES.register(bus);
        SpeculativeEntityTypes.ENTITY_TYPES.register(bus);
        SpeculativeWorldGenRegistries.registerAll(bus);
        SpeculativeStructures.STRUCTURES.register(bus);
        SpeculativeStructures.STRUCTURE_PIECES.register(bus);
        SpeculativeStructures.Configured.CONFIGURED_STRUCTURES.register(bus);
        SpeculativeBiomes.BIOMES.register(bus);

        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DimensionUtil.registerDimensionWorldInfo(SpeculativeWorlds.SPECULO_WORLD);
            SpeculativeStructures.setupStructures();
            SpeculativeBiomes.addTypes();
            SpeculativeWoodTypes.registerValidBlocks();
            SpeculativeConfiguredFeatures.init();
            SpeculativeBoatItem.setupDispenseItemBehaviours();
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(SpeculativeRendering::addWoodTypes);
        SpeculativeRendering.registerBlockRenderLayers();
        SpeculativeRendering.registerContainerScreens();
        SpeculativeDimensionSpecialEffects.registerDimensionsSpecialEffects();
    }

    private void gatherData(final GatherDataEvent event) {
        DataGenerationContext ctx = new DataGenerationContext(event.getGenerator(), Speculative.MODID, event.getExistingFileHelper());

        if(event.includeServer()) {
            ctx.addProvider(new SpeculativeLootTableProvider(ctx));
            SpeculativeBlockTagsProvider blockTags = new SpeculativeBlockTagsProvider(ctx);
            ctx.addProvider(blockTags);
            ctx.addProvider(new SpeculativeItemTagsProvider(ctx, blockTags));
        }
    }

    @SubscribeEvent
    public void onRegisterCommands(final RegisterCommandsEvent event) {
        SpeculativeCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onBiomeLoad(final BiomeLoadingEvent event) {
        SpeculativeConfiguredFeatures.onBiomeLoad(event);
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

    public static SpeculativeCommonProxy proxy() {
        return instance.proxy;
    }
}
