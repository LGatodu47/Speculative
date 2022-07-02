package io.github.lgatodu47.speculative.common.container;

import io.github.lgatodu47.speculative.common.init.SpeculativeContainerTypes;
import io.github.lgatodu47.speculative.common.tile.SpeculoosSummonerTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SpeculoosSummonerContainer extends SpeculativeContainer<SpeculoosSummonerTileEntity> {
    public SpeculoosSummonerContainer(int id, PlayerInventory playerInv, SpeculoosSummonerTileEntity tile) {
        super(SpeculativeContainerTypes.SPECULOOS_SUMMONER.get(), id, tile);
        createPlayerInventorySlots(playerInv, 8, 84);
    }

    @Override
    protected void createInventorySlots(SpeculoosSummonerTileEntity tile) {
        this.addSlot(new Slot(tile, 0, 80, 33));
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack copy = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (index == 0) {
                if (!this.mergeItemStack(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, copy);
            } else {
                if ((stack.getItem() == Items.DIAMOND)) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == copy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }
        return copy;
    }
}