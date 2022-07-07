package io.github.lgatodu47.speculative.common.container;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.init.SpeculativeMenuTypes;
import io.github.lgatodu47.speculative.common.init.SpeculativeRecipeSerializers;
import io.github.lgatodu47.speculative.common.recipe.NuclearWorkbenchShapedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class NuclearWorkbenchMenu extends AbstractContainerMenu {
    private final CraftingContainer craftMatrix = new CraftingContainer(this, 3, 3);
    private final ResultContainer craftResult = new ResultContainer();
    private final Player player;
    private final ContainerLevelAccess access;

    public NuclearWorkbenchMenu(int windowId, Inventory playerInv, FriendlyByteBuf data) {
        this(windowId, playerInv, ContainerLevelAccess.NULL);
    }

    public NuclearWorkbenchMenu(int windowId, Inventory playerInv, ContainerLevelAccess access) {
        super(SpeculativeMenuTypes.NUCLEAR_WORKBENCH.get(), windowId);
        this.player = playerInv.player;
        this.access = access;

        this.addSlot(new ResultSlot(player, this.craftMatrix, this.craftResult, 0, 124, 35));

        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3; ++y) {
                this.addSlot(new Slot(this.craftMatrix, y + x * 3, 30 + y * 18, 17 + x * 18));
            }
        }

        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 9; ++y) {
                this.addSlot(new Slot(playerInv, y + x * 9 + 9, 8 + y * 18, 84 + x * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInv, l, 8 + l * 18, 142));
        }
    }

    protected void updateCraftingResult(Level world) {
        if (!world.isClientSide) {
            ServerPlayer player = (ServerPlayer) this.player;
            ItemStack result = ItemStack.EMPTY;
            Optional<NuclearWorkbenchShapedRecipe> opt = world.getServer().getRecipeManager().getRecipeFor(SpeculativeRecipeSerializers.NUCLEAR_WORKBENCH_TYPE, this.craftMatrix, world);

            if (opt.isPresent()) {
                NuclearWorkbenchShapedRecipe recipe = opt.get();
                if (this.craftResult.setRecipeUsed(world, player, recipe)) {
                    result = recipe.assemble(this.craftMatrix);
                }
            }

            this.craftResult.setItem(0, result);
            player.connection.send(new ClientboundContainerSetSlotPacket(this.containerId, this.incrementStateId(), 0, result));
        }
    }

    @Override
    public void slotsChanged(Container inv) {
        this.access.execute((world, pos) -> updateCraftingResult(world));
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((world, pos) -> {
            this.clearContainer(player, this.craftMatrix);
        });
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(access, playerIn, SpeculativeBlocks.NUCLEAR_WORKBENCH.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack old = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            old = stack.copy();
            if (index == 0) {
                this.access.execute((world, pos) -> stack.getItem().onCraftedBy(stack, world, player));
                if (!this.moveItemStackTo(stack, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack, old);
            } else if (index >= 10 && index < 46) {
                if (!this.moveItemStackTo(stack, 1, 10, false)) {
                    if (index < 37) {
                        if (!this.moveItemStackTo(stack, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(stack, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(stack, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == old.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
            if (index == 0) {
                player.drop(stack, false);
            }
        }

        return old;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.craftResult && super.canTakeItemForPickAll(stack, slot);
    }
}
