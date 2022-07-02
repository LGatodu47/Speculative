package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.world.gen.carver.SpeculativeCanyonWorldCarver;
import io.github.lgatodu47.speculative.common.world.gen.carver.SpeculativeCaveWorldCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Speculative.MODID, bus = Bus.MOD)
public class SpeculativeWorldCarvers {
    public static final SpeculativeCaveWorldCarver SPECULO_CAVES_WORLD_CARVER = new SpeculativeCaveWorldCarver(ProbabilityConfig.CODEC, 256);
    public static final SpeculativeCanyonWorldCarver SPECULO_CANYONS_WORLD_CARVER = new SpeculativeCanyonWorldCarver(ProbabilityConfig.CODEC);

    @SubscribeEvent
    public static void registerWorldCarvers(RegistryEvent.Register<WorldCarver<?>> event) {
        event.getRegistry().registerAll(SPECULO_CAVES_WORLD_CARVER, SPECULO_CANYONS_WORLD_CARVER);
    }
}
