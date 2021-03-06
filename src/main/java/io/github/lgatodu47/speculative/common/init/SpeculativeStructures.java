package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.world.feature.structure.SpeculoHouseStructure;
import io.github.lgatodu47.speculative.common.world.feature.structure.SpeculoHouseStructurePiece;
import io.github.lgatodu47.speculative.common.world.feature.structure.SpeculoPyramidStructure;
import io.github.lgatodu47.speculative.common.world.feature.structure.SpeculoPyramidStructurePiece;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SpeculativeStructures {
    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Speculative.MODID);
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECES = DeferredRegister.create(Registry.STRUCTURE_PIECE_REGISTRY, Speculative.MODID);

    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> SPECULO_PYRAMID = STRUCTURES.register("speculo_pyramid", () -> new SpeculoPyramidStructure(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructurePieceType.StructureTemplateType> SPECULO_PYRAMID_PIECE = STRUCTURE_PIECES.register("speculo_pyramid_piece", () -> SpeculoPyramidStructurePiece::new);
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> SPECULO_HOUSE = STRUCTURES.register("speculo_house", () -> new SpeculoHouseStructure(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructurePieceType.StructureTemplateType> SPECULO_HOUSE_PIECE = STRUCTURE_PIECES.register("speculo_house_piece", () -> SpeculoHouseStructurePiece::new);

    public static void setupStructures() {
//        StructureFeature.STRUCTURES_REGISTRY.put(SPECULO_PYRAMID.getId().toString(), SPECULO_PYRAMID.get());
//        setupMapSpacingAndLand(SPECULO_PYRAMID.get(), new StructureSeparationSettings(32, 8, 354978923), false);
    }

    /*public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(F structure, StructureFeatureConfiguration separationSettings, boolean transformSurroundingLand) {
        if (transformSurroundingLand) {
            StructureFeature.NOISE_AFFECTING_FEATURES = ImmutableList.<StructureFeature<?>>builder().addAll(StructureFeature.NOISE_AFFECTING_FEATURES).add(structure).build();
        }

        StructureSettings.DEFAULTS = ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder().putAll(StructureSettings.DEFAULTS).put(structure, separationSettings).build();

        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();

            if (structureMap instanceof ImmutableMap) {
                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, separationSettings);
                settings.getValue().structureSettings().structureConfig = tempMap;
            } else {
                structureMap.put(structure, separationSettings);
            }
        });
    }*/

    /*private static Method GETCODEC_METHOD;

//    @SubscribeEvent
    public static void addDimensionalSpacing(WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerLevel) {
            ServerLevel serverWorld = (ServerLevel) event.getWorld();

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

            if (serverWorld.getChunkSource().getGenerator() instanceof FlatLevelSource && serverWorld.dimension().equals(Level.OVERWORLD))
                return;

            Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
//            tempMap.putIfAbsent(SpeculativeStructures.SPECULO_PYRAMID.get(), DimensionStructuresSettings.DEFAULTS.get(SpeculativeStructures.SPECULO_PYRAMID.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }*/

    public static final class Configured {
        public static final DeferredRegister<ConfiguredStructureFeature<?, ?>> CONFIGURED_STRUCTURES = DeferredRegister.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, Speculative.MODID);

        public static final RegistryObject<ConfiguredStructureFeature<?, ?>> SPECULO_PYRAMID = register("speculo_pyramid", SpeculativeStructures.SPECULO_PYRAMID, NoneFeatureConfiguration.INSTANCE, SpeculativeBiomes.Tags.HAS_SPECULO_PYRAMID);
        public static final RegistryObject<ConfiguredStructureFeature<?, ?>> SPECULO_HOUSE = registerAdapting("speculo_house", SpeculativeStructures.SPECULO_HOUSE, NoneFeatureConfiguration.INSTANCE, SpeculativeBiomes.Tags.HAS_SPECULO_HOUSE);

        private static <FC extends FeatureConfiguration, F extends StructureFeature<FC>> RegistryObject<ConfiguredStructureFeature<?, ?>> register(String name, Supplier<F> structure, FC config, TagKey<Biome> biomePredicate) {
            return CONFIGURED_STRUCTURES.register(name, () -> structure.get().configured(config, biomePredicate));
        }

        private static <FC extends FeatureConfiguration, F extends StructureFeature<FC>> RegistryObject<ConfiguredStructureFeature<?, ?>> registerAdapting(String name, Supplier<F> structure, FC config, TagKey<Biome> biomePredicate) {
            return CONFIGURED_STRUCTURES.register(name, () -> structure.get().configured(config, biomePredicate, true));
        }

        public static final class Tags {
            public static final TagKey<ConfiguredStructureFeature<?, ?>> ON_DUNGEON_MAP = CONFIGURED_STRUCTURES.createTagKey("on_dungeon_map");
        }
    }
}
