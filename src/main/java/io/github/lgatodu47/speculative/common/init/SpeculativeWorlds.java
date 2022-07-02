package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class SpeculativeWorlds
{
    public static final RegistryKey<World> SPECULO_WORLD = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(Speculative.MODID, "speculo_world"));
//    public static final RegistryKey<DimensionType> SPECULO_WORLD_DIMENSION = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, new ResourceLocation(Speculative.MODID, "speculo_world"));
}
