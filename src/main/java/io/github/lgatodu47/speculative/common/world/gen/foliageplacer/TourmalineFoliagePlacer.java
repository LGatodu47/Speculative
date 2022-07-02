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

public class TourmalineFoliagePlacer extends FoliagePlacer {
    public TourmalineFoliagePlacer(int radius, int radiusRandom) {
        super(FeatureSpread.create(radius), FeatureSpread.create(radiusRandom));
    }

    @Override
    protected FoliagePlacerType<?> getPlacerType() {
        return SpeculativePlacerTypes.Foliage.TOURMALINE.get();
    }

    //place
    @Override
    protected void func_230372_a_(IWorldGenerationReader reader, Random rand, BaseTreeFeatureConfig config, int baseHeight, Foliage foliage, int p, int p1, Set<BlockPos> poses, int p_230372_9_, MutableBoundingBox box) {
        boolean flag = foliage.func_236765_c_();
        BlockPos blockpos = foliage.func_236763_a_().up(p_230372_9_);
        this.func_236753_a_(reader, rand, config, blockpos, p1 + foliage.func_236764_b_(), poses, -1 - p, flag, box);
        this.func_236753_a_(reader, rand, config, blockpos, p1 - 1, poses, -p, flag, box);
        this.func_236753_a_(reader, rand, config, blockpos, p1 + foliage.func_236764_b_() - 1, poses, 0, flag, box);
    }

    @Override
    public int func_230374_a_(Random rand, int p_230374_2_, BaseTreeFeatureConfig config) {
        return 0;
    }

    @Override
    protected boolean func_230373_a_(Random p_230373_1_, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_) {
        if (p_230373_3_ == 0) {
            return (p_230373_2_ > 1 || p_230373_4_ > 1) && p_230373_2_ != 0 && p_230373_4_ != 0;
        } else {
            return p_230373_2_ == p_230373_5_ && p_230373_4_ == p_230373_5_ && p_230373_5_ > 0;
        }
    }
}
