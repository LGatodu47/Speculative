package io.github.lgatodu47.speculative.common.fluid;

import io.github.lgatodu47.speculative.common.init.SpeculativeFluids;
import io.github.lgatodu47.speculative.common.init.SpeculativeSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.Random;

public class SpeculativeFlowingFluid extends ForgeFlowingFluid {
    private final boolean isSource;
    private final FluidAttributes.Builder attributes;

    public SpeculativeFlowingFluid(boolean isSource, Properties properties, FluidAttributes.Builder attributes) {
        super(properties);
        this.isSource = isSource;
        this.attributes = attributes;
    }

    @Override
    public boolean isSource(FluidState state) {
        return this.isSource;
    }

    @Override
    public int getAmount(FluidState state) {
        if (this.isSource) {
            return 8;
        }
        return state.getValue(LEVEL);
    }

    @Override
    protected FluidAttributes createAttributes() {
        return attributes.build(this);
    }

    @Override
    protected void createFluidStateDefinition(Builder<Fluid, FluidState> builder) {
        super.createFluidStateDefinition(builder);
        if (!this.isSource) {
            builder.add(LEVEL);
        }
    }

    @Override
    protected void animateTick(Level level, BlockPos pos, FluidState state, Random rand) {
        if(state.is(SpeculativeFluids.Tags.SULFURIC_WATER)) {
            if (!state.isSource() && !state.getValue(FALLING)) {
                if (rand.nextInt(64) == 0) {
                    level.playSound(null,
                            (double) pos.getX() + 0.5D,
                            (double) pos.getY() + 0.5D,
                            (double) pos.getZ() + 0.5D,
                            SpeculativeSounds.SULFURIC_WATER_AMBIENT.get(),
                            SoundSource.BLOCKS, rand.nextFloat() * 0.25F + 0.75F,
                            rand.nextFloat() + 0.5F);
                }
            }
        }
    }

    /*@OnlyIn(Dist.CLIENT)
    @Override
    protected IParticleData getDripParticleData() {
        return ParticleTypes.ANGRY_VILLAGER;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void animateTick(World worldIn, BlockPos pos, FluidState state, Random random) {
        if (!state.isSource() && !state.get(FALLING)) {
            if (random.nextInt(64) == 0) {
                worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SpeculativeSounds.SULFURIC_WATER_AMBIENT.get(), SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F,
                        random.nextFloat() + 0.5F, false);
            }
        } else if (random.nextInt(10) == 0) {
            worldIn.addParticle(ParticleTypes.COMPOSTER, (double) pos.getX() + (double) random.nextFloat(), (double) pos.getY() + (double) random.nextFloat(), (double) pos.getZ() + (double) random.nextFloat(), 0.0D,
                    0.0D, 0.0D);
        }
    }*/
}
