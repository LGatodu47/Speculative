package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.common.init.SpeculativeItems;
import io.github.lgatodu47.speculative.common.init.SpeculativeParticleTypes;
import io.github.lgatodu47.speculative.data.loot.IDataGenLoot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Random;

public class SpeculativeTorchBlock extends TorchBlock implements IDataGenLoot {
    public SpeculativeTorchBlock(Properties properties) {
        super(properties, null);
    }

    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.7D;
        double d2 = (double) pos.getZ() + 0.5D;
        worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(SpeculativeParticleTypes.GREEN_FLAME.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public LootTable.Builder makeLootTable() {
        return Helper.createSingleItemTable(SpeculativeItems.GREENSTONE_TORCH.get());
    }

    public static class SpeculativeWallTorchBlock extends WallTorchBlock {
        public SpeculativeWallTorchBlock(Properties properties) {
            super(properties, null);
        }

        @Override
        public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
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