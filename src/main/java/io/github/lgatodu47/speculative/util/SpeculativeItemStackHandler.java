package io.github.lgatodu47.speculative.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class SpeculativeItemStackHandler extends ItemStackHandler implements ISpeculativeItemHandler {
    public SpeculativeItemStackHandler(int size, ItemStack... stacks) {
        super(size);

        for (int index = 0; index < stacks.length; index++) {
            this.stacks.set(index, stacks[index]);
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    public void setItems(NonNullList<ItemStack> stacks) {
        for (int index = 0; index < stacks.size(); index++) {
            this.stacks.set(index, stacks.get(index));
        }
    }

    @Override
    public String toString() {
        return this.stacks.toString();
    }
}
