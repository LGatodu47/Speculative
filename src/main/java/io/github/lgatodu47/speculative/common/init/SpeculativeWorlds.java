package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class SpeculativeWorlds
{
    public static final ResourceKey<Level> SPECULO_WORLD = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Speculative.MODID, "speculo_world"));
//    public static final RegistryKey<DimensionType> SPECULO_WORLD_DIMENSION = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, new ResourceLocation(Speculative.MODID, "speculo_world"));
}
