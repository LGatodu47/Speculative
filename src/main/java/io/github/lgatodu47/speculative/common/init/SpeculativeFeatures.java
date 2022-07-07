package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.world.feature.BushPatchFeature;
import io.github.lgatodu47.speculative.common.world.feature.SpeculativeLakeFeature;
import io.github.lgatodu47.speculative.common.world.feature.OasisFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Speculative.MODID, bus = Bus.MOD)
public class SpeculativeFeatures {
    public static final Feature<BlockStateConfiguration> MOD_LAKES = new SpeculativeLakeFeature(BlockStateConfiguration.CODEC);
    public static final Feature<BlockStateConfiguration> BUSH_PATCH = new BushPatchFeature(BlockStateConfiguration.CODEC);
    public static final Feature<BlockStateConfiguration> OASIS = new OasisFeature(BlockStateConfiguration.CODEC);

    @SubscribeEvent
    public static void onFeatureRegister(RegistryEvent.Register<Feature<?>> event) {
        event.getRegistry().registerAll(MOD_LAKES, BUSH_PATCH, OASIS);
    }
}
