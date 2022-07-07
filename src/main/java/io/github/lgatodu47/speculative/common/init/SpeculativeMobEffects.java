package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SpeculativeMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Speculative.MODID);

    public static final RegistryObject<MobEffect> SULFURIC_SPEED = MOB_EFFECTS.register("sulfuric_speed", () -> new EffectImpl(MobEffectCategory.NEUTRAL, 12559673)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "4585B72D-A3DE-4057-B791-0D767E614B5E", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> C6_BLINDNESS = MOB_EFFECTS.register("cs_blindness", () -> new EffectImpl(MobEffectCategory.HARMFUL, 3413051));

    static class EffectImpl extends MobEffect {
        EffectImpl(MobEffectCategory type, int color) {
            super(type, color);
        }
    }
}