package io.github.lgatodu47.speculative.common.container;

import io.github.lgatodu47.speculative.common.init.SpeculativeContainerTypes;
import io.github.lgatodu47.speculative.common.tile.BossSummonerTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraftforge.items.SlotItemHandler;

public class BossSummonerContainer extends SpeculativeContainer<BossSummonerTileEntity> {
    public BossSummonerContainer(int windowID, PlayerInventory playerInv, BossSummonerTileEntity tile) {
        super(SpeculativeContainerTypes.BOSS_SUMMONER.get(), windowID, tile);
        createPlayerInventorySlots(playerInv, 8, 84);
    }

    @Override
    protected void createInventorySlots(BossSummonerTileEntity tile) {
        this.addSlot(new SlotItemHandler(tile.getInventory(), 0, 48, 16));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 1, 66, 12));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 2, 84, 12));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 3, 102, 16));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 4, 97, 34));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 5, 115, 34));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 6, 102, 52));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 7, 84, 56));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 8, 66, 56));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 9, 48, 52));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 10, 53, 34));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 11, 35, 34));
        this.addSlot(new SlotItemHandler(tile.getInventory(), 12, 75, 34));
    }
}
