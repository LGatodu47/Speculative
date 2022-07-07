package io.github.lgatodu47.speculative.common.block.entity;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.block.CentrifugeBlock;
import io.github.lgatodu47.speculative.common.container.CentrifugeMenu;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlockEntityTypes;
import io.github.lgatodu47.speculative.common.init.SpeculativeRecipeSerializers;
import io.github.lgatodu47.speculative.common.recipe.CentrifugeRecipe;
import io.github.lgatodu47.speculative.util.SpeculativeItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.Optional;

public class CentrifugeBlockEntity extends SpeculativeBlockEntity implements ISpeculativeTickingBlockEntity {
    public int currentFuseTime;
    public final int totalFuseTime = 100;

    public CentrifugeBlockEntity(BlockPos pos, BlockState state) {
        super(SpeculativeBlockEntityTypes.CENTRIFUGE.get(), pos, state, new SpeculativeItemStackHandler(3));
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container." + Speculative.MODID + ".centrifuge");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new CentrifugeMenu(id, player, this);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.currentFuseTime = nbt.getInt("CurrentFuseTime");
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("CurrentFuseTime", this.currentFuseTime);
    }

    @Override
    public void tick(Level level) {
        if(level == null) {
            return;
        }

        boolean update = false;

        if (!level.isClientSide) {
            if (level.hasNeighborSignal(worldPosition) && !inv.getStackInSlot(0).isEmpty() && !inv.getStackInSlot(1).isEmpty()) {
                Optional<CentrifugeRecipe> recipe = findRecipe(SpeculativeRecipeSerializers.CENTRIFUGE_TYPE, CentrifugeRecipe.class, new RecipeWrapper(inv));

                if (recipe.isPresent() && this.canFuse(recipe.get())) {
                    ++this.currentFuseTime;
                    if (this.currentFuseTime == this.totalFuseTime) {
                        this.currentFuseTime = 0;
                        this.fuse(recipe.get());
                        update = true;
                    }
                    level.setBlockAndUpdate(worldPosition, this.getBlockState().setValue(CentrifugeBlock.LIT, true));
                } else {
                    this.currentFuseTime = 0;
                    level.setBlockAndUpdate(worldPosition, this.getBlockState().setValue(CentrifugeBlock.LIT, false));
                }
            } else if (!level.hasNeighborSignal(worldPosition) && this.currentFuseTime > 0) {
                this.currentFuseTime = Mth.clamp(this.currentFuseTime - 2, 0, this.totalFuseTime);
                level.setBlockAndUpdate(worldPosition, this.getBlockState().setValue(CentrifugeBlock.LIT, false));
            }
        }

        if (update) {
            this.setChanged();
        }
    }

    private boolean canFuse(CentrifugeRecipe recipe) {
        ItemStack result = recipe.getResultItem();
        ItemStack output = inv.getStackInSlot(2);

        if (result.isEmpty()) {
            return false;
        }
        if (output.isEmpty()) {
            return true;
        }
        if (!output.sameItem(result)) {
            return false;
        }
        if (output.getCount() + result.getCount() <= this.getMaxStackSize() && output.getCount() + result.getCount() <= output.getMaxStackSize()) {
            return true;
        }

        return output.getCount() + result.getCount() <= result.getMaxStackSize();
    }

    private void fuse(CentrifugeRecipe recipe) {
        if (this.canFuse(recipe)) {
            ItemStack input1 = inv.getStackInSlot(0);
            ItemStack input2 = inv.getStackInSlot(1);
            ItemStack result = recipe.getResultItem();
            ItemStack output = inv.getStackInSlot(2);

            if (output.isEmpty()) {
                inv.setStackInSlot(2, result.copy());
            } else if (output.getItem() == result.getItem()) {
                output.grow(result.getCount());
            }

            input1.shrink(recipe.getInput1().amount);
            input2.shrink(recipe.getInput2().amount);
        }
    }
}
