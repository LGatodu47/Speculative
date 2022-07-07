package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.world.gen.carver.SpeculativeCanyonWorldCarver;
import io.github.lgatodu47.speculative.common.world.gen.carver.SpeculativeCaveWorldCarver;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Speculative.MODID, bus = Bus.MOD)
public final class SpeculativeWorldCarvers {
    public static final SpeculativeCaveWorldCarver SPECULO_CAVE = new SpeculativeCaveWorldCarver(CaveCarverConfiguration.CODEC);
    public static final SpeculativeCanyonWorldCarver SPECULO_CANYON = new SpeculativeCanyonWorldCarver(CanyonCarverConfiguration.CODEC);

    @SubscribeEvent
    public static void registerWorldCarvers(RegistryEvent.Register<WorldCarver<?>> event) {
        event.getRegistry().registerAll(SPECULO_CAVE, SPECULO_CANYON);
    }

    public static final class Configured {
        public static final Holder<ConfiguredWorldCarver<CaveCarverConfiguration>> CAVE = register("cave", SpeculativeWorldCarvers.SPECULO_CAVE.configured(new CaveCarverConfiguration(0.15F, UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180)), UniformFloat.of(0.1F, 0.9F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()), UniformFloat.of(0.7F, 1.4F), UniformFloat.of(0.8F, 1.3F), UniformFloat.of(-1.0F, -0.4F))));
        public static final Holder<ConfiguredWorldCarver<CanyonCarverConfiguration>> CANYON = register("canyon", SpeculativeWorldCarvers.SPECULO_CANYON.configured(new CanyonCarverConfiguration(0.01F, UniformHeight.of(VerticalAnchor.absolute(10), VerticalAnchor.absolute(67)), ConstantFloat.of(3.0F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.WARPED_BUTTON.defaultBlockState()), UniformFloat.of(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.of(0.75F, 1.0F), TrapezoidFloat.of(0.0F, 6.0F, 2.0F), 3, UniformFloat.of(0.75F, 1.0F), 1.0F, 0.0F))));

        private static <WC extends CarverConfiguration> Holder<ConfiguredWorldCarver<WC>> register(String name, ConfiguredWorldCarver<WC> carver) {
            return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_CARVER, Speculative.MODID.concat(":").concat(name), carver);
        }
    }
}
