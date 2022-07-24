package io.github.lgatodu47.speculative.common.world.feature.structure;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.entity.AncientLordEntity;
import io.github.lgatodu47.speculative.common.init.SpeculativeEntityTypes;
import io.github.lgatodu47.speculative.common.init.SpeculativeItems;
import io.github.lgatodu47.speculative.common.init.SpeculativeStructures;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;

public class SpeculoHouseStructurePiece extends TemplateStructurePiece {
    private static final ResourceLocation ID = new ResourceLocation(Speculative.MODID, "speculo_house");
    private final Rotation rotation;

    public SpeculoHouseStructurePiece(StructureManager manager, BlockPos pos, Rotation rot) {
        super(SpeculativeStructures.SPECULO_HOUSE_PIECE.get(), 0, manager, ID, ID.toString(), createSettings(rot), pos);
        this.rotation = rot;
        this.boundingBox = this.template.getBoundingBox(this.placeSettings, this.templatePosition.above());
    }

    public SpeculoHouseStructurePiece(StructureManager manager, CompoundTag nbt) {
        super(SpeculativeStructures.SPECULO_HOUSE_PIECE.get(), nbt, manager, id -> createSettings(Rotation.valueOf(nbt.getString("Rotation"))));
        this.rotation = Rotation.valueOf(nbt.getString("Rotation"));
        this.boundingBox = this.template.getBoundingBox(this.placeSettings, this.templatePosition.above());
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tag) {
        super.addAdditionalSaveData(ctx, tag);
        tag.putString("Rotation", this.rotation.name());
    }

    @Override
    protected void handleDataMarker(String marker, BlockPos pos, ServerLevelAccessor level, Random rand, BoundingBox box) {
        if(box.isInside(pos)) {
            switch (marker) {
                case "framed_random_item" -> {
                    level.removeBlock(pos, false);
                    ItemFrame itemFrame = new ItemFrame(level.getLevel(), pos, Direction.NORTH);
                    ItemStack stack = ForgeRegistries.ITEMS.tags().getTag(SpeculativeItems.Tags.FOUND_IN_SPECULO_HOUSE).getRandomElement(rand).map(ItemStack::new).orElse(ItemStack.EMPTY);
                    itemFrame.setItem(stack, false);
                    level.addFreshEntityWithPassengers(itemFrame);
                }
                case "framed_music_disc" -> {
                    level.removeBlock(pos, false);
                    ItemFrame itemFrame = new ItemFrame(level.getLevel(), pos, Direction.NORTH);
                    ItemStack stack = ForgeRegistries.ITEMS.tags().getTag(ItemTags.MUSIC_DISCS).getRandomElement(rand).map(ItemStack::new).orElse(ItemStack.EMPTY);
                    itemFrame.setItem(stack, false);
                    level.addFreshEntityWithPassengers(itemFrame);
                }
                case "random_flower_pot" -> {
                    level.removeBlock(pos, false);
                    Block flowerPotBlock = Blocks.FLOWER_POT;
                    if(flowerPotBlock instanceof FlowerPotBlock flowerPot) {
                        BlockState placing = Util.getRandomSafe(new ArrayList<>(flowerPot.getFullPotsView().values()), rand)
                                .map(Supplier::get)
                                .map(Block::defaultBlockState)
                                .orElse(flowerPotBlock.defaultBlockState());
                        level.setBlock(pos, placing, 1);
                    }
                }
                case "npc" -> {
                    level.removeBlock(pos, false);
                    AncientLordEntity npc = SpeculativeEntityTypes.ANCIENT_LORD.get().create(level.getLevel());
                    if(npc != null) {
                        npc.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, Mth.wrapDegrees(rand.nextFloat() * 360F), 0);
                        npc.yHeadRot = npc.getYRot();
                        npc.yBodyRot = npc.getYRot();
                        npc.finalizeSpawn(level, level.getCurrentDifficultyAt(npc.blockPosition()), MobSpawnType.STRUCTURE, null, null);
                        level.addFreshEntityWithPassengers(npc);
                    }
                }
            }
        }
    }

    private static StructurePlaceSettings createSettings(Rotation rot) {
        return new StructurePlaceSettings().setRotation(rot).setMirror(Mirror.NONE).setIgnoreEntities(false).setKeepLiquids(false);
    }
}
