package io.github.lgatodu47.speculative.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;

public class DimensionUtil {
    private static final Set<ResourceKey<Level>> DIMENSION_WORLD_INFO = new HashSet<>();

    public static void registerDimensionWorldInfo(ResourceKey<Level> world) {
        DIMENSION_WORLD_INFO.add(world);
    }

    public static boolean hasDimensionWorldInfo(ResourceKey<Level> world) {
        return DIMENSION_WORLD_INFO.contains(world);
    }
}
