package io.github.lgatodu47.speculative.common.block;

import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class SpeculativeOre extends OreBlock {
    protected int minXp;
    protected int maxXp;

    public SpeculativeOre(float hardness, float resistance, int harvestLevel) {
        super(Properties.create(Material.ROCK).hardnessAndResistance(hardness, resistance).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE));
    }

    public SpeculativeOre xp(int min, int max) {
        this.minXp = min;
        this.maxXp = max;
        return this;
    }

    @Override
    protected int getExperience(Random rand) {
        if ((minXp < 1 || maxXp < 1) || (maxXp - minXp <= 0)) {
            return 0;
        }
        return MathHelper.nextInt(rand, minXp, maxXp);
    }
}
