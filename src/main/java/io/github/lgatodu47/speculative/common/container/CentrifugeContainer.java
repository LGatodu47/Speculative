package io.github.lgatodu47.speculative.common.container;

import io.github.lgatodu47.speculative.common.init.SpeculativeContainerTypes;
import io.github.lgatodu47.speculative.common.tile.CentrifugeTileEntity;
import io.github.lgatodu47.speculative.util.FunctionalIntReferenceHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class CentrifugeContainer extends SpeculativeContainer<CentrifugeTileEntity> {
    private FunctionalIntReferenceHolder currentFuseTime;

    public CentrifugeContainer(int windowID, PlayerInventory playerInv, CentrifugeTileEntity tile) {
        super(SpeculativeContainerTypes.CENTRIFUGE_CONTAINER.get(), windowID, tile);

        this.createPlayerInventorySlots(playerInv, 8, 84);
        this.addDataSlot(currentFuseTime = new FunctionalIntReferenceHolder(() -> this.tile.currentFuseTime, value -> this.tile.currentFuseTime = value));
    }

    @Override
    protected void createInventorySlots(CentrifugeTileEntity tile) {
        this.addSlot(new SlotItemHandler(tile.getInventory(), 0, 35, 14));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 1, 35, 48));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 2, 95, 31));
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(final PlayerEntity player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            final ItemStack slotStack = slot.getItem();
            returnStack = slotStack.copy();

            final int containerSlots = this.slots.size() - player.inventory.items.size();
            if (index < containerSlots) {
                if (!moveItemStackTo(slotStack, containerSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(slotStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }
        return returnStack;
    }

    @OnlyIn(Dist.CLIENT)
    public int getFuseProgressionScaled() {
        return this.currentFuseTime.get() != 0 ? this.currentFuseTime.get() * 37 / this.tile.totalFuseTime : 0;
    }
}
