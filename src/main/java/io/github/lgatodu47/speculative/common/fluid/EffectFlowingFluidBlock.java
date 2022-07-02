package io.github.lgatodu47.speculative.common.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class EffectFlowingFluidBlock extends FlowingFluidBlock {
    private final Supplier<EffectInstance> effect;

    public EffectFlowingFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties, Supplier<EffectInstance> effect) {
        super(supplier, properties);
        this.effect = effect;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            ((LivingEntity) entityIn).addPotionEffect(effect.get());
        }
    }
}
