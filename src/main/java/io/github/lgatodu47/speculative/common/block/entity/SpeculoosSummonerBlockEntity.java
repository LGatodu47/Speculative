package io.github.lgatodu47.speculative.common.block.entity;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.container.SpeculoosSummonerMenu;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlockEntityTypes;
import io.github.lgatodu47.speculative.common.init.SpeculativeItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SpeculoosSummonerBlockEntity extends RandomizableContainerBlockEntity implements ISpeculativeTickingBlockEntity {
    private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

    public SpeculoosSummonerBlockEntity(BlockPos pos, BlockState state) {
        super(SpeculativeBlockEntityTypes.SPECULOOS_SUMMONER.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container." + Speculative.MODID + ".speculoos_summoner");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new SpeculoosSummonerMenu(id, player, this);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("Content", getStack().save(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        setStack(ItemStack.of(nbt.getCompound("Content")));
    }

    @Override
    public void tick(Level level) {
        if (getStack().getItem().equals(Items.DIAMOND)) {
            int diamondCount = getStack().getCount();
            setStack(new ItemStack(SpeculativeItems.SPECULO_GEM.get(), diamondCount));
            this.level.playLocalSound(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), SoundEvents.ZOMBIE_VILLAGER_CONVERTED, SoundSource.BLOCKS, 1.0F, 0.5F + this.level.random.nextFloat(), false);
            setChanged();
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
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
