package io.github.lgatodu47.speculative.data.models;

import net.minecraftforge.client.model.generators.ModelFile;

import java.util.ArrayList;
import java.util.List;

public class ModelFileList {
    private final List<ModelFile> list = new ArrayList<>();

    public int add(ModelFile file) {
        list.add(file);
        return list.indexOf(file);
    }

    public ModelFile[] modelFiles() {
        return list.toArray(ModelFile[]::new);
    }
}
