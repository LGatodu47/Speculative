package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.world.biome.*;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class SpeculativeBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Speculative.MODID);

    private static final Map<ResourceLocation, SpeculativeBiome> SPECULATIVE_BIOME_MAP = new HashMap<>();

    public static final RegistryObject<Biome> SPECULO_PLAINS = register("speculo_plains", new SpeculoPlainsBiome());
    public static final RegistryObject<Biome> SPECULO_FOREST = register("speculo_forest", new SpeculoForestBiome());
    public static final RegistryObject<Biome> SPECULO_MOUNTAINS = register("speculo_mountains", new SpeculoMountainsBiome());
    public static final RegistryObject<Biome> SPECULO_DESERT = register("speculo_desert", new SpeculoDesertBiome());

    private static RegistryObject<Biome> register(String name, SpeculativeBiome biome) {
        if(SPECULATIVE_BIOME_MAP.putIfAbsent(new ResourceLocation(Speculative.MODID, name), biome) != null) {
            throw new IllegalStateException("Tried to register an already present Speculative biome! id: '" + name + "'.");
        }

        return BIOMES.register(name, biome::build);
    }

    public static Optional<SpeculativeBiome> getSpeculativeBiome(ResourceLocation id) {
        return Optional.ofNullable(SPECULATIVE_BIOME_MAP.get(id));
    }

    public static void addTypes() {
        BIOMES.getEntries().forEach(obj -> getSpeculativeBiome(obj.getId()).ifPresent(biome -> addBiomeTypes(obj, Util.make(new HashSet<>(), biome::addTypes).toArray(new Type[0]))));
    }

    private static void addBiomeTypes(RegistryObject<Biome> biome, Type... types) {
        BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, biome.getId()), types);
    }

    private static void addOverworldBiomeTypes(RegistryObject<Biome> biome, BiomeManager.BiomeType type, int weight, Type... types) {
        RegistryKey<Biome> key = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, biome.getId());
        BiomeDictionary.addTypes(key, types);
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(key, weight));
    }
}