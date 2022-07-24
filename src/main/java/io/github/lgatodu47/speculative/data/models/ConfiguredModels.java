package io.github.lgatodu47.speculative.data.models;

import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConfiguredModels {
    private final List<ConfiguredModel> models = new ArrayList<>();

    public void add(ConfiguredModel.Builder<?> builder) {
        this.add(builder.buildLast());
    }

    public void add(ConfiguredModel model) {
        this.models.add(model);
    }

    public void addAll(ConfiguredModel[] models) {
        this.models.addAll(Arrays.asList(models));
    }

    public void fromBuilder(@Nonnull Consumer<ConfiguredModel.Builder<?>> consumer) {
        ConfiguredModel.Builder<?> modelBuilder = ConfiguredModel.builder();
        consumer.accept(modelBuilder);
        this.add(modelBuilder);
    }

    public void withModelFile(ModelFile file) {
        this.add(ConfiguredModel.builder().modelFile(file));
    }

    public ConfiguredModel[] models() {
        return models.toArray(ConfiguredModel[]::new);
    }
}
