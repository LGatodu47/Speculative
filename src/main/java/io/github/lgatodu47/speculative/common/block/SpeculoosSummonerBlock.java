package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.common.init.SpeculativeTileEntityTypes;
import io.github.lgatodu47.speculative.common.tile.SpeculoosSummonerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;

public class SpeculoosSummonerBlock extends Block {
    public SpeculoosSummonerBlock() {
        super(Properties.of(Material.METAL).strength(4.0F, 5.0F).harvestLevel(1).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return SpeculativeTileEntityTypes.SPECULOOS_SUMMONER_TILE_ENTITY.get().create();
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
        if (!worldIn.isClientSide) {
            TileEntity tile = worldIn.getBlockEntity(pos);
            if (tile instanceof SpeculoosSummonerTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (SpeculoosSummonerTileEntity) tile, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tile = worldIn.getBlockEntity(pos);
            if (tile instanceof SpeculoosSummonerTileEntity) {
                InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((SpeculoosSummonerTileEntity) tile).getStack());
            }

            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
