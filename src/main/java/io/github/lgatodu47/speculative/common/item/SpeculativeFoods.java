package io.github.lgatodu47.speculative.common.item;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class SpeculativeFoods {
    public static final Food ORANGE_FRUIT = builder().nutrition(4).saturationMod(3.5f).build();
    public static final Food STRANGE_MANGO = builder().nutrition(3).saturationMod(1.5f).effect(() -> new EffectInstance(Effects.GLOWING, 200), 0.2f).build();
    public static final Food SPECULO_PORKCHOP = builder().nutrition(3).saturationMod(0.3F).meat().build();
    public static final Food COOKED_SPECULO_PORKCHOP = builder().nutrition(8).saturationMod(1.2F).meat().build();

    private static Food.Builder builder() {
        return new Food.Builder();
    }
}
