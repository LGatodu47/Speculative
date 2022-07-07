package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.common.block.entity.BossSummonerBlockEntity;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class BossSummonerBlock extends Block implements ISpeculativeEntityBlock {
    public BossSummonerBlock() {
        super(Properties.of(Material.GLASS, MaterialColor.GOLD).strength(-1).lightLevel(state -> 12).noDrops().noOcclusion());
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return SpeculativeBlockEntityTypes.BOSS_SUMMONER.get();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SpeculativeBlockEntityTypes.BOSS_SUMMONER.get().create(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!worldIn.isClientSide) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if (tile instanceof BossSummonerBlockEntity) {
                NetworkHooks.openGui((ServerPlayer) player, (BossSummonerBlockEntity) tile, pos);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
