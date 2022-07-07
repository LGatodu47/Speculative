package io.github.lgatodu47.speculative.common.item;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class SpeculativeFoodItem extends Item {
    private SoundEvent eatSound = super.getEatingSound();
    private int eatTicks = 32;

    public SpeculativeFoodItem(FoodProperties food) {
        super(new Properties().tab(Speculative.tab()).food(food));
    }

    public SpeculativeFoodItem sounds(@Nonnull SoundEvent event) {
        this.eatSound = event;
        return this;
    }

    public SpeculativeFoodItem eatSpeed(int ticks) {
        this.eatTicks = ticks;
        return this;
    }

    @Override
    public SoundEvent getEatingSound() {
        return eatSound;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return eatTicks;
    }
}
