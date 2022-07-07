package io.github.lgatodu47.speculative.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface ISpeculativeItemHandler extends IItemHandlerModifiable, INBTSerializable<CompoundTag> {
    NonNullList<ItemStack> getItems();

    void setItems(NonNullList<ItemStack> stacks);
}
