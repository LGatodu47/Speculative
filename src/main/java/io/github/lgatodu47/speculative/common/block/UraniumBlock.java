package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.common.block.entity.UraniumBlockEntity;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlockEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class UraniumBlock extends Block implements ISpeculativeEntityBlock {
    private final UraniumType type;

    public UraniumBlock(UraniumType type) {
        super(Properties.of(Material.METAL).sound(SoundType.METAL).strength(5F, 1F).requiresCorrectToolForDrops());
        this.type = type;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return type == UraniumType.U238 ? null : new UraniumBlockEntity(pos, state, type == UraniumType.U234);
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return SpeculativeBlockEntityTypes.URANIUM_BLOCK.get();
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if(type != UraniumType.U238) {
            BlockEntity te = world.getBlockEntity(pos);
            if (te instanceof UraniumBlockEntity) {
                UraniumBlockEntity uraniumBlockTe = (UraniumBlockEntity)te;
                if (!world.isClientSide) {
                    ItemStack stack = new ItemStack(this);
                    CompoundTag nbt = uraniumBlockTe.serialize(new CompoundTag());

                    if (!nbt.isEmpty()) {
                        stack.addTagElement("BlockEntityTag", nbt);
                    }

                    ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, stack);
                    itemEntity.setDefaultPickUpDelay();
                    world.addFreshEntity(itemEntity);
                }
            }
        }

        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void wasExploded(Level world, BlockPos pos, Explosion explosion) {
        if(type != UraniumType.U238) {
            world.explode(explosion.getExploder(), pos.getX(), pos.getY(), pos.getZ(), 4, true, Explosion.BlockInteraction.DESTROY);
        }
        super.wasExploded(world, pos, explosion);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter reader, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, reader, tooltip, flag);
        if(type == UraniumType.U238) {
            tooltip.add(new TranslatableComponent("tooltip.speculative.uranium_block.not_radioactive").withStyle(style -> style.withColor(type.getColor())));
        } else {
            tooltip.add(new TranslatableComponent("tooltip.speculative.uranium_block.radioactive").withStyle(style -> style.withColor(type.getColor())));

            CompoundTag nbt = stack.getTagElement("BlockEntityTag");
            if(nbt != null) {
                if(nbt.contains("CurrentRadiationTime")) {
                    tooltip.add(new TranslatableComponent("tooltip.speculative.uranium_block.radiation_time", nbt.getInt("CurrentRadiationTime")).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
                }
            }
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {
        if(type != UraniumType.U238) {
            final int loopTimes = type == UraniumType.U234 ? 3 : 1;
            for(int i = 0; i < loopTimes; i++) {
                double offsetX = world.random.nextGaussian() * 0.4;
                double offsetY = world.random.nextGaussian() * 0.5;
                double offsetZ = world.random.nextGaussian() * 0.4;
                world.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5 + offsetX, pos.getY() + 1 + offsetY, pos.getZ() + 0.5 + offsetZ, world.random.nextGaussian(), world.random.nextGaussian(), world.random.nextGaussian());
            }
        }
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    public enum UraniumType {
        U238("#93E9B8"),
        U235("#40E989"),
        U234("#26E946");

        final String hexColor;

        UraniumType(String color) {
            this.hexColor = color;
        }

        public TextColor getColor() {
            return TextColor.parseColor(hexColor);
        }
    }
}
