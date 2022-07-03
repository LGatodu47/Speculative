package io.github.lgatodu47.speculative.common.init;

import com.mojang.serialization.Codec;
import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.world.gen.foliageplacer.SpeculativeTrunkPlacer;
import io.github.lgatodu47.speculative.common.world.gen.foliageplacer.TourmalineFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class SpeculativePlacerTypes {
    public static final class Trunk {
        public static final TrunkPlacerType<SpeculativeTrunkPlacer> SPECULATIVE = register("speculative", SpeculativeTrunkPlacer.CODEC);

        private static Method REGISTER;

        @SuppressWarnings("unchecked")
        private static <P extends AbstractTrunkPlacer> TrunkPlacerType<P> register(String name, Codec<P> codec) {
            if(REGISTER == null) {
                REGISTER = ObfuscationReflectionHelper.findMethod(TrunkPlacerType.class, "register", String.class, Codec.class);
                REGISTER.setAccessible(true);
            }

            try {
                return (TrunkPlacerType<P>) REGISTER.invoke(null, Speculative.MODID.concat(":").concat(name), codec);
            } catch (IllegalAccessException | InvocationTargetException e) {
                Speculative.LOGGER.error("Error in reflection", e);
            }

            return null;
        }

        public static void init() {
        }
    }

    public static final class Foliage {
        public static final DeferredRegister<FoliagePlacerType<?>> TYPES = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, Speculative.MODID);

        public static final RegistryObject<FoliagePlacerType<?>> TOURMALINE = TYPES.register("tourmaline", () -> new FoliagePlacerType<>(TourmalineFoliagePlacer.CODEC));
    }

    public static void registerAll(IEventBus bus) {
        Foliage.TYPES.register(bus);
    }
}
