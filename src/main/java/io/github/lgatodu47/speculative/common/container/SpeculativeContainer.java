package io.github.lgatodu47.speculative.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

public abstract class SpeculativeContainer<T extends TileEntity> extends Container {
    protected final T tile;
    protected final IWorldPosCallable worldPosCallable;

    public SpeculativeContainer(ContainerType<?> type, int id, T tileEntity) {
        super(type, id);

        this.tile = tileEntity;
        this.worldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        this.createInventorySlots(tileEntity);
    }

    protected void createPlayerInventorySlots(PlayerInventory playerInv, int startX, int startY) {
        int slotSizePlus2 = 18;
        int hotbarY = startY + 58;

        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(playerInv, column, startX + (column * slotSizePlus2), hotbarY));
        }

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInv, 9 + (row * 9) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
            }
        }
    }

    protected abstract void createInventorySlots(T tile);

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(worldPosCallable, playerIn, tile.getBlockState().getBlock());
    }
}
