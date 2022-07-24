package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.world.biome.*;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class SpeculativeBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Speculative.MODID);

    private static final Map<ResourceLocation, Supplier<SpeculativeBiome>> SPECULATIVE_BIOME_MAP = new HashMap<>();

    public static final RegistryObject<Biome> SPECULO_PLAINS = register("speculo_plains", SpeculoPlainsBiome::new);
    public static final RegistryObject<Biome> SPECULO_FOREST = register("speculo_forest", SpeculoForestBiome::new);
    public static final RegistryObject<Biome> SPECULO_MOUNTAINS = register("speculo_mountains", SpeculoMountainsBiome::new);
    public static final RegistryObject<Biome> SPECULO_DESERT = register("speculo_desert", SpeculoDesertBiome::new);

    private static RegistryObject<Biome> register(String name, Supplier<SpeculativeBiome> biome) {
        if(SPECULATIVE_BIOME_MAP.putIfAbsent(new ResourceLocation(Speculative.MODID, name), biome) != null) {
            throw new IllegalStateException("Tried to register an already present Speculative biome! id: '" + name + "'.");
        }

        return BIOMES.register(name, () -> biome.get().build());
    }

    public static Optional<SpeculativeBiome> getSpeculativeBiome(ResourceLocation id) {
        return Optional.ofNullable(SPECULATIVE_BIOME_MAP.getOrDefault(id, () -> null).get());
    }

    public static void addTypes() {
        BIOMES.getEntries().forEach(obj -> getSpeculativeBiome(obj.getId()).ifPresent(biome -> addBiomeTypes(obj, Util.make(new HashSet<>(), biome::addTypes).toArray(new Type[0]))));
    }

    private static void addBiomeTypes(RegistryObject<Biome> biome, Type... types) {
        BiomeDictionary.addTypes(biome.getKey(), types);
    }

    private static void addOverworldBiomeTypes(RegistryObject<Biome> biome, BiomeManager.BiomeType type, int weight, Type... types) {
        BiomeDictionary.addTypes(biome.getKey(), types);
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(biome.getKey(), weight));
    }

    public static final class Tags {
        public static final TagKey<Biome> HAS_SPECULO_PYRAMID = BIOMES.createTagKey("has_structure/speculo_pyramid");
        public static final TagKey<Biome> HAS_SPECULO_HOUSE = BIOMES.createTagKey("has_structure/speculo_house");
    }
}