package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.common.tile.UraniumBlockTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class UraniumBlock extends Block {
    private final UraniumType type;

    public UraniumBlock(UraniumType type) {
        super(Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.METAL).strength(5F, 1F));
        this.type = type;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return type != UraniumType.U238;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return type == UraniumType.U238 ? null : new UraniumBlockTileEntity(type == UraniumType.U234);
    }

    @Override
    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(type != UraniumType.U238) {
            TileEntity te = world.getBlockEntity(pos);
            if (te instanceof UraniumBlockTileEntity) {
                UraniumBlockTileEntity uraniumBlockTe = (UraniumBlockTileEntity)te;
                if (!world.isClientSide) {
                    ItemStack stack = new ItemStack(this);
                    CompoundNBT nbt = uraniumBlockTe.serialize(new CompoundNBT());

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
    public void wasExploded(World world, BlockPos pos, Explosion explosion) {
        if(type != UraniumType.U238) {
            world.explode(explosion.getExploder(), pos.getX(), pos.getY(), pos.getZ(), 4, true, Explosion.Mode.DESTROY);
        }
        super.wasExploded(world, pos, explosion);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, reader, tooltip, flag);
        if(type == UraniumType.U238) {
            tooltip.add(new TranslationTextComponent("tooltip.speculative.uranium_block.not_radioactive").withStyle(style -> style.withColor(type.getColor())));
        } else {
            tooltip.add(new TranslationTextComponent("tooltip.speculative.uranium_block.radioactive").withStyle(style -> style.withColor(type.getColor())));

            CompoundNBT nbt = stack.getTagElement("BlockEntityTag");
            if(nbt != null) {
                if(nbt.contains("CurrentRadiationTime")) {
                    tooltip.add(new TranslationTextComponent("tooltip.speculative.uranium_block.radiation_time", nbt.getInt("CurrentRadiationTime")).withStyle(TextFormatting.ITALIC, TextFormatting.GRAY));
                }
            }
        }
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
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

        public Color getColor() {
            return Color.parseColor(hexColor);
        }
    }
}
