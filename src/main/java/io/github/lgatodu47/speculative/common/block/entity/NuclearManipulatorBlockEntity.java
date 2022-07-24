package io.github.lgatodu47.speculative.common.block.entity;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlockEntityTypes;
import io.github.lgatodu47.speculative.util.SpeculativeItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class NuclearManipulatorBlockEntity extends SpeculativeBlockEntity {
    public NuclearManipulatorBlockEntity(BlockPos pos, BlockState state) {
        super(SpeculativeBlockEntityTypes.NUCLEAR_MANIPULATOR.get(), pos, state, new SpeculativeItemStackHandler(1));
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.speculative.nuclear_manipulator");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return null;
    }
}
