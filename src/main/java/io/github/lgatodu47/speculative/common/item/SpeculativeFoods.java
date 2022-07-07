package io.github.lgatodu47.speculative.common.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class SpeculativeFoods {
    public static final FoodProperties ORANGE_FRUIT = builder().nutrition(4).saturationMod(3.5f).build();
    public static final FoodProperties STRANGE_MANGO = builder().nutrition(3).saturationMod(1.5f).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 200), 0.2f).build();
    public static final FoodProperties SPECULO_PORKCHOP = builder().nutrition(3).saturationMod(0.3F).meat().build();
    public static final FoodProperties COOKED_SPECULO_PORKCHOP = builder().nutrition(8).saturationMod(1.2F).meat().build();

    private static FoodProperties.Builder builder() {
        return new FoodProperties.Builder();
    }
}
