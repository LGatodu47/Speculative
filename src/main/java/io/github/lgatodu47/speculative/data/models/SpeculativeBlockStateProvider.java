package io.github.lgatodu47.speculative.data.models;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.data.DataGenerationContext;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.registries.RegistryObject;

// Unused
public class SpeculativeBlockStateProvider extends BlockStateProvider {
    private final DataGenerationContext ctx;

    public SpeculativeBlockStateProvider(DataGenerationContext ctx) {
        super(ctx.generator(), ctx.modid(), ctx.existingFileHelper());
        this.ctx = ctx;
    }

    @Override
    protected void registerStatesAndModels() {
        SpeculativeBlocks.BLOCKS.getEntries()
                .stream()
                .filter(RegistryObject::isPresent)
                .map(RegistryObject::get)
                .filter(IDataGenBlockState.class::isInstance)
                .map(IDataGenBlockState.class::cast)
                .forEach(block -> block.createBlockState(this, modelFiles -> new ModelFileHelper(ctx.modid(), ctx.existingFileHelper(), modelFiles)));
    }
}
