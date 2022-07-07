package io.github.lgatodu47.speculative.common.container;

import io.github.lgatodu47.speculative.common.init.SpeculativeMenuTypes;
import io.github.lgatodu47.speculative.common.block.entity.CentrifugeBlockEntity;
import io.github.lgatodu47.speculative.util.FunctionalIntReferenceHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class CentrifugeMenu extends SpeculativeMenu<CentrifugeBlockEntity> {
    private FunctionalIntReferenceHolder currentFuseTime;

    public CentrifugeMenu(int windowID, Inventory playerInv, CentrifugeBlockEntity tile) {
        super(SpeculativeMenuTypes.CENTRIFUGE_CONTAINER.get(), windowID, tile);

        this.createPlayerInventorySlots(playerInv, 8, 84);
        this.addDataSlot(currentFuseTime = new FunctionalIntReferenceHolder(() -> this.blockEntity.currentFuseTime, value -> this.blockEntity.currentFuseTime = value));
    }

    @Override
    protected void createInventorySlots(CentrifugeBlockEntity blockEntity) {
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 35, 14));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 1, 35, 48));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 2, 95, 31));
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(final Player player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            final ItemStack slotStack = slot.getItem();
            returnStack = slotStack.copy();

            final int containerSlots = this.slots.size() - player.getInventory().items.size();
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
        return this.currentFuseTime.get() != 0 ? this.currentFuseTime.get() * 37 / this.blockEntity.totalFuseTime : 0;
    }
}
