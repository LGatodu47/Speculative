package io.github.lgatodu47.speculative.common.container;

import io.github.lgatodu47.speculative.common.init.SpeculativeMenuTypes;
import io.github.lgatodu47.speculative.common.block.entity.SpeculoosSummonerBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SpeculoosSummonerMenu extends SpeculativeMenu<SpeculoosSummonerBlockEntity> {
    public SpeculoosSummonerMenu(int id, Inventory playerInv, SpeculoosSummonerBlockEntity blockEntity) {
        super(SpeculativeMenuTypes.SPECULOOS_SUMMONER.get(), id, blockEntity);
        createPlayerInventorySlots(playerInv, 8, 84);
    }

    @Override
    protected void createInventorySlots(SpeculoosSummonerBlockEntity blockEntity) {
        this.addSlot(new Slot(blockEntity, 0, 80, 33));
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack copy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            copy = stack.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, copy);
            } else {
                if ((stack.getItem() == Items.DIAMOND)) {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == copy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }
        return copy;
    }
}