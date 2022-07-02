package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.world.feature.BushPatchFeature;
import io.github.lgatodu47.speculative.common.world.feature.SpeculativeLakesFeature;
import io.github.lgatodu47.speculative.common.world.feature.OasisFeature;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Speculative.MODID, bus = Bus.MOD)
public class SpeculativeFeatures
{
	public static final Feature<BlockStateFeatureConfig> MOD_LAKES = new SpeculativeLakesFeature(BlockStateFeatureConfig.CODEC);
	public static final Feature<BlockStateFeatureConfig> BUSH_PATCH = new BushPatchFeature(BlockStateFeatureConfig.CODEC);
	public static final Feature<BlockStateFeatureConfig> OASIS = new OasisFeature(BlockStateFeatureConfig.CODEC);
	
	@SubscribeEvent
	public static void onFeatureRegister(RegistryEvent.Register<Feature<?>> event)
	{
		event.getRegistry().registerAll(MOD_LAKES, BUSH_PATCH, OASIS);
	}
}
