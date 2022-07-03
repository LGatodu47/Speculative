package io.github.lgatodu47.speculative.common.tile;

import io.github.lgatodu47.speculative.util.ISpeculativeItemHandler;
import io.github.lgatodu47.speculative.util.SpeculativeUtils;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandler;

import java.util.Optional;

public abstract class SpeculativeLockableTileEntity extends LockableLootTileEntity {
    protected final ISpeculativeItemHandler inv;

    protected SpeculativeLockableTileEntity(TileEntityType<?> type, ISpeculativeItemHandler inv) {
        super(type);
        this.inv = inv;
    }

    public final ISpeculativeItemHandler getInventory() {
        return this.inv;
    }

    protected <C extends IInventory, R extends IRecipe<C>> Optional<R> findRecipe(IRecipeType<R> type, Class<R> recipeClass, C inventory) {
        return SpeculativeUtils.findRecipesByType(type, level)
                .stream()
                .filter(recipeClass::isInstance)
                .map(recipeClass::cast)
                .filter(recipe -> recipe.matches(inventory, level))
                .findFirst();
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.inv.deserializeNBT(nbt.getCompound("Inventory"));
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.put("Inventory", this.inv.serializeNBT());
        return nbt;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.inv.getItems();
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.inv.setItems(itemsIn);
    }

    @Override
    public int getContainerSize() {
        return this.inv.getSlots();
    }

    @Override
    protected IItemHandler createUnSidedHandler() {
        return this.inv;
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getBlockPos(), 1, this.save(new CompoundNBT()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.load(level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.load(state, tag);
    }
}
