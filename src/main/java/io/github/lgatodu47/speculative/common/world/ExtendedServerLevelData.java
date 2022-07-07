package io.github.lgatodu47.speculative.common.world;

import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ServerLevelData;

import java.util.function.Supplier;

public interface ExtendedServerLevelData {
    void addDimensionWorldInfo(ResourceKey<Level> world, DimensionLevelData info);

    DimensionLevelData getDimensionWorldInfo(ResourceKey<Level> world, Supplier<DimensionLevelData> defaultValue);

    void deserializeDimensionWorldInfo(Dynamic<Tag> dynamic);

    static ExtendedServerLevelData get(ServerLevelData info) {
        return (ExtendedServerLevelData) info;
    }
}
