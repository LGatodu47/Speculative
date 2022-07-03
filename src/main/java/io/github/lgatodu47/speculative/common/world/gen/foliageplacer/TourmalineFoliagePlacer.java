package io.github.lgatodu47.speculative.common.world.gen.foliageplacer;

import io.github.lgatodu47.speculative.common.init.SpeculativePlacerTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

import net.minecraft.world.gen.foliageplacer.FoliagePlacer.Foliage;

public class TourmalineFoliagePlacer extends FoliagePlacer {
    public TourmalineFoliagePlacer(int radius, int radiusRandom) {
        super(FeatureSpread.fixed(radius), FeatureSpread.fixed(radiusRandom));
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return SpeculativePlacerTypes.Foliage.TOURMALINE.get();
    }

    //place
    @Override
    protected void createFoliage(IWorldGenerationReader reader, Random rand, BaseTreeFeatureConfig config, int baseHeight, Foliage foliage, int p, int p1, Set<BlockPos> poses, int p_230372_9_, MutableBoundingBox box) {
        boolean flag = foliage.doubleTrunk();
        BlockPos blockpos = foliage.foliagePos().above(p_230372_9_);
        this.placeLeavesRow(reader, rand, config, blockpos, p1 + foliage.radiusOffset(), poses, -1 - p, flag, box);
        this.placeLeavesRow(reader, rand, config, blockpos, p1 - 1, poses, -p, flag, box);
        this.placeLeavesRow(reader, rand, config, blockpos, p1 + foliage.radiusOffset() - 1, poses, 0, flag, box);
    }

    @Override
    public int foliageHeight(Random rand, int p_230374_2_, BaseTreeFeatureConfig config) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(Random p_230373_1_, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_) {
        if (p_230373_3_ == 0) {
            return (p_230373_2_ > 1 || p_230373_4_ > 1) && p_230373_2_ != 0 && p_230373_4_ != 0;
        } else {
            return p_230373_2_ == p_230373_5_ && p_230373_4_ == p_230373_5_ && p_230373_5_ > 0;
        }
    }
}
