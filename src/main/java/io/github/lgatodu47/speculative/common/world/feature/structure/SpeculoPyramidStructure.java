package io.github.lgatodu47.speculative.common.world.feature.structure;

import com.mojang.serialization.Codec;
import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import net.minecraft.world.gen.feature.structure.Structure.IStartFactory;

public class SpeculoPyramidStructure extends Structure<NoFeatureConfig> {
    public SpeculoPyramidStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    @Override
    public String getFeatureName() {
        return Speculative.MODID + ":speculo_pyramid";
    }

    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox mutable, int reference, long seed) {
            super(structure, chunkX, chunkZ, mutable, reference, seed);
        }

        @Override
        public void generatePieces(DynamicRegistries reg, ChunkGenerator generator, TemplateManager manager, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config) {
            Rotation rotation = Rotation.getRandom(random);

            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;
            int y = generator.getBaseHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);
            BlockPos pos = new BlockPos(x, y, z);

            SpeculoPyramidStructurePiece piece = new SpeculoPyramidStructurePiece(manager, new BlockPos(0, -15, 0).rotate(rotation).offset(pos), rotation);

            this.pieces.add(piece);

            this.calculateBoundingBox();
        }
    }
}