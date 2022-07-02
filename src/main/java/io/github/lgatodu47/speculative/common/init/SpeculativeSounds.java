package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SpeculativeSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Speculative.MODID);

    public static final SoundEvent SULFURIC_WATER_BUCKET_FILL_SE = new SoundEvent(new ResourceLocation(Speculative.MODID, "item.sulfuric_water_bucket.fill"));
    public static final SoundEvent SULFURIC_WATER_BUCKET_EMPTY_SE = new SoundEvent(new ResourceLocation(Speculative.MODID, "item.sulfuric_water_bucket.empty"));
    public static final RegistryObject<SoundEvent> SULFURIC_WATER_BUCKET_FILL = SOUNDS.register("item.sulfuric_water_bucket.fill", () -> SULFURIC_WATER_BUCKET_FILL_SE);
    public static final RegistryObject<SoundEvent> SULFURIC_WATER_BUCKET_EMPTY = SOUNDS.register("item.sulfuric_water_bucket.empty", () -> SULFURIC_WATER_BUCKET_EMPTY_SE);
    public static final RegistryObject<SoundEvent> SULFURIC_WATER_AMBIENT = SOUNDS.register("block.sulfuric_water.ambient", () -> new SoundEvent(new ResourceLocation(Speculative.MODID, "block.sulfuric_water.ambient")));
}
