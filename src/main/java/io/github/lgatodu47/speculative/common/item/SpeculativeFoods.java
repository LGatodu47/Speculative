package io.github.lgatodu47.speculative.common.item;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class SpeculativeFoods {
    public static final Food ORANGE_FRUIT = builder().hunger(4).saturation(3.5f).build();
    public static final Food STRANGE_MANGO = builder().hunger(3).saturation(1.5f).effect(() -> new EffectInstance(Effects.GLOWING, 200), 0.2f).build();
    public static final Food SPECULO_PORKCHOP = builder().hunger(3).saturation(0.3F).meat().build();
    public static final Food COOKED_SPECULO_PORKCHOP = builder().hunger(8).saturation(1.2F).meat().build();

    private static Food.Builder builder() {
        return new Food.Builder();
    }
}
