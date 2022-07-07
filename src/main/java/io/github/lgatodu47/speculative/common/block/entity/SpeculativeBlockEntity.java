package io.github.lgatodu47.speculative.common.block.entity;

import io.github.lgatodu47.speculative.util.ISpeculativeItemHandler;
import io.github.lgatodu47.speculative.util.SpeculativeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;

import java.util.Optional;

public abstract class SpeculativeBlockEntity extends RandomizableContainerBlockEntity {
    protected final ISpeculativeItemHandler inv;

    protected SpeculativeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ISpeculativeItemHandler inv) {
        super(type, pos, state);
        this.inv = inv;
    }

    public final ISpeculativeItemHandler getInventory() {
        return this.inv;
    }

    protected <C extends Container, R extends Recipe<C>> Optional<R> findRecipe(RecipeType<R> type, Class<R> recipeClass, C inventory) {
        return SpeculativeUtils.findRecipesByType(type, level)
                .stream()
                .filter(recipeClass::isInstance)
                .map(recipeClass::cast)
                .filter(recipe -> recipe.matches(inventory, level))
                .findFirst();
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.inv.deserializeNBT(nbt.getCompound("Inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("Inventory", this.inv.serializeNBT());
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
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
