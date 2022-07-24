package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.common.entity.SpeculoTNTEntity;
import io.github.lgatodu47.speculative.data.loot.ISelfDropBlockLoot;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class SpeculoTNTBlock extends TntBlock implements ISelfDropBlockLoot {
    public SpeculoTNTBlock() {
        super(Properties.copy(Blocks.TNT));
    }

    @Override
    public void wasExploded(Level world, BlockPos pos, Explosion explosionIn) {
        if (!world.isClientSide) {
            SpeculoTNTEntity tnt = new SpeculoTNTEntity(world, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, explosionIn.getSourceMob());
            tnt.setFuse((short)(world.random.nextInt(tnt.getFuse() / 4) + tnt.getFuse() / 8));
            world.addFreshEntity(tnt);
            world.playSound(null, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        if (!world.isClientSide) {
            SpeculoTNTEntity tnt = new SpeculoTNTEntity(world, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, igniter);
            world.addFreshEntity(tnt);
            world.playSound(null, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }
}
