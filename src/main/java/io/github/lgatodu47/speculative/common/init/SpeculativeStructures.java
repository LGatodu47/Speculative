package io.github.lgatodu47.speculative.common.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.world.feature.structure.SpeculoPyramidStructure;
import io.github.lgatodu47.speculative.common.world.feature.structure.SpeculoPyramidStructurePiece;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SpeculativeStructures {
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Speculative.MODID);

    public static final RegistryObject<Structure<NoFeatureConfig>> SPECULO_PYRAMID = STRUCTURES.register("speculo_pyramid", () -> new SpeculoPyramidStructure(NoFeatureConfig.CODEC));
    public static final IStructurePieceType SPECULO_PYRAMID_PIECE = IStructurePieceType.setPieceId(SpeculoPyramidStructurePiece::new, "speculative:speculo_pyramid_piece");

    public static void setupStructures() {
        Structure.STRUCTURES_REGISTRY.put(SPECULO_PYRAMID.getId().toString(), SPECULO_PYRAMID.get());
//        setupMapSpacingAndLand(SPECULO_PYRAMID.get(), new StructureSeparationSettings(32, 8, 354978923), false);
    }

    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings separationSettings, boolean transformSurroundingLand) {
        if (transformSurroundingLand) {
            Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();
        }

        DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure, separationSettings).build();

        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();

            if (structureMap instanceof ImmutableMap) {
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, separationSettings);
                settings.getValue().structureSettings().structureConfig = tempMap;
            } else {
                structureMap.put(structure, separationSettings);
            }
        });
    }

    private static Method GETCODEC_METHOD;

//    @SubscribeEvent
    public static void addDimensionalSpacing(WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();

            try {
                if (GETCODEC_METHOD == null)
                    GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
                @SuppressWarnings("unchecked")
                ResourceLocation rl = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
                if (rl != null && rl.getNamespace().equals("terraforged"))
                    return;
            } catch (Exception e) {
                Speculative.LOGGER.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }

            if (serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator && serverWorld.dimension().equals(World.OVERWORLD))
                return;

            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
//            tempMap.putIfAbsent(SpeculativeStructures.SPECULO_PYRAMID.get(), DimensionStructuresSettings.DEFAULTS.get(SpeculativeStructures.SPECULO_PYRAMID.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }

    public static final class Configured {
        private static final Map<String, Supplier<StructureFeature<?, ?>>> CONFIGURED_STRUCTURES_MAP = new HashMap<>();

        public static final Supplier<StructureFeature<?, ?>> CONFIGURED_SPECULO_PYRAMID = register("configured_speculo_pyramid", SpeculativeStructures.SPECULO_PYRAMID, NoFeatureConfig.INSTANCE);

        private static <FC extends IFeatureConfig, F extends Structure<FC>> Supplier<StructureFeature<?, ?>> register(String name, Supplier<F> structure, FC config) {
            Supplier<StructureFeature<?, ?>> sup = () -> structure.get().configured(config);
            CONFIGURED_STRUCTURES_MAP.put(name, sup);
            return sup;
        }

        public static void registerConfiguredStructures() {
            CONFIGURED_STRUCTURES_MAP.forEach((name, configured) -> {
                Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(Speculative.MODID, name), configured.get());
                FlatGenerationSettings.STRUCTURE_FEATURES.put(configured.get().feature, configured.get());
            });
        }
    }
}
