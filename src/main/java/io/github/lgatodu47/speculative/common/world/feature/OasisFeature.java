package io.github.lgatodu47.speculative.common.world.feature;

import com.mojang.serialization.Codec;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;

import java.util.Random;

public class OasisFeature extends Feature<BlockStateFeatureConfig> {
    public OasisFeature(Codec<BlockStateFeatureConfig> codec) {
        super(codec);
        setRegistryName("oasis");
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateFeatureConfig config) {
        while (pos.getY() > 5 && worldIn.isAirBlock(pos)) {
            pos = pos.down();
        }

        if (pos.getY() <= 4) {
            return false;
        } else {
            pos = pos.down(4);
            ChunkPos chunkpos = new ChunkPos(pos);
            if (!worldIn.getChunk(chunkpos.x, chunkpos.z, ChunkStatus.STRUCTURE_REFERENCES).getStructureReferences().get(Structure.VILLAGE).isEmpty()) {
                return false;
            } else {
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
                                Material material = worldIn.getBlockState(pos.add(x, y, z)).getMaterial();
                                if (y >= 4 && material.isLiquid()) {
                                    return false;
                                }

                                if (y < 4 && !material.isSolid() && worldIn.getBlockState(pos.add(x, y, z)) != config.state) {
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
                                worldIn.setBlockState(pos.add(x, y, z), y >= 4 ? Blocks.CAVE_AIR.getDefaultState() : config.state, 2);
                            }
                        }
                    }
                }

                boolean treePlaced = false;
                for (int x = 0; x < 16; ++x) {
                    for (int z = 0; z < 16; ++z) {
                        for (int y = 4; y < 8; ++y) {
                            if (poses[(x * 16 + z) * 8 + y]) {
                                BlockPos blockpos = pos.add(x, y - 1, z);
                                if (isDirt(worldIn.getBlockState(blockpos).getBlock()) || worldIn.getBlockState(blockpos).getBlock().equals(SpeculativeBlocks.SPECULO_SAND.get()) && worldIn.getLightFor(LightType.SKY, pos.add(x, y, z)) > 0) {
                                    Biome biome = worldIn.getBiome(blockpos);
                                    worldIn.setBlockState(blockpos, biome.getGenerationSettings().getSurfaceBuilderConfig().getTop().getBlock().getDefaultState(), 2);
                                    if (!treePlaced) {
                                        worldIn.setBlockState(blockpos, Blocks.GRASS_BLOCK.getDefaultState(), 2);
                                        treePlaced = Feature.TREE.withConfiguration(TourmalineTree.CONFIG).generate(worldIn, generator, rand, blockpos.add(0, 1, 0));
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
}
