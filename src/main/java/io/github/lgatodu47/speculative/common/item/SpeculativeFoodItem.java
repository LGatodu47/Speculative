package io.github.lgatodu47.speculative.common.item;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nonnull;

public class SpeculativeFoodItem extends Item {
    private SoundEvent eatSound = super.getEatSound();
    private int eatTicks = 32;

    public SpeculativeFoodItem(Food food) {
        super(new Properties().group(Speculative.tab()).food(food));
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
    public SoundEvent getEatSound() {
        return eatSound;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return eatTicks;
    }
}
