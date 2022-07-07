package io.github.lgatodu47.speculative.common.world.feature;

import com.mojang.serialization.Codec;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.init.SpeculativeConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class OasisFeature extends Feature<BlockStateConfiguration> {
    public OasisFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
        setRegistryName("oasis");
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> ctx) {
        return place(ctx.level(), ctx.chunkGenerator(), ctx.random(), ctx.origin(), ctx.config());
    }

    public boolean place(WorldGenLevel worldIn, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateConfiguration config) {
        while (pos.getY() > 5 && worldIn.isEmptyBlock(pos)) {
            pos = pos.below();
        }

        if (pos.getY() <= 4) {
            return false;
        } else {
            BlockState top = worldIn.getBlockState(pos);
            pos = pos.below(4);

            boolean[] poses = new boolean[2048];
            int i = rand.nextInt(4) + 4;

            for (int j = 0; j < i; ++j) {
                double d0 = rand.nextDouble() * 6.0D + 3.0D;
                double d1 = rand.nextDouble() * 4.0D + 2.0D;
                double d2 = rand.nextDouble() * 6.0D + 3.0D;
                double d3 = rand.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = rand.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = rand.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                for (int l = 1; l < 15; ++l) {
                    for (int i1 = 1; i1 < 15; ++i1) {
                        for (int j1 = 1; j1 < 7; ++j1) {
                            double d6 = ((double) l - d3) / (d0 / 2.0D);
                            double d7 = ((double) j1 - d4) / (d1 / 2.0D);
                            double d8 = ((double) i1 - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                            if (d9 < 1.0D) {
                                poses[(l * 16 + i1) * 8 + j1] = true;
                            }
                        }
                    }
                }
            }

            for (int x = 0; x < 16; ++x) {
                for (int z = 0; z < 16; ++z) {
                    for (int y = 0; y < 8; ++y) {
                        boolean flag = !poses[(x * 16 + z) * 8 + y]
                                && (x < 15 && poses[((x + 1) * 16 + z) * 8 + y] || x > 0 && poses[((x - 1) * 16 + z) * 8 + y] || z < 15 && poses[(x * 16 + z + 1) * 8 + y]
                                || z > 0 && poses[(x * 16 + (z - 1)) * 8 + y] || y < 7 && poses[(x * 16 + z) * 8 + y + 1] || y > 0 && poses[(x * 16 + z) * 8 + (y - 1)]);
                        if (flag) {
                            Material material = worldIn.getBlockState(pos.offset(x, y, z)).getMaterial();
                            if (y >= 4 && material.isLiquid()) {
                                return false;
                            }

                            if (y < 4 && !material.isSolid() && worldIn.getBlockState(pos.offset(x, y, z)) != config.state) {
                                return false;
                            }
                        }
                    }
                }
            }

            for (int x = 0; x < 16; ++x) {
                for (int z = 0; z < 16; ++z) {
                    for (int y = 0; y < 8; ++y) {
                        if (poses[(x * 16 + z) * 8 + y]) {
                            worldIn.setBlock(pos.offset(x, y, z), y >= 4 ? Blocks.CAVE_AIR.defaultBlockState() : config.state, 2);
                        }
                    }
                }
            }

            boolean treePlaced = false;
            for (int x = 0; x < 16; ++x) {
                for (int z = 0; z < 16; ++z) {
                    for (int y = 4; y < 8; ++y) {
                        if (poses[(x * 16 + z) * 8 + y]) {
                            BlockPos blockpos = pos.offset(x, y - 1, z);
                            if (isDirt(worldIn.getBlockState(blockpos)) || worldIn.getBlockState(blockpos).getBlock().equals(SpeculativeBlocks.SPECULO_SAND.get()) && worldIn.getBrightness(LightLayer.SKY, pos.offset(x, y, z)) > 0) {
                                if(treePlaced) {
                                    worldIn.setBlock(blockpos, top, 2);
                                }
                                else {
                                    treePlaced = SpeculativeConfiguredFeatures.TOURMALINE_TREE.getConfiguredFeature().value().place(worldIn, generator, rand, blockpos.offset(0, 1, 0));
                                }
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
