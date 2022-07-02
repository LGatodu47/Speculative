package io.github.lgatodu47.speculative.common.tile;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.container.SpeculoosSummonerContainer;
import io.github.lgatodu47.speculative.common.init.SpeculativeItems;
import io.github.lgatodu47.speculative.common.init.SpeculativeTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class SpeculoosSummonerTileEntity extends LockableLootTileEntity implements ITickableTileEntity {
    private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

    public SpeculoosSummonerTileEntity() {
        super(SpeculativeTileEntityTypes.SPECULOOS_SUMMONER_TILE_ENTITY.get());
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Speculative.MODID + ".speculoos_summoner");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new SpeculoosSummonerContainer(id, player, this);
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);
        nbt.put("Content", getStack().write(new CompoundNBT()));
        return nbt;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        setStack(ItemStack.read(nbt.getCompound("Content")));
    }

    @Override
    public void tick() {
        if (getStack().getItem().equals(Items.DIAMOND)) {
            int diamondCount = getStack().getCount();
            setStack(new ItemStack(SpeculativeItems.SPECULO_GEM.get(), diamondCount));
            this.world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.BLOCKS, 1.0F, 0.5F + this.world.rand.nextFloat(), false);
            markDirty();
        }
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.inventory = items;
    }

    protected void setStack(ItemStack stack) {
        inventory.set(0, stack);
    }

    public ItemStack getStack() {
        return inventory.get(0);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), 1, this.write(new CompoundNBT()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(world.getBlockState(pkt.getPos()), pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.read(state, tag);
    }
}