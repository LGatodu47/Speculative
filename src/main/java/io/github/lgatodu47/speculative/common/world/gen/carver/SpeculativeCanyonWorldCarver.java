package io.github.lgatodu47.speculative.common.world.gen.carver;

import com.mojang.serialization.Codec;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CanyonWorldCarver;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.function.Function;

public class SpeculativeCanyonWorldCarver extends CanyonWorldCarver {
    public SpeculativeCanyonWorldCarver(Codec<CanyonCarverConfiguration> codec) {
        super(codec);
        setRegistryName("canyon_world_carver");
    }

    @Override
    protected boolean canReplaceBlock(BlockState state) {
        return state.is(SpeculativeBlocks.Tags.CARVABLE_BLOCKS);
    }

    @Override
    protected boolean carveBlock(CarvingContext pContext, CanyonCarverConfiguration pConfig, ChunkAccess pChunk, Function<BlockPos, Holder<Biome>> pBiomeAccessor, CarvingMask pCarvingMask, BlockPos.MutableBlockPos pPos, BlockPos.MutableBlockPos pCheckPos, Aquifer pAquifer, MutableBoolean pReachedSurface) {
        BlockState blockstate = pChunk.getBlockState(pPos);
        if (blockstate.is(Blocks.GRASS_BLOCK) || blockstate.is(Blocks.MYCELIUM)) {
            pReachedSurface.setTrue();
        }

        if (!this.canReplaceBlock(blockstate) && !pConfig.debugSettings.isDebugMode()) {
            return false;
        } else if(pChunk.getFluidState(pPos.above()).isEmpty()) {
            BlockState blockstate1 = ISpeculativeWorldCarver.getCarveState(pContext, pConfig, pPos, pAquifer);
            if (blockstate1 == null) {
                return false;
            } else {
                pChunk.setBlockState(pPos, blockstate1, false);
                if (pAquifer.shouldScheduleFluidUpdate() && !blockstate1.getFluidState().isEmpty()) {
                    pChunk.markPosForPostprocessing(pPos);
                }

                if (pReachedSurface.isTrue()) {
                    pCheckPos.setWithOffset(pPos, Direction.DOWN);
                    if (pChunk.getBlockState(pCheckPos).is(Blocks.DIRT)) {
                        pContext.topMaterial(pBiomeAccessor, pChunk, pCheckPos, !blockstate1.getFluidState().isEmpty()).ifPresent((p_190743_) -> {
                            pChunk.setBlockState(pCheckPos, p_190743_, false);
                            if (!p_190743_.getFluidState().isEmpty()) {
                                pChunk.markPosForPostprocessing(pCheckPos);
                            }

                        });
                    }
                }

                return true;
            }
        }

        return false;
    }
}
