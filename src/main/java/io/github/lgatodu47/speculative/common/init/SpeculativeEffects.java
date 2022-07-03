package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SpeculativeEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Speculative.MODID);

    public static final RegistryObject<Effect> SULFURIC_SPEED = EFFECTS.register("sulfuric_speed", () -> new EffectImpl(EffectType.NEUTRAL, 12559673)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "4585B72D-A3DE-4057-B791-0D767E614B5E", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<Effect> C6_BLINDNESS = EFFECTS.register("cs_blindness", () -> new EffectImpl(EffectType.HARMFUL, 3413051));

    static class EffectImpl extends Effect {
        EffectImpl(EffectType type, int color) {
            super(type, color);
        }
    }
}