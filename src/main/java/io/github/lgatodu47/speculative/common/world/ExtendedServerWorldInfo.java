package io.github.lgatodu47.speculative.common.world;

import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.INBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.storage.IServerWorldInfo;

import java.util.function.Supplier;

public interface ExtendedServerWorldInfo {
    void addDimensionWorldInfo(RegistryKey<World> world, DimensionWorldInfo info);

    DimensionWorldInfo getDimensionWorldInfo(RegistryKey<World> world, Supplier<DimensionWorldInfo> defaultValue);

    void deserializeDimensionWorldInfo(Dynamic<INBT> dynamic);

    static ExtendedServerWorldInfo get(IServerWorldInfo info) {
        return (ExtendedServerWorldInfo) info;
    }
}
