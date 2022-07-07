package io.github.lgatodu47.speculative.common.world.feature.structure;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.init.SpeculativeStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpeculoPyramidStructurePiece extends TemplateStructurePiece {
    private int goodChestsCount;
    private int badChestsCount;
    private final List<BlockPos> goodChests = new ArrayList<>();
    private final List<BlockPos> badChests = new ArrayList<>();
    private final Rotation rot;

    public SpeculoPyramidStructurePiece(StructureManager manager, BlockPos pos, Rotation rot) {
        super(SpeculativeStructures.SPECULO_PYRAMID_PIECE.get(), 0, manager, new ResourceLocation(Speculative.MODID, "speculo_pyramid"), new ResourceLocation(Speculative.MODID, "speculo_pyramid").toString(), new StructurePlaceSettings().setRotation(rot).setMirror(Mirror.NONE), pos);
        this.templatePosition = pos;
        this.rot = rot;
        this.getLootTablesForChests();
    }

    public SpeculoPyramidStructurePiece(StructureManager manager, CompoundTag nbt) {
        super(SpeculativeStructures.SPECULO_PYRAMID_PIECE.get(), nbt, manager, id -> new StructurePlaceSettings().setRotation(Rotation.valueOf(nbt.getString("Rot"))).setMirror(Mirror.NONE));
        this.rot = Rotation.valueOf(nbt.getString("Rot"));
        this.getLootTablesForChests();
    }

    private void getLootTablesForChests() {
        Random rand = new Random();
        int chance = rand.nextInt(9);

        if (chance < 2) {
            this.badChestsCount = 3;
            this.goodChestsCount = 1;
        } else if (chance < 7) {
            this.badChestsCount = this.goodChestsCount = 2;
        } else {
            this.goodChestsCount = 3;
            this.badChestsCount = 1;
        }
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag nbt) {
        super.addAdditionalSaveData(ctx, nbt);
        nbt.putString("Rot", this.rot.name());
    }

    private void createLoot(BlockPos pos, Random rand) {
        if(rand.nextBoolean()) {
            if (this.goodChests.size() < this.goodChestsCount) {
                this.goodChests.add(pos);
            }
            else {
                this.badChests.add(pos);
            }
        } else {
            if (this.badChests.size() < this.badChestsCount) {
                this.badChests.add(pos);
            }
            else {
                this.goodChests.add(pos);
            }
        }
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor worldIn, Random rand, BoundingBox sbb) {
        if (function.startsWith("Loot")) {
            BlockPos chestPos = pos.below();
            if (sbb.isInside(chestPos)) {
                createLoot(pos, rand);

                String chestLootTable;
                String dispenserLootTable;
                if (this.goodChests.contains(pos)) {
                    chestLootTable = "chests/speculo_pyramid_good_loot";
                    dispenserLootTable = "chests/speculo_pyramid_good_splash";
                } else if (this.badChests.contains(pos)) {
                    chestLootTable = "chests/speculo_pyramid_bad_loot";
                    dispenserLootTable = "chests/speculo_pyramid_bad_splash";
                } else {
                    Speculative.LOGGER.error("Failed to create loot tables for speculative:speculo_pyramid at " + pos);
                    return;
                }

                BlockEntity tile = worldIn.getBlockEntity(chestPos);

                if (tile instanceof ChestBlockEntity chest) {
                    chest.setLootTable(new ResourceLocation(Speculative.MODID, chestLootTable), rand.nextLong());
                }

                BlockPos dispenserPos = pos.relative(worldIn.getBlockState(chestPos).getValue(ChestBlock.FACING).getOpposite());
                BlockEntity tile2 = worldIn.getBlockEntity(dispenserPos);

                if (tile2 instanceof DispenserBlockEntity dispenser) {
                    dispenser.setLootTable(new ResourceLocation(Speculative.MODID, dispenserLootTable), rand.nextLong());
                }

                worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }
}
