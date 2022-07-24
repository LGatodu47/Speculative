package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.world.gen.foliageplacer.TourmalineFoliagePlacer;
import io.github.lgatodu47.speculative.util.LazyBlockMatchTest;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class SpeculativeWorldGenRegistries {
    public static final class FoliagePlacers {
        public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, Speculative.MODID);

        public static final RegistryObject<FoliagePlacerType<?>> TOURMALINE = FOLIAGE_PLACER_TYPES.register("tourmaline", () -> new FoliagePlacerType<>(TourmalineFoliagePlacer.CODEC));
    }

    public static final class RuleTestTypes {
        public static final DeferredRegister<RuleTestType<?>> RULE_TEST_TYPES = DeferredRegister.create(Registry.RULE_TEST_REGISTRY, Speculative.MODID);

        public static final RegistryObject<RuleTestType<LazyBlockMatchTest>> LAZY_BLOCK_MATCH = RULE_TEST_TYPES.register("lazy_block_match", () -> () -> LazyBlockMatchTest.CODEC);
    }

    public static void registerAll(IEventBus bus) {
        FoliagePlacers.FOLIAGE_PLACER_TYPES.register(bus);
        RuleTestTypes.RULE_TEST_TYPES.register(bus);
    }
}
