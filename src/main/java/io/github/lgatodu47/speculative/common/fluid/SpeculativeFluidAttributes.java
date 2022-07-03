package io.github.lgatodu47.speculative.common.fluid;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.init.SpeculativeSounds;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;

public final class SpeculativeFluidAttributes {
    public static final FluidAttributes.Builder SULFURIC_WATER = FluidAttributes.builder(
                    new ResourceLocation(Speculative.MODID, "block/still_sulfuric"), new ResourceLocation(Speculative.MODID, "block/flowing_sulfuric")
            )
            .overlay(new ResourceLocation(Speculative.MODID, "misc/sulfuric_water_overlay"))
            .color(8545293)
            .density(150)
            .sound(SpeculativeSounds.SULFURIC_WATER_BUCKET_FILL_SE, SpeculativeSounds.SULFURIC_WATER_BUCKET_EMPTY_SE)
            .viscosity(20)
            .translationKey("fluid.speculative.sulfuric_water");

    public static final FluidAttributes.Builder UNSTABLE_WATER = FluidAttributes.builder(
                    new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow")
            )
            .overlay(new ResourceLocation(Speculative.MODID, "misc/unstable_water_overlay"))
            .color(3473300)
            .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
            .translationKey("fluid.speculative.unstable_water");

    public static final FluidAttributes.Builder LIQUID_NITROGEN = FluidAttributes.builder(
                    new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow")
            )
            .color(526411)
            .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
            .translationKey("fluid.speculative.liquid_nitrogen");

}
