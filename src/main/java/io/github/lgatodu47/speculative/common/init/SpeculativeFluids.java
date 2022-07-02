package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.fluid.SpeculativeFluid;
import io.github.lgatodu47.speculative.common.fluid.SpeculativeFluidAttributes;
import io.github.lgatodu47.speculative.common.fluid.EffectFlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SpeculativeFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Speculative.MODID);

    public static final SpeculativeFluid SULFURIC_WATER = new SpeculativeFluid.Builder("sulfuric_water", SpeculativeFluidAttributes.SULFURIC_WATER)
            .withProperties(props -> props.canMultiply().bucket(SpeculativeItems.SULFURIC_WATER_BUCKET).tickRate(10))
            .setBlockFactory((fluidSupplier, properties) -> new EffectFlowingFluidBlock(fluidSupplier, properties, () -> new EffectInstance(SpeculativeEffects.SULFURIC_SPEED.get(), 20 * 15)))
            .build();

    public static final SpeculativeFluid UNSTABLE_WATER = new SpeculativeFluid.Builder("unstable_water", SpeculativeFluidAttributes.UNSTABLE_WATER)
            .withBlockProperties(props -> props.setLightLevel(state -> 7))
            .withProperties(props -> props.canMultiply().bucket(SpeculativeItems.UNSTABLE_WATER_BUCKET).tickRate(5))
            .build();

    public static final SpeculativeFluid LIQUID_NITROGEN = new SpeculativeFluid.Builder("liquid_nitrogen", SpeculativeFluidAttributes.LIQUID_NITROGEN)
            .withProperties(props -> props.bucket(SpeculativeItems.LIQUID_NITROGEN_BUCKET).tickRate(20).levelDecreasePerBlock(2))
            .setBlockFactory((fluidSupplier, properties) -> new EffectFlowingFluidBlock(fluidSupplier, properties, () -> new EffectInstance(Effects.SLOWNESS, 20 * 2, 99)))
            .build();

    public static class Tags {
        public static final ITag.INamedTag<Fluid> SULFURIC_WATER = FluidTags.createOptional(new ResourceLocation(Speculative.MODID, "sulfuric_water"));
        public static final ITag.INamedTag<Fluid> UNSTABLE_WATER = FluidTags.createOptional(new ResourceLocation(Speculative.MODID, "unstable_water"));
    }
}
