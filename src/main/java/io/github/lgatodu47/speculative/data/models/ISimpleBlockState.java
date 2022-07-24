package io.github.lgatodu47.speculative.data.models;

import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.Function;

public interface ISimpleBlockState extends IDataGenBlockState.BlockImpl {
    @Override
    default void createBlockState(BlockStateProvider provider, Function<ModelFile[], ModelFileHelper> helperFactory) {
        provider.simpleBlock(impl_asBlock());
    }
}
