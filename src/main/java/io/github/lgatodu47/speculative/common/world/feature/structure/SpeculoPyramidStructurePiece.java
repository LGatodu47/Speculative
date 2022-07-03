package io.github.lgatodu47.speculative.common.world.feature.structure;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.init.SpeculativeStructures;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpeculoPyramidStructurePiece extends TemplateStructurePiece {
    private int goodChestsCount;
    private int badChestsCount;
    private final List<BlockPos> goodChests = new ArrayList<>();
    private final List<BlockPos> badChests = new ArrayList<>();
    private final Rotation rot;

    public SpeculoPyramidStructurePiece(TemplateManager manager, BlockPos pos, Rotation rot) {
        super(SpeculativeStructures.SPECULO_PYRAMID_PIECE, 0);
        this.templatePosition = pos;
        this.rot = rot;
        this.getLootTablesForChests();
        this.setupPiece(manager);
    }

    public SpeculoPyramidStructurePiece(TemplateManager manager, CompoundNBT nbt) {
        super(SpeculativeStructures.SPECULO_PYRAMID_PIECE, nbt);
        this.rot = Rotation.valueOf(nbt.getString("Rot"));
        this.getLootTablesForChests();
        this.setupPiece(manager);
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

    private void setupPiece(TemplateManager manager) {
        Template template = manager.getOrCreate(new ResourceLocation(Speculative.MODID, "speculo_pyramid"));
        PlacementSettings placementSettings = (new PlacementSettings()).setRotation(rot).setMirror(Mirror.NONE);

        this.setup(template, templatePosition, placementSettings);
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT tagCompound) {
        super.addAdditionalSaveData(tagCompound);
        tagCompound.putString("Rot", this.rot.name());
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
    protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
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

                TileEntity tile = worldIn.getBlockEntity(chestPos);

                if (tile instanceof ChestTileEntity) {
                    ChestTileEntity chest = (ChestTileEntity) tile;
                    chest.setLootTable(new ResourceLocation(Speculative.MODID, chestLootTable), rand.nextLong());
                }

                BlockPos dispenserPos = pos.relative(worldIn.getBlockState(chestPos).getValue(ChestBlock.FACING).getOpposite());
                TileEntity tile2 = worldIn.getBlockEntity(dispenserPos);

                if (tile2 instanceof DispenserTileEntity) {
                    DispenserTileEntity dispenser = (DispenserTileEntity) tile2;
                    dispenser.setLootTable(new ResourceLocation(Speculative.MODID, dispenserLootTable), rand.nextLong());
                }

                worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }
}
