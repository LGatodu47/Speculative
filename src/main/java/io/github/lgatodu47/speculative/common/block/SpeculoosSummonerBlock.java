package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.common.block.entity.SpeculoosSummonerBlockEntity;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class SpeculoosSummonerBlock extends Block implements ISpeculativeEntityBlock {
    public SpeculoosSummonerBlock() {
        super(Properties.of(Material.METAL).strength(4.0F, 5.0F).sound(SoundType.METAL).requiresCorrectToolForDrops());
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return SpeculativeBlockEntityTypes.SPECULOOS_SUMMONER.get();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SpeculativeBlockEntityTypes.SPECULOOS_SUMMONER.get().create(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult result) {
        if (!worldIn.isClientSide) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if (tile instanceof SpeculoosSummonerBlockEntity) {
                NetworkHooks.openGui((ServerPlayer) player, (SpeculoosSummonerBlockEntity) tile, pos);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if (tile instanceof SpeculoosSummonerBlockEntity) {
                Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((SpeculoosSummonerBlockEntity) tile).getStack());
            }

            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
