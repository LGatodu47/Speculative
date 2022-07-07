package io.github.lgatodu47.speculative.common.world.gen.foliageplacer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.lgatodu47.speculative.common.init.SpeculativePlacerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.Random;
import java.util.function.BiConsumer;

public class TourmalineFoliagePlacer extends FoliagePlacer {
    public static final Codec<TourmalineFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> foliagePlacerParts(instance).apply(instance, TourmalineFoliagePlacer::new));

    public TourmalineFoliagePlacer(int radius, int offset) {
        this(ConstantInt.of(radius), ConstantInt.of(offset));
    }

    public TourmalineFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return SpeculativePlacerTypes.Foliage.TOURMALINE.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, Random rand, TreeConfiguration config, int pMaxFreeTreeHeight, FoliageAttachment pAttachment, int pFoliageHeight, int pFoliageRadius, int pOffset) {
        boolean flag = pAttachment.doubleTrunk();
        BlockPos blockpos = pAttachment.pos().above(pOffset);
        this.placeLeavesRow(pLevel, pBlockSetter, rand, config, blockpos, pFoliageRadius + pAttachment.radiusOffset(), -1 - pFoliageHeight, flag);
        this.placeLeavesRow(pLevel, pBlockSetter, rand, config, blockpos, pFoliageRadius - 1, -pFoliageHeight, flag);
        this.placeLeavesRow(pLevel, pBlockSetter, rand, config, blockpos, pFoliageRadius + pAttachment.radiusOffset() - 1, 0, flag);
    }

    @Override
    public int foliageHeight(Random rand, int p_230374_2_, TreeConfiguration config) {
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
