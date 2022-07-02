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
    public static final IStructurePieceType SPECULO_PYRAMID_PIECE = IStructurePieceType.register(SpeculoPyramidStructurePiece::new, "speculative:speculo_pyramid_piece");

    public static void setupStructures() {
        Structure.NAME_STRUCTURE_BIMAP.put(SPECULO_PYRAMID.getId().toString(), SPECULO_PYRAMID.get());
//        setupMapSpacingAndLand(SPECULO_PYRAMID.get(), new StructureSeparationSettings(32, 8, 354978923), false);
    }

    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings separationSettings, boolean transformSurroundingLand) {
        if (transformSurroundingLand) {
            Structure.field_236384_t_ = ImmutableList.<Structure<?>>builder().addAll(Structure.field_236384_t_).add(structure).build();
        }

        DimensionStructuresSettings.field_236191_b_ = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.field_236191_b_).put(structure, separationSettings).build();

        WorldGenRegistries.NOISE_SETTINGS.getEntries().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().getStructures().func_236195_a_();

            if (structureMap instanceof ImmutableMap) {
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, separationSettings);
                settings.getValue().getStructures().field_236193_d_ = tempMap;
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
                    GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                @SuppressWarnings("unchecked")
                ResourceLocation rl = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkProvider().generator));
                if (rl != null && rl.getNamespace().equals("terraforged"))
                    return;
            } catch (Exception e) {
                Speculative.LOGGER.error("Was unable to check if " + serverWorld.getDimensionKey().getLocation() + " is using Terraforged's ChunkGenerator.");
            }

            if (serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator && serverWorld.getDimensionKey().equals(World.OVERWORLD))
                return;

            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
//            tempMap.putIfAbsent(SpeculativeStructures.SPECULO_PYRAMID.get(), DimensionStructuresSettings.field_236191_b_.get(SpeculativeStructures.SPECULO_PYRAMID.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
        }
    }

    public static final class Configured {
        private static final Map<String, Supplier<StructureFeature<?, ?>>> CONFIGURED_STRUCTURES_MAP = new HashMap<>();

        public static final Supplier<StructureFeature<?, ?>> CONFIGURED_SPECULO_PYRAMID = register("configured_speculo_pyramid", SpeculativeStructures.SPECULO_PYRAMID, NoFeatureConfig.INSTANCE);

        private static <FC extends IFeatureConfig, F extends Structure<FC>> Supplier<StructureFeature<?, ?>> register(String name, Supplier<F> structure, FC config) {
            Supplier<StructureFeature<?, ?>> sup = () -> structure.get().withConfiguration(config);
            CONFIGURED_STRUCTURES_MAP.put(name, sup);
            return sup;
        }

        public static void registerConfiguredStructures() {
            CONFIGURED_STRUCTURES_MAP.forEach((name, configured) -> {
                Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(Speculative.MODID, name), configured.get());
                FlatGenerationSettings.STRUCTURES.put(configured.get().field_236268_b_, configured.get());
            });
        }
    }
}
