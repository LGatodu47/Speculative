package io.github.lgatodu47.speculative.util;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class DimensionUtil {
    private static final Set<RegistryKey<World>> DIMENSION_WORLD_INFO = new HashSet<>();

    public static void registerDimensionWorldInfo(RegistryKey<World> world) {
        DIMENSION_WORLD_INFO.add(world);
    }

    public static boolean hasDimensionWorldInfo(RegistryKey<World> world) {
        return DIMENSION_WORLD_INFO.contains(world);
    }
}
