package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.common.entity.SpeculoTNTEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TNTBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;

public class SpeculoTNTBlock extends TNTBlock {
    public SpeculoTNTBlock() {
        super(Properties.copy(Blocks.TNT));
    }

    @Override
    public void wasExploded(World world, BlockPos pos, Explosion explosionIn) {
        if (!world.isClientSide) {
            SpeculoTNTEntity tnt = new SpeculoTNTEntity(world, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, explosionIn.getSourceMob());
            tnt.setFuse((short)(world.random.nextInt(tnt.getLife() / 4) + tnt.getLife() / 8));
            world.addFreshEntity(tnt);
            world.playSound(null, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public void catchFire(BlockState state, World world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        if (!world.isClientSide) {
            SpeculoTNTEntity tnt = new SpeculoTNTEntity(world, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, igniter);
            world.addFreshEntity(tnt);
            world.playSound(null, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }
}
