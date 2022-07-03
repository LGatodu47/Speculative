package io.github.lgatodu47.speculative.common.container;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.init.SpeculativeContainerTypes;
import io.github.lgatodu47.speculative.common.init.SpeculativeRecipeSerializers;
import io.github.lgatodu47.speculative.common.recipe.NuclearWorkbenchShapedRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

import java.util.Optional;

public class NuclearWorkbenchContainer extends Container {
    private final CraftingInventory craftMatrix = new CraftingInventory(this, 3, 3);
    private final CraftResultInventory craftResult = new CraftResultInventory();
    private final PlayerEntity player;
    private final IWorldPosCallable worldPosCallable;

    public NuclearWorkbenchContainer(int windowId, PlayerInventory playerInv, PacketBuffer data) {
        this(windowId, playerInv, IWorldPosCallable.NULL);
    }

    public NuclearWorkbenchContainer(int windowId, PlayerInventory playerInv, IWorldPosCallable worldPosCallable) {
        super(SpeculativeContainerTypes.NUCLEAR_WORKBENCH.get(), windowId);
        this.player = playerInv.player;
        this.worldPosCallable = worldPosCallable;

        this.addSlot(new CraftingResultSlot(player, this.craftMatrix, this.craftResult, 0, 124, 35));

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

    protected void updateCraftingResult(World world) {
        if (!world.isClientSide) {
            ServerPlayerEntity player = (ServerPlayerEntity) this.player;
            ItemStack result = ItemStack.EMPTY;
            Optional<NuclearWorkbenchShapedRecipe> opt = world.getServer().getRecipeManager().getRecipeFor(SpeculativeRecipeSerializers.NUCLEAR_WORKBENCH_TYPE, this.craftMatrix, world);

            if (opt.isPresent()) {
                NuclearWorkbenchShapedRecipe recipe = opt.get();
                if (this.craftResult.setRecipeUsed(world, player, recipe)) {
                    result = recipe.assemble(this.craftMatrix);
                }
            }

            this.craftResult.setItem(0, result);
            player.connection.send(new SSetSlotPacket(this.containerId, 0, result));
        }
    }

    @Override
    public void slotsChanged(IInventory inv) {
        this.worldPosCallable.execute((world, pos) -> updateCraftingResult(world));
    }

    @Override
    public void removed(PlayerEntity player) {
        super.removed(player);
        this.worldPosCallable.execute((world, pos) -> {
            this.clearContainer(player, world, this.craftMatrix);
        });
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(worldPosCallable, playerIn, SpeculativeBlocks.NUCLEAR_WORKBENCH.get());
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack old = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            old = stack.copy();
            if (index == 0) {
                this.worldPosCallable.execute((world, pos) -> stack.getItem().onCraftedBy(stack, world, player));
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

            ItemStack taken = slot.onTake(player, stack);
            if (index == 0) {
                player.drop(taken, false);
            }
        }

        return old;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.craftResult && super.canTakeItemForPickAll(stack, slot);
    }
}
