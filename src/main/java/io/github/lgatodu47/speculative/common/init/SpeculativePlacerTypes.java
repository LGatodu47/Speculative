package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.world.gen.foliageplacer.TourmalineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class SpeculativePlacerTypes {
    public static final class Foliage {
        public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, Speculative.MODID);

        public static final RegistryObject<FoliagePlacerType<?>> TOURMALINE = FOLIAGE_PLACER_TYPES.register("tourmaline", () -> new FoliagePlacerType<>(TourmalineFoliagePlacer.CODEC));
    }

    public static void registerAll(IEventBus bus) {
        Foliage.FOLIAGE_PLACER_TYPES.register(bus);
    }
}
