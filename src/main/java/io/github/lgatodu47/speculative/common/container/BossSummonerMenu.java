package io.github.lgatodu47.speculative.common.container;

import io.github.lgatodu47.speculative.common.init.SpeculativeMenuTypes;
import io.github.lgatodu47.speculative.common.block.entity.BossSummonerBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class BossSummonerMenu extends SpeculativeMenu<BossSummonerBlockEntity> {
    public BossSummonerMenu(int windowID, Inventory playerInv, BossSummonerBlockEntity tile) {
        super(SpeculativeMenuTypes.BOSS_SUMMONER.get(), windowID, tile);
        createPlayerInventorySlots(playerInv, 8, 84);
    }

    @Override
    protected void createInventorySlots(BossSummonerBlockEntity blockEntity) {
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 48, 16));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 1, 66, 12));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 2, 84, 12));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 3, 102, 16));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 4, 97, 34));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 5, 115, 34));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 6, 102, 52));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 7, 84, 56));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 8, 66, 56));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 9, 48, 52));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 10, 53, 34));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 11, 35, 34));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 12, 75, 34));
    }
}
