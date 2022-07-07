package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.block.*;
import io.github.lgatodu47.speculative.common.world.feature.SpeculoTreeGrower;
import io.github.lgatodu47.speculative.common.world.feature.TourmalineTreeGrower;
import io.github.lgatodu47.speculative.util.SpeculativeWoodTypes;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public class SpeculativeBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Speculative.MODID);

    // Ores and Ore Blocks
    public static final RegistryObject<Block> SPECULO_ORE = BLOCKS.register("speculo_ore", () -> new SpeculativeOre(3.5F, 4.0F));
    public static final RegistryObject<Block> SPECULO_BLOCK = BLOCKS.register("speculo_block", oreBlock(5.0F, 8.5F));
    public static final RegistryObject<Block> SPECULO_ORE_W = BLOCKS.register("speculo_world_speculo_ore", () -> new SpeculativeOre(3.5F, 4.0F).xp(2, 5));
    public static final RegistryObject<Block> RADIOACTIVE_DIAMOND_ORE = BLOCKS.register("radioactive_diamond_ore", () -> new SpeculativeOre(4.0F, 5.0F).xp(3, 7));
    public static final RegistryObject<Block> RADIOACTIVE_DIAMOND_BLOCK = BLOCKS.register("radioactive_diamond_block", oreBlock(5.0F, 8.0F));
    public static final RegistryObject<Block> URANIUM_ORE = BLOCKS.register("uranium_ore", () -> new SpeculativeOre(3.0F, 3.0F));
    public static final RegistryObject<Block> GRAPHITE_ORE = BLOCKS.register("graphite_ore", () -> new SpeculativeOre(2.5F, 3.0F));
    public static final RegistryObject<Block> GRAPHITE_BLOCK = BLOCKS.register("graphite_block", oreBlock(5.25F, 10F));
    public static final RegistryObject<Block> URANIUM_238_BLOCK = BLOCKS.register("uranium_238_block", () -> new UraniumBlock(UraniumBlock.UraniumType.U238));
    public static final RegistryObject<Block> URANIUM_235_BLOCK = BLOCKS.register("uranium_235_block", () -> new UraniumBlock(UraniumBlock.UraniumType.U235));
    public static final RegistryObject<Block> URANIUM_234_BLOCK = BLOCKS.register("uranium_234_block", () -> new UraniumBlock(UraniumBlock.UraniumType.U234));

    // Machines
    public static final RegistryObject<Block> SPECULOOS_SUMMONER = BLOCKS.register("speculoos_summoner", SpeculoosSummonerBlock::new);
    public static final RegistryObject<Block> CENTRIFUGE = BLOCKS.register("centrifuge", CentrifugeBlock::new);
    public static final RegistryObject<Block> SPECULO_BOSS_SUMMONER = BLOCKS.register("speculo_boss_summoner", BossSummonerBlock::new);
    public static final RegistryObject<Block> NUCLEAR_WORKBENCH = BLOCKS.register("nuclear_workbench", NuclearWorkbenchBlock::new);

    //Speculo world stuff
    public static final RegistryObject<Block> SPECULO_GRASS = BLOCKS.register("speculo_grass", () -> new SpeculativeGrassBlock(SpeculativeBlocks.SPECULO_DIRT));
    public static final RegistryObject<Block> SPECULO_DIRT = BLOCKS.register("speculo_dirt", dirt());
    public static final RegistryObject<Block> SPECULO_STONE = BLOCKS.register("speculo_stone", stone());

    public static final RegistryObject<Block> SPECULO_COBBLESTONE = BLOCKS.register("speculo_cobblestone", cobblestone());
    public static final RegistryObject<Block> SPECULO_COBBLESTONE_STAIRS = BLOCKS.register("speculo_cobblestone_stairs", () -> new StairBlock(() -> SpeculativeBlocks.SPECULO_COBBLESTONE.get().defaultBlockState(), Block.Properties.copy(SpeculativeBlocks.SPECULO_COBBLESTONE.get())));
    public static final RegistryObject<Block> SPECULO_COBBLESTONE_SLAB = BLOCKS.register("speculo_cobblestone_slab", () -> new SlabBlock(Properties.copy(SpeculativeBlocks.SPECULO_COBBLESTONE.get())));
    public static final RegistryObject<Block> SPECULO_COBBLESTONE_WALL = BLOCKS.register("speculo_cobblestone_wall", () -> new WallBlock(Properties.copy(SpeculativeBlocks.SPECULO_COBBLESTONE.get())));

    public static final RegistryObject<Block> SPECULO_FLOWER = BLOCKS.register("speculo_flower", () -> new FlowerBlock(MobEffects.FIRE_RESISTANCE, 20, Properties.of(Material.PLANT).noCollission().strength(0.0F).sound(SoundType.GRASS)));

    public static final RegistryObject<Block> STRIPPED_SPECULO_LOG = BLOCKS.register("stripped_speculo_log", () -> new RotatedPillarBlock(Properties.copy(Blocks.STRIPPED_ACACIA_LOG)));
    public static final RegistryObject<Block> SPECULO_LOG = BLOCKS.register("speculo_log", () -> new StrippableLog(SpeculativeBlocks.STRIPPED_SPECULO_LOG, Properties.copy(Blocks.ACACIA_LOG)));
    public static final RegistryObject<Block> STRIPPED_SPECULO_BARK = BLOCKS.register("stripped_speculo_bark", () -> new RotatedPillarBlock(Properties.copy(Blocks.STRIPPED_ACACIA_WOOD)));
    public static final RegistryObject<Block> SPECULO_BARK = BLOCKS.register("speculo_bark", () -> new StrippableLog(SpeculativeBlocks.STRIPPED_SPECULO_BARK, Properties.copy(Blocks.ACACIA_WOOD)));

    public static final RegistryObject<Block> SPECULO_PLANKS = BLOCKS.register("speculo_planks", () -> new Block(Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistryObject<Block> SPECULO_WOOD_STAIRS = BLOCKS.register("speculo_wood_stairs", () -> new StairBlock(() -> SpeculativeBlocks.SPECULO_PLANKS.get().defaultBlockState(), Block.Properties.copy(Blocks.ACACIA_STAIRS)));
    public static final RegistryObject<Block> SPECULO_WOOD_SLAB = BLOCKS.register("speculo_wood_slab", () -> new SlabBlock(Properties.copy(Blocks.ACACIA_SLAB)));
    public static final RegistryObject<Block> SPECULO_WOOD_FENCE = BLOCKS.register("speculo_wood_fence", () -> new FenceBlock(Properties.copy(Blocks.ACACIA_FENCE)));
    public static final RegistryObject<Block> SPECULO_WOOD_FENCE_GATE = BLOCKS.register("speculo_wood_fence_gate", () -> new FenceGateBlock(Properties.copy(Blocks.ACACIA_FENCE_GATE)));
    public static final RegistryObject<Block> SPECULO_WOOD_STANDING_SIGN = BLOCKS.register("speculo_wood_standing_sign", () -> new StandingSignBlock(Properties.copy(Blocks.ACACIA_SIGN), SpeculativeWoodTypes.SPECULO_WOOD));
    public static final RegistryObject<Block> SPECULO_WOOD_WALL_SIGN = BLOCKS.register("speculo_wood_wall_sign", () -> new WallSignBlock(Properties.copy(Blocks.ACACIA_WALL_SIGN), SpeculativeWoodTypes.SPECULO_WOOD));
    public static final RegistryObject<Block> SPECULO_WOOD_TRAPDOOR = BLOCKS.register("speculo_wood_trapdoor", () -> new TrapDoorBlock(Properties.copy(Blocks.ACACIA_TRAPDOOR)));
    public static final RegistryObject<Block> SPECULO_WOOD_PRESSURE_PLATE = BLOCKS.register("speculo_wood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.copy(Blocks.ACACIA_PRESSURE_PLATE)));

    public static final RegistryObject<Block> SPECULO_LEAVES = BLOCKS.register("speculo_leaves", () -> new LeavesBlock(Properties.copy(Blocks.ACACIA_LEAVES)));
    public static final RegistryObject<Block> SPECULO_TREE_SAPLING = BLOCKS.register("speculo_tree_sapling", () -> new SaplingBlock(new SpeculoTreeGrower(), Properties.copy(Blocks.ACACIA_SAPLING)));

    public static final RegistryObject<Block> SPECULO_SAND = BLOCKS.register("speculo_sand", () -> new SandBlock(16222542, Properties.of(Material.SAND, MaterialColor.COLOR_ORANGE).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> SPECULO_SANDSTONE = BLOCKS.register("speculo_sandstone", stone());
    public static final RegistryObject<Block> CUT_SPECULO_SANDSTONE = BLOCKS.register("cut_speculo_sandstone", stone());
    public static final RegistryObject<Block> CHISELED_SPECULO_SANDSTONE = BLOCKS.register("chiseled_speculo_sandstone", stone());
    public static final RegistryObject<Block> SPECULO_SANDSTONE_STAIRS = BLOCKS.register("speculo_sandstone_stairs", () -> new StairBlock(() -> SpeculativeBlocks.SPECULO_SANDSTONE.get().defaultBlockState(), Block.Properties.copy(SpeculativeBlocks.SPECULO_SANDSTONE.get())));
    public static final RegistryObject<Block> SPECULO_SANDSTONE_SLAB = BLOCKS.register("speculo_sandstone_slab", () -> new SlabBlock(Properties.copy(SpeculativeBlocks.SPECULO_SANDSTONE.get())));
    public static final RegistryObject<Block> SPECULO_SANDSTONE_WALL = BLOCKS.register("speculo_sandstone_wall", () -> new WallBlock(Properties.copy(SpeculativeBlocks.SPECULO_SANDSTONE.get())));

    public static final RegistryObject<Block> MANGO_BUSH = BLOCKS.register("mango_bush", MangoBush::new);

    public static final RegistryObject<Block> GREENSTONE_TORCH = BLOCKS.register("greenstone_torch", () -> new SpeculativeTorchBlock(Properties.copy(Blocks.TORCH).lightLevel(state -> 9)));
    public static final RegistryObject<Block> GREENSTONE_WALL_TORCH = BLOCKS.register("greenstone_wall_torch", () -> new SpeculativeTorchBlock.SpeculativeWallTorchBlock(Properties.copy(Blocks.WALL_TORCH).lightLevel(state -> 9)));

    public static final RegistryObject<Block> STRIPPED_TOURMALINE_LOG = BLOCKS.register("stripped_tourmaline_log", logBlock(MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN));
    public static final RegistryObject<Block> TOURMALINE_LOG = BLOCKS.register("tourmaline_log", () -> new StrippableLog(SpeculativeBlocks.STRIPPED_TOURMALINE_LOG, Properties.copy(SpeculativeBlocks.STRIPPED_TOURMALINE_LOG.get())));
    public static final RegistryObject<Block> STRIPPED_TOURMALINE_BARK = BLOCKS.register("stripped_tourmaline_bark", () -> new RotatedPillarBlock(Properties.copy(SpeculativeBlocks.STRIPPED_SPECULO_LOG.get())));
    public static final RegistryObject<Block> TOURMALINE_BARK = BLOCKS.register("tourmaline_bark", () -> new StrippableLog(SpeculativeBlocks.STRIPPED_TOURMALINE_BARK, Properties.copy(SpeculativeBlocks.STRIPPED_SPECULO_LOG.get())));

    public static final RegistryObject<Block> TOURMALINE_PLANKS = BLOCKS.register("tourmaline_planks", () -> new Block(Properties.of(Material.WOOD, MaterialColor.COLOR_GREEN).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> TOURMALINE_WOOD_STAIRS = BLOCKS.register("tourmaline_wood_stairs", () -> new StairBlock(() -> SpeculativeBlocks.TOURMALINE_PLANKS.get().defaultBlockState(), Block.Properties.copy(SpeculativeBlocks.TOURMALINE_PLANKS.get())));
    public static final RegistryObject<Block> TOURMALINE_WOOD_SLAB = BLOCKS.register("tourmaline_wood_slab", () -> new SlabBlock(Properties.copy(SpeculativeBlocks.TOURMALINE_PLANKS.get())));
    public static final RegistryObject<Block> TOURMALINE_WOOD_FENCE = BLOCKS.register("tourmaline_wood_fence", () -> new FenceBlock(Properties.copy(SpeculativeBlocks.TOURMALINE_PLANKS.get())));
    public static final RegistryObject<Block> TOURMALINE_WOOD_FENCE_GATE = BLOCKS.register("tourmaline_wood_fence_gate", () -> new FenceGateBlock(Properties.copy(SpeculativeBlocks.TOURMALINE_PLANKS.get())));
    public static final RegistryObject<Block> TOURMALINE_WOOD_STANDING_SIGN = BLOCKS.register("tourmaline_wood_standing_sign", () -> new StandingSignBlock(Properties.copy(SpeculativeBlocks.TOURMALINE_PLANKS.get()), SpeculativeWoodTypes.TOURMALINE_WOOD));
    public static final RegistryObject<Block> TOURMALINE_WOOD_WALL_SIGN = BLOCKS.register("tourmaline_wood_wall_sign", () -> new WallSignBlock(Properties.copy(SpeculativeBlocks.TOURMALINE_PLANKS.get()), SpeculativeWoodTypes.TOURMALINE_WOOD));
    public static final RegistryObject<Block> TOURMALINE_WOOD_TRAPDOOR = BLOCKS.register("tourmaline_wood_trapdoor", () -> new TrapDoorBlock(Properties.copy(SpeculativeBlocks.TOURMALINE_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> TOURMALINE_WOOD_PRESSURE_PLATE = BLOCKS.register("tourmaline_wood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.of(Material.WOOD, MaterialColor.COLOR_GREEN).strength(0.5F).noCollission().sound(SoundType.WOOD)));

    public static final RegistryObject<Block> TOURMALINE_LEAVES = BLOCKS.register("tourmaline_leaves", () -> new LeavesBlock(Properties.copy(Blocks.ACACIA_LEAVES)));
    public static final RegistryObject<Block> TOURMALINE_TREE_SAPLING = BLOCKS.register("tourmaline_tree_sapling", () -> new SaplingBlock(new TourmalineTreeGrower(), Properties.copy(Blocks.ACACIA_SAPLING)));

    // Miscellaneous
    public static final RegistryObject<Block> SPECULO_TNT = BLOCKS.register("speculo_tnt", SpeculoTNTBlock::new);

    private static Supplier<Block> stone() {
        return () -> new Block(Properties.of(Material.STONE).strength(1.5F, 6.0F));
    }

    private static Supplier<Block> oreBlock(float hardness, float resistance) {
        return () -> new Block(Properties.of(Material.METAL).sound(SoundType.METAL).strength(hardness, resistance).requiresCorrectToolForDrops());
    }

    private static Supplier<Block> logBlock(MaterialColor topColor, MaterialColor barkColor) {
        return () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).strength(2.0F).sound(SoundType.WOOD));
    }

    private static Supplier<Block> dirt() {
        return () -> new Block(Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL));
    }

    private static Supplier<Block> cobblestone() {
        return () -> new Block(Properties.of(Material.STONE).strength(2.0F, 6.0F));
    }

    public static final class Tags {
        public static final TagKey<Block> CARVABLE_BLOCKS = BlockTags.create(new ResourceLocation(Speculative.MODID, "carvable_blocks"));
        public static final TagKey<Block> URANIUM_PROOF = BlockTags.create(new ResourceLocation(Speculative.MODID, "uranium_proof"));
    }
}
