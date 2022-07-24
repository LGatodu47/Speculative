package io.github.lgatodu47.speculative.data.models;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModelFileHelper {
    private final String modid;
    private final ExistingFileHelper existingFileHelper;
    private final ModelFile[] modelFiles;

    public ModelFileHelper(String modid, ExistingFileHelper existingFileHelper, ModelFile[] modelFiles) {
        this.modid = modid;
        this.existingFileHelper = existingFileHelper;
        this.modelFiles = modelFiles;
    }

    public ModelFile generated(int index) {
        if(index >= modelFiles.length) {
            throw new IndexOutOfBoundsException("No generated model at index '" + index + "'!");
        }
        return modelFiles[index];
    }

    public ModelFile unchecked(String name) {
        return new ModelFile.UncheckedModelFile(new ResourceLocation(modid, name));
    }

    public ModelFile existing(String name) {
        return new ModelFile.ExistingModelFile(new ResourceLocation(modid, name), existingFileHelper);
    }
}
