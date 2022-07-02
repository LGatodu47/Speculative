package io.github.lgatodu47.speculative.common.tile;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.block.CentrifugeBlock;
import io.github.lgatodu47.speculative.common.container.CentrifugeContainer;
import io.github.lgatodu47.speculative.common.init.SpeculativeItems;
import io.github.lgatodu47.speculative.common.init.SpeculativeRecipeSerializers;
import io.github.lgatodu47.speculative.common.init.SpeculativeTileEntityTypes;
import io.github.lgatodu47.speculative.common.recipe.CentrifugeRecipe;
import io.github.lgatodu47.speculative.util.SpeculativeItemStackHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.Optional;

public class CentrifugeTileEntity extends SpeculativeLockableTileEntity implements ITickableTileEntity {
    public int currentFuseTime;
    public final int totalFuseTime = 100;

    public CentrifugeTileEntity() {
        super(SpeculativeTileEntityTypes.CENTRIFUGE_TILE_ENTITY.get(), new SpeculativeItemStackHandler(3));
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Speculative.MODID + ".centrifuge");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new CentrifugeContainer(id, player, this);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.currentFuseTime = nbt.getInt("CurrentFuseTime");
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);
        nbt.putInt("CurrentFuseTime", this.currentFuseTime);
        return nbt;
    }

    @Override
    public void tick() {
        if(world == null) {
            return;
        }

        boolean update = false;

        if (!world.isRemote) {
            if (world.isBlockPowered(pos) && !inv.getStackInSlot(0).isEmpty() && !inv.getStackInSlot(1).isEmpty()) {
                Optional<CentrifugeRecipe> recipe = findRecipe(SpeculativeRecipeSerializers.CENTRIFUGE_TYPE, CentrifugeRecipe.class, new RecipeWrapper(inv));

                if (recipe.isPresent() && this.canFuse(recipe.get())) {
                    ++this.currentFuseTime;
                    if (this.currentFuseTime == this.totalFuseTime) {
                        this.currentFuseTime = 0;
                        this.fuse(recipe.get());
                        update = true;
                    }
                    world.setBlockState(pos, this.getBlockState().with(CentrifugeBlock.LIT, true));
                } else {
                    this.currentFuseTime = 0;
                    world.setBlockState(pos, this.getBlockState().with(CentrifugeBlock.LIT, false));
                }
            } else if (!world.isBlockPowered(pos) && this.currentFuseTime > 0) {
                this.currentFuseTime = MathHelper.clamp(this.currentFuseTime - 2, 0, this.totalFuseTime);
                world.setBlockState(pos, this.getBlockState().with(CentrifugeBlock.LIT, false));
            }
        }

        if (update) {
            this.markDirty();
        }
    }

    private boolean canFuse(CentrifugeRecipe recipe) {
        ItemStack result = recipe.getRecipeOutput();
        ItemStack output = inv.getStackInSlot(2);

        if (result.isEmpty()) {
            return false;
        }
        if (output.isEmpty()) {
            return true;
        }
        if (!output.isItemEqual(result)) {
            return false;
        }
        if (output.getCount() + result.getCount() <= this.getInventoryStackLimit() && output.getCount() + result.getCount() <= output.getMaxStackSize()) {
            return true;
        }

        return output.getCount() + result.getCount() <= result.getMaxStackSize();
    }

    private void fuse(CentrifugeRecipe recipe) {
        if (this.canFuse(recipe)) {
            ItemStack input1 = inv.getStackInSlot(0);
            ItemStack input2 = inv.getStackInSlot(1);
            ItemStack result = recipe.getRecipeOutput();
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
