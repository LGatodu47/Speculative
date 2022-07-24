package io.github.lgatodu47.speculative.data.models;

import net.minecraft.Util;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.Function;

public interface IDataGenVariantsState extends IDataGenBlockState.BlockImpl {
    @Override
    default void createBlockState(BlockStateProvider provider, Function<ModelFile[], ModelFileHelper> helperFactory) {
        ModelFileHelper helper = helperFactory.apply(Util.make(new ModelFileList(), list -> makeBlockModels(provider.models(), list)).modelFiles());
        provider.getVariantBuilder(impl_asBlock()).forAllStates(state -> {
            ConfiguredModels models = new ConfiguredModels();
            assignModelsToState(state, models, helper);
            return models.models();
        });
    }

    void assignModelsToState(BlockState state, ConfiguredModels models, ModelFileHelper helper);

    void makeBlockModels(BlockModelProvider provider, ModelFileList list);
}
