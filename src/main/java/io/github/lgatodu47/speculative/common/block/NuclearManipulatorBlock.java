package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.common.block.entity.NuclearManipulatorBlockEntity;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlockEntityTypes;
import io.github.lgatodu47.speculative.data.loot.ISelfDropBlockLoot;
import io.github.lgatodu47.speculative.data.models.IDataGenBlockState;
import io.github.lgatodu47.speculative.data.models.ModelFileHelper;
import io.github.lgatodu47.speculative.data.tags.IHarvestableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class NuclearManipulatorBlock extends Block implements ISpeculativeEntityBlock, ISelfDropBlockLoot, IHarvestableBlock {
    public NuclearManipulatorBlock() {
        super(Properties.of(Material.HEAVY_METAL).strength(7F, 15F).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops());
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return SpeculativeBlockEntityTypes.NUCLEAR_MANIPULATOR.get();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SpeculativeBlockEntityTypes.NUCLEAR_MANIPULATOR.get().create(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if(be instanceof NuclearManipulatorBlockEntity manipulator) {
                NetworkHooks.openGui((ServerPlayer) pPlayer, manipulator, pPos);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public TierType getTierType() {
        return TierType.IRON;
    }

    @Nullable
    @Override
    public ToolType getToolType() {
        return ToolType.PICKAXE;
    }
}
