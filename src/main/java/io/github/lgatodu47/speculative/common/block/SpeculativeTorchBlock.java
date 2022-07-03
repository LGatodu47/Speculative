package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.common.init.SpeculativeParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class SpeculativeTorchBlock extends TorchBlock {
    public SpeculativeTorchBlock(Properties properties) {
        super(properties, null);
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.7D;
        double d2 = (double) pos.getZ() + 0.5D;
        worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(SpeculativeParticleTypes.GREEN_FLAME.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    public static class SpeculativeWallTorchBlock extends WallTorchBlock {
        public SpeculativeWallTorchBlock(Properties properties) {
            super(properties, null);
        }

        @Override
        public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
            Direction direction = stateIn.getValue(FACING);
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + 0.7D;
            double d2 = (double) pos.getZ() + 0.5D;
            double d3 = 0.22D;
            double d4 = 0.27D;
            Direction direction1 = direction.getOpposite();
            worldIn.addParticle(ParticleTypes.SMOKE, d0 + 0.27D * (double) direction1.getStepX(), d1 + 0.22D, d2 + 0.27D * (double) direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(SpeculativeParticleTypes.GREEN_FLAME.get(), d0 + 0.27D * (double) direction1.getStepX(), d1 + 0.22D, d2 + 0.27D * (double) direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
        }
    }
}