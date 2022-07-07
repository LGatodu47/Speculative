package io.github.lgatodu47.speculative.common.world.feature;

import com.mojang.serialization.Codec;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.Random;

public class SpeculativeLakeFeature extends Feature<BlockStateConfiguration> {
    public SpeculativeLakeFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
        setRegistryName("lake");
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

            for (int k1 = 0; k1 < 16; ++k1) {
                for (int l2 = 0; l2 < 16; ++l2) {
                    for (int k = 0; k < 8; ++k) {
                        boolean flag = !poses[(k1 * 16 + l2) * 8 + k]
                                && (k1 < 15 && poses[((k1 + 1) * 16 + l2) * 8 + k] || k1 > 0 && poses[((k1 - 1) * 16 + l2) * 8 + k] || l2 < 15 && poses[(k1 * 16 + l2 + 1) * 8 + k]
                                || l2 > 0 && poses[(k1 * 16 + (l2 - 1)) * 8 + k] || k < 7 && poses[(k1 * 16 + l2) * 8 + k + 1] || k > 0 && poses[(k1 * 16 + l2) * 8 + (k - 1)]);
                        if (flag) {
                            Material material = worldIn.getBlockState(pos.offset(k1, k, l2)).getMaterial();
                            if (k >= 4 && material.isLiquid()) {
                                return false;
                            }

                            if (k < 4 && !material.isSolid() && worldIn.getBlockState(pos.offset(k1, k, l2)) != config.state) {
                                return false;
                            }
                        }
                    }
                }
            }

            for (int l1 = 0; l1 < 16; ++l1) {
                for (int i3 = 0; i3 < 16; ++i3) {
                    for (int i4 = 0; i4 < 8; ++i4) {
                        if (poses[(l1 * 16 + i3) * 8 + i4]) {
                            worldIn.setBlock(pos.offset(l1, i4, i3), i4 >= 4 ? Blocks.CAVE_AIR.defaultBlockState() : config.state, 2);
                        }
                    }
                }
            }

            for (int i2 = 0; i2 < 16; ++i2) {
                for (int j3 = 0; j3 < 16; ++j3) {
                    for (int j4 = 4; j4 < 8; ++j4) {
                        if (poses[(i2 * 16 + j3) * 8 + j4]) {
                            BlockPos blockpos = pos.offset(i2, j4 - 1, j3);
                            if (isDirt(worldIn.getBlockState(blockpos)) && worldIn.getBrightness(LightLayer.SKY, pos.offset(i2, j4, j3)) > 0) {
                                worldIn.setBlock(blockpos, top, 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
