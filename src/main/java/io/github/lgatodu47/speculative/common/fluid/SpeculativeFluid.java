package io.github.lgatodu47.speculative.common.fluid;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static io.github.lgatodu47.speculative.common.init.SpeculativeBlocks.BLOCKS;
import static io.github.lgatodu47.speculative.common.init.SpeculativeFluids.FLUIDS;

public class SpeculativeFluid {
    private final RegistryObject<FlowingFluidBlock> block;
    private final RegistryObject<FlowingFluid> still;
    private final RegistryObject<FlowingFluid> flowing;
    private final ForgeFlowingFluid.Properties properties;

    private SpeculativeFluid(Builder builder) {
        this.block = BLOCKS.register(builder.blockName, () -> builder.blockFactory.create(getStillFluid(), builder.blockProperties));
        this.still = FLUIDS.register(builder.stillFluidName, () -> builder.stillFactory.create(true, getProperties(), builder.attributesBuilder));
        this.flowing = FLUIDS.register(builder.flowingFluidName, () -> builder.flowingFactory.create(false, getProperties(), builder.attributesBuilder));
        this.properties = builder.fluidPropertiesOperator.apply(new ForgeFlowingFluid.Properties(getStillFluid(), getFlowingFluid(), builder.attributesBuilder).block(getBlock()));
    }

    public RegistryObject<FlowingFluidBlock> getBlock() {
        return block;
    }

    public RegistryObject<FlowingFluid> getStillFluid() {
        return still;
    }

    public RegistryObject<FlowingFluid> getFlowingFluid() {
        return flowing;
    }

    public ForgeFlowingFluid.Properties getProperties() {
        return properties;
    }

    public static class Builder {
        private String blockName;
        private String stillFluidName;
        private String flowingFluidName;
        private final FluidAttributes.Builder attributesBuilder;
        private AbstractBlock.Properties blockProperties = Block.Properties.of(Material.WATER).strength(100F).noDrops();
        private UnaryOperator<ForgeFlowingFluid.Properties> fluidPropertiesOperator = UnaryOperator.identity();
        private FluidFactory stillFactory = SpeculativeFlowingFluid::new;
        private FluidFactory flowingFactory = SpeculativeFlowingFluid::new;
        private FluidBlockFactory blockFactory = FlowingFluidBlock::new;

        public Builder(String name, FluidAttributes.Builder attributesBuilder) {
            this.blockName = name;
            this.stillFluidName = name.concat("_still");
            this.flowingFluidName = name.concat("_flowing");
            this.attributesBuilder = attributesBuilder;
        }

        public Builder setBlockName(String name) {
            this.blockName = name;
            return this;
        }

        public Builder setStillFluidName(String name) {
            this.stillFluidName = name;
            return this;
        }

        public Builder setFlowingFluidName(String name) {
            this.flowingFluidName = name;
            return this;
        }

        public Builder setBlockProperties(@Nonnull AbstractBlock.Properties blockProperties) {
            this.blockProperties = blockProperties;
            return this;
        }

        public Builder withBlockProperties(@Nonnull UnaryOperator<AbstractBlock.Properties> operator) {
            return setBlockProperties(operator.apply(this.blockProperties));
        }

        public Builder withProperties(@Nonnull UnaryOperator<ForgeFlowingFluid.Properties> operator) {
            this.fluidPropertiesOperator = operator;
            return this;
        }

        public Builder setStillFactory(@Nonnull FluidFactory factory) {
            this.stillFactory = factory;
            return this;
        }

        public Builder setFlowingFactory(@Nonnull FluidFactory factory) {
            this.flowingFactory = factory;
            return this;
        }

        public Builder setBlockFactory(@Nonnull FluidBlockFactory factory) {
            this.blockFactory = factory;
            return this;
        }

        public SpeculativeFluid build() {
            return new SpeculativeFluid(this);
        }
    }

    public interface FluidFactory {
        ForgeFlowingFluid create(boolean isSource, ForgeFlowingFluid.Properties properties, FluidAttributes.Builder attributes);
    }

    public interface FluidBlockFactory {
        FlowingFluidBlock create(Supplier<? extends FlowingFluid> fluidSupplier, AbstractBlock.Properties properties);
    }
}
