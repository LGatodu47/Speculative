package io.github.lgatodu47.speculative.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface ISpeculativeItemHandler extends IItemHandlerModifiable, INBTSerializable<CompoundNBT> {
    NonNullList<ItemStack> getItems();

    void setItems(NonNullList<ItemStack> stacks);
}
