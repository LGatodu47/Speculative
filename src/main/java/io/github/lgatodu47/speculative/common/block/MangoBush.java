package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.common.init.SpeculativeItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class MangoBush extends Block implements IGrowable {
    private static final IntegerProperty AGE = BlockStateProperties.AGE_1;

    public MangoBush() {
        super(Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).noOcclusion().randomTicks());
        registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.getValue(AGE) == 1) {
            player.addItem(new ItemStack(SpeculativeItems.STRANGE_MANGO.get()));
            worldIn.setBlock(pos, state.setValue(AGE, 0), 2);
            player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 0.5F, 2F);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!this.canSurvive(state, worldIn, pos)) {
            worldIn.destroyBlock(pos, false);
        } else {
            int chance = rand.nextInt(10);
            if (chance == 0) {
                this.performBonemeal(worldIn, rand, pos, state);
            }
        }
    }

    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return (worldIn.getRawBrightness(pos, 0) >= 8 || worldIn.canSeeSky(pos));
    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(AGE) < 1;
    }

    @Override
    public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlock(pos, state.setValue(AGE, 1), 2);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AGE);
    }
}
