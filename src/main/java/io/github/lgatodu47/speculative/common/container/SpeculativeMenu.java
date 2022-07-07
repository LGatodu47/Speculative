package io.github.lgatodu47.speculative.common.container;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.inventory.ContainerLevelAccess;

public abstract class SpeculativeMenu<T extends BlockEntity> extends AbstractContainerMenu {
    protected final T blockEntity;
    protected final ContainerLevelAccess access;

    public SpeculativeMenu(MenuType<?> type, int id, T blockEntity) {
        super(type, id);

        this.blockEntity = blockEntity;
        this.access = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        this.createInventorySlots(blockEntity);
    }

    protected void createPlayerInventorySlots(Inventory playerInv, int startX, int startY) {
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

    protected abstract void createInventorySlots(T blockEntity);

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(access, playerIn, blockEntity.getBlockState().getBlock());
    }
}
