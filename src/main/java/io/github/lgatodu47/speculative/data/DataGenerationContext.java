package io.github.lgatodu47.speculative.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public record DataGenerationContext(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
    public void addProvider(DataProvider provider) {
        generator().addProvider(provider);
    }
}
