package io.github.lgatodu47.speculative.common.world.gen.foliageplacer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.lgatodu47.speculative.common.init.SpeculativePlacerTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class SpeculativeTrunkPlacer extends AbstractTrunkPlacer {
    public static final Codec<SpeculativeTrunkPlacer> CODEC = RecordCodecBuilder.create((builder) -> getAbstractTrunkCodec(builder).apply(builder, SpeculativeTrunkPlacer::new));

    public SpeculativeTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> getPlacerType() {
        return SpeculativePlacerTypes.Trunk.SPECULATIVE;
    }

    @Override
    public List<FoliagePlacer.Foliage> getFoliages(IWorldGenerationReader reader, Random rand, int treeHeight, BlockPos pos, Set<BlockPos> leaves, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
        for(int i = 0; i < treeHeight; ++i) {
            func_236911_a_(reader, rand, pos.up(i), leaves, bb, config);
        }

        return ImmutableList.of(new FoliagePlacer.Foliage(pos.up(treeHeight), 0, false));
    }
}
