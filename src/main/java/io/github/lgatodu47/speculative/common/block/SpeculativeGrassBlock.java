package io.github.lgatodu47.speculative.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.Random;
import java.util.function.Supplier;

public class SpeculativeGrassBlock extends Block {
    private final Supplier<Block> dirt;

    public SpeculativeGrassBlock(Supplier<Block> dirt) {
        super(Properties.create(Material.ORGANIC).tickRandomly().hardnessAndResistance(0.6F).sound(SoundType.PLANT));
        this.dirt = dirt;
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return true;
    }

    private static boolean canStay(BlockState state, IWorldReader reader, BlockPos pos) {
        BlockPos blockpos = pos.up();
        BlockState blockstate = reader.getBlockState(blockpos);
        if (blockstate.getBlock() == Blocks.SNOW && blockstate.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else {
            int i = LightEngine.func_215613_a(reader, state, pos, blockstate, blockpos, Direction.UP, blockstate.getOpacity(reader, blockpos));
            return i < reader.getMaxLightLevel();
        }
    }

    private static boolean canStayGrass(BlockState state, IWorldReader reader, BlockPos pos) {
        BlockPos blockpos = pos.up();
        return canStay(state, reader, pos) && !reader.getFluidState(blockpos).isTagged(FluidTags.WATER);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!canStay(state, worldIn, pos)) {
            if (!worldIn.isAreaLoaded(pos, 3))
                return;

            worldIn.setBlockState(pos, this.dirt.get().getDefaultState());
        } else {
            if (worldIn.getLight(pos.up()) >= 9) {
                BlockState blockstate = this.getDefaultState();

                for (int i = 0; i < 4; ++i) {
                    BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
                    if (worldIn.getBlockState(blockpos).getBlock() == this.dirt.get() && canStayGrass(blockstate, worldIn, blockpos)) {
                        worldIn.setBlockState(blockpos, blockstate);
                    }
                }
            }

        }
    }
}
