package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.common.init.SpeculativeTileEntityTypes;
import io.github.lgatodu47.speculative.common.tile.BossSummonerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BossSummonerBlock extends Block {
    public BossSummonerBlock() {
        super(Properties.create(Material.GLASS, MaterialColor.GOLD).hardnessAndResistance(-1).setLightLevel(state -> 12).noDrops().notSolid());
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return SpeculativeTileEntityTypes.BOSS_SUMMONER.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof BossSummonerTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (BossSummonerTileEntity) tile, pos);
            }
        }
        return ActionResultType.SUCCESS;
    }
}