package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SpeculativeSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Speculative.MODID);

    public static final RegistryObject<SoundEvent> SULFURIC_WATER_BUCKET_FILL = register("item.sulfuric_water_bucket.fill");
    public static final RegistryObject<SoundEvent> SULFURIC_WATER_BUCKET_EMPTY = register("item.sulfuric_water_bucket.empty");
    public static final RegistryObject<SoundEvent> SULFURIC_WATER_AMBIENT = register("block.sulfuric_water.ambient");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(Speculative.MODID, name)));
    }

    public static SoundEvent lazy(RegistryObject<SoundEvent> sound) {
        return new LazySoundEvent(sound);
    }

    private static final class LazySoundEvent extends SoundEvent {
        private final RegistryObject<SoundEvent> obj;

        private LazySoundEvent(RegistryObject<SoundEvent> obj) {
            super(obj.getId());
            this.obj = obj;
        }

        @Override
        public ResourceLocation getLocation() {
            return obj.get().getLocation();
        }
    }
}
