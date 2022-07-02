package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.world.dimension.speculo_world.SpeculoWorldDimensionSettings;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class SpeculativeWorldTypes {
    public static final DeferredRegister<ForgeWorldType> WORLD_TYPES = DeferredRegister.create(ForgeRegistries.WORLD_TYPES, Speculative.MODID);

    public static final RegistryObject<ForgeWorldType> SPECULO_WORLD = WORLD_TYPES.register("speculo_world", () -> new ForgeWorldType((biomeRegistry, dimensionSettingsRegistry, seed) ->
            new NoiseChunkGenerator(new OverworldBiomeProvider(seed, false, false, biomeRegistry), seed, new SpeculoWorldDimensionSettings()::createSettings)));
}
