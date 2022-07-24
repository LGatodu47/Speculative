package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.block.*;
import io.github.lgatodu47.speculative.common.world.feature.SpeculoTreeGrower;
import io.github.lgatodu47.speculative.common.world.feature.TourmalineTreeGrower;
import io.github.lgatodu47.speculative.util.SpeculativeWoodTypes;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.lgatodu47.speculative.data.tags.IHarvestableBlock.TierType;
import static io.github.lgatodu47.speculative.data.tags.IHarvestableBlock.ToolType;

@SuppressWarnings({"unused"})
public class SpeculativeBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Speculative.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Speculative.MODID);

    // Ores
    public static final RegistryObject<Block> SPECULO_ORE = register("speculo_ore", () -> new SpeculativeOre(3.5F, 4.0F).drops(SpeculativeItems.SPECULO_GEM).harvests(ToolType.PICKAXE, TierType.IRON));
    public static final RegistryObject<Block> SPECULO_WORLD_SPECULO_ORE = register("speculo_world_speculo_ore", () -> new SpeculativeOre(3.5F, 4.0F).xp(2, 5).drops(SpeculativeItems.SPECULO_GEM).harvests(ToolType.PICKAXE, TierType.IRON));
    public static final RegistryObject<Block> RADIOACTIVE_DIAMOND_ORE = register("radioactive_diamond_ore", () -> new SpeculativeOre(4.0F, 5.0F).xp(3, 7).drops(SpeculativeItems.RADIOACTIVE_DIAMOND).harvests(ToolType.PICKAXE, TierType.IRON));
    public static final RegistryObject<Block> URANIUM_ORE = register("uranium_ore", () -> new SpeculativeOre(3.0F, 3.0F).harvests(ToolType.PICKAXE, TierType.IRON).disableDataGenLoot());
    public static final RegistryObject<Block> GRAPHITE_ORE = register("graphite_ore", () -> new SpeculativeOre(2.5F, 3.0F).harvests(ToolType.PICKAXE, TierType.STONE));

    // Ore Blocks
    public static final RegistryObject<Block> SPECULO_BLOCK = register("speculo_block", oreBlock(5.0F, 8.5F));
    public static final RegistryObject<Block> RADIOACTIVE_DIAMOND_BLOCK = register("radioactive_diamond_block", oreBlock(5.0F, 8.0F));
    public static final RegistryObject<Block> GRAPHITE_BLOCK = register("graphite_block", oreBlock(5.25F, 10F));
    public static final RegistryObject<Block> URANIUM_238_BLOCK = register("uranium_238_block", () -> new UraniumBlock(UraniumBlock.UraniumType.U238));
    public static final RegistryObject<Block> URANIUM_235_BLOCK = register("uranium_235_block", () -> new UraniumBlock(UraniumBlock.UraniumType.U235));
    public static final RegistryObject<Block> URANIUM_234_BLOCK = register("uranium_234_block", () -> new UraniumBlock(UraniumBlock.UraniumType.U234));

    // Machines
    public static final RegistryObject<Block> SPECULOOS_SUMMONER = register("speculoos_summoner", SpeculoosSummonerBlock::new);
    public static final RegistryObject<Block> CENTRIFUGE = register("centrifuge", CentrifugeBlock::new);
    public static final RegistryObject<Block> SPECULO_BOSS_SUMMONER = register("speculo_boss_summoner", BossSummonerBlock::new);
    public static final RegistryObject<Block> NUCLEAR_WORKBENCH = register("nuclear_workbench", NuclearWorkbenchBlock::new);
    public static final RegistryObject<Block> NUCLEAR_MANIPULATOR = register("nuclear_manipulator", NuclearManipulatorBlock::new);

    // Plants
    public static final RegistryObject<Block> MANGO_BUSH = register("mango_bush", MangoBush::new);
    public static final RegistryObject<Block> SPECULO_FLOWER = register("speculo_flower", () -> new FlowerBlock(MobEffects.FIRE_RESISTANCE, 20, Properties.of(Material.PLANT).noCollission().strength(0.0F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SPECULO_TREE_SAPLING = register("speculo_tree_sapling", () -> new SaplingBlock(new SpeculoTreeGrower(), Properties.copy(Blocks.ACACIA_SAPLING)));
    public static final RegistryObject<Block> TOURMALINE_TREE_SAPLING = register("tourmaline_tree_sapling", () -> new SaplingBlock(new TourmalineTreeGrower(), Properties.copy(Blocks.ACACIA_SAPLING)));
    public static final RegistryObject<Block> POTTED_SPECULO_FLOWER = registerPotted(SpeculativeBlocks.SPECULO_FLOWER);
    public static final RegistryObject<Block> POTTED_SPECULO_TREE_SAPLING = registerPotted(SpeculativeBlocks.SPECULO_TREE_SAPLING);
    public static final RegistryObject<Block> POTTED_TOURMALINE_TREE_SAPLING = registerPotted(SpeculativeBlocks.TOURMALINE_TREE_SAPLING);

    // Speculo Wood
    public static final RegistryObject<Block> SPECULO_LEAVES = register("speculo_leaves", () -> new LeavesBlock(Properties.copy(Blocks.ACACIA_LEAVES)));

    public static final RegistryObject<Block> STRIPPED_SPECULO_LOG = register("stripped_speculo_log", () -> new RotatedPillarBlock(Properties.copy(Blocks.STRIPPED_ACACIA_LOG)));
    public static final RegistryObject<Block> SPECULO_LOG = register("speculo_log", () -> new SpeculativeLogBlock(SpeculativeBlocks.STRIPPED_SPECULO_LOG, Properties.copy(Blocks.ACACIA_LOG)));
    public static final RegistryObject<Block> STRIPPED_SPECULO_BARK = register("stripped_speculo_bark", () -> new RotatedPillarBlock(Properties.copy(Blocks.STRIPPED_ACACIA_WOOD)));
    public static final RegistryObject<Block> SPECULO_BARK = register("speculo_bark", () -> new SpeculativeLogBlock(SpeculativeBlocks.STRIPPED_SPECULO_BARK, Properties.copy(Blocks.ACACIA_WOOD)));

    public static final RegistryObject<Block> SPECULO_PLANKS = register("speculo_planks", () -> new Block(Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistryObject<Block> SPECULO_WOOD_STAIRS = register("speculo_wood_stairs", () -> new StairBlock(() -> SpeculativeBlocks.SPECULO_PLANKS.get().defaultBlockState(), Block.Properties.copy(Blocks.ACACIA_STAIRS)));
    public static final RegistryObject<Block> SPECULO_WOOD_SLAB = register("speculo_wood_slab", () -> new SlabBlock(Properties.copy(Blocks.ACACIA_SLAB)));
    public static final RegistryObject<Block> SPECULO_WOOD_FENCE = register("speculo_wood_fence", () -> new FenceBlock(Properties.copy(Blocks.ACACIA_FENCE)));
    public static final RegistryObject<Block> SPECULO_WOOD_FENCE_GATE = register("speculo_wood_fence_gate", () -> new FenceGateBlock(Properties.copy(Blocks.ACACIA_FENCE_GATE)));
    public static final RegistryObject<Block> SPECULO_WOOD_STANDING_SIGN = BLOCKS.register("speculo_wood_standing_sign", () -> new StandingSignBlock(Properties.copy(Blocks.ACACIA_SIGN), SpeculativeWoodTypes.SPECULO_WOOD));
    public static final RegistryObject<Block> SPECULO_WOOD_WALL_SIGN = BLOCKS.register("speculo_wood_wall_sign", () -> new WallSignBlock(Properties.copy(Blocks.ACACIA_WALL_SIGN).lootFrom(SpeculativeBlocks.SPECULO_WOOD_STANDING_SIGN), SpeculativeWoodTypes.SPECULO_WOOD));
    public static final RegistryObject<Block> SPECULO_WOOD_TRAPDOOR = register("speculo_wood_trapdoor", () -> new TrapDoorBlock(Properties.copy(Blocks.ACACIA_TRAPDOOR)));
    public static final RegistryObject<Block> SPECULO_WOOD_PRESSURE_PLATE = register("speculo_wood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.copy(Blocks.ACACIA_PRESSURE_PLATE)));
    public static final RegistryObject<Block> SPECULO_WOOD_DOOR = BLOCKS.register("speculo_wood_door", () -> new DoorBlock(Properties.copy(Blocks.ACACIA_DOOR)));
    public static final RegistryObject<Block> SPECULO_WOOD_BUTTON = register("speculo_wood_button", () -> new WoodButtonBlock(Properties.copy(Blocks.ACACIA_BUTTON)));

    // Tourmaline Wood
    public static final RegistryObject<Block> TOURMALINE_LEAVES = register("tourmaline_leaves", () -> new LeavesBlock(Properties.copy(Blocks.ACACIA_LEAVES)));

    public static final RegistryObject<Block> STRIPPED_TOURMALINE_LOG = register("stripped_tourmaline_log", logBlock(MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN));
    public static final RegistryObject<Block> TOURMALINE_LOG = register("tourmaline_log", () -> new SpeculativeLogBlock(SpeculativeBlocks.STRIPPED_TOURMALINE_LOG, Properties.copy(SpeculativeBlocks.STRIPPED_TOURMALINE_LOG.get())));
    public static final RegistryObject<Block> STRIPPED_TOURMALINE_BARK = register("stripped_tourmaline_bark", () -> new RotatedPillarBlock(Properties.copy(SpeculativeBlocks.STRIPPED_SPECULO_LOG.get())));
    public static final RegistryObject<Block> TOURMALINE_BARK = register("tourmaline_bark", () -> new SpeculativeLogBlock(SpeculativeBlocks.STRIPPED_TOURMALINE_BARK, Properties.copy(SpeculativeBlocks.STRIPPED_SPECULO_LOG.get())));

    public static final RegistryObject<Block> TOURMALINE_PLANKS = register("tourmaline_planks", () -> new Block(Properties.of(Material.WOOD, MaterialColor.COLOR_GREEN).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> TOURMALINE_WOOD_STAIRS = register("tourmaline_wood_stairs", () -> new StairBlock(() -> SpeculativeBlocks.TOURMALINE_PLANKS.get().defaultBlockState(), Block.Properties.copy(SpeculativeBlocks.TOURMALINE_PLANKS.get())));
    public static final RegistryObject<Block> TOURMALINE_WOOD_SLAB = register("tourmaline_wood_slab", () -> new SlabBlock(Properties.copy(SpeculativeBlocks.TOURMALINE_PLANKS.get())));
    public static final RegistryObject<Block> TOURMALINE_WOOD_FENCE = register("tourmaline_wood_fence", () -> new FenceBlock(Properties.copy(SpeculativeBlocks.TOURMALINE_PLANKS.get())));
    public static final RegistryObject<Block> TOURMALINE_WOOD_FENCE_GATE = register("tourmaline_wood_fence_gate", () -> new FenceGateBlock(Properties.copy(SpeculativeBlocks.TOURMALINE_PLANKS.get())));
    public static final RegistryObject<Block> TOURMALINE_WOOD_STANDING_SIGN = BLOCKS.register("tourmaline_wood_standing_sign", () -> new StandingSignBlock(Properties.copy(Blocks.ACACIA_SIGN).color(MaterialColor.COLOR_GREEN), SpeculativeWoodTypes.TOURMALINE_WOOD));
    public static final RegistryObject<Block> TOURMALINE_WOOD_WALL_SIGN = BLOCKS.register("tourmaline_wood_wall_sign", () -> new WallSignBlock(Properties.copy(Blocks.ACACIA_WALL_SIGN).color(MaterialColor.COLOR_GREEN).lootFrom(SpeculativeBlocks.TOURMALINE_WOOD_STANDING_SIGN), SpeculativeWoodTypes.TOURMALINE_WOOD));
    public static final RegistryObject<Block> TOURMALINE_WOOD_TRAPDOOR = register("tourmaline_wood_trapdoor", () -> new TrapDoorBlock(Properties.copy(SpeculativeBlocks.TOURMALINE_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> TOURMALINE_WOOD_PRESSURE_PLATE = register("tourmaline_wood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.of(Material.WOOD, MaterialColor.COLOR_GREEN).strength(0.5F).noCollission().sound(SoundType.WOOD)));
    public static final RegistryObject<Block> TOURMALINE_WOOD_DOOR = BLOCKS.register("tourmaline_wood_door", () -> new DoorBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_GREEN).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<Block> TOURMALINE_WOOD_BUTTON = register("tourmaline_wood_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).color(MaterialColor.COLOR_GREEN)));

    //Speculo world stuff
    public static final RegistryObject<Block> SPECULO_GRASS = register("speculo_grass", () -> new SpeculativeGrassBlock(SpeculativeBlocks.SPECULO_DIRT));
    public static final RegistryObject<Block> SPECULO_DIRT = register("speculo_dirt", SpeculativeDirtBlock::new);
    public static final RegistryObject<Block> SPECULO_STONE = register("speculo_stone", stone());

    public static final RegistryObject<Block> SPECULO_COBBLESTONE = register("speculo_cobblestone", cobblestone());
    public static final RegistryObject<Block> SPECULO_COBBLESTONE_STAIRS = register("speculo_cobblestone_stairs", () -> new StairBlock(() -> SpeculativeBlocks.SPECULO_COBBLESTONE.get().defaultBlockState(), Block.Properties.copy(SpeculativeBlocks.SPECULO_COBBLESTONE.get())));
    public static final RegistryObject<Block> SPECULO_COBBLESTONE_SLAB = register("speculo_cobblestone_slab", () -> new SlabBlock(Properties.copy(SpeculativeBlocks.SPECULO_COBBLESTONE.get())));
    public static final RegistryObject<Block> SPECULO_COBBLESTONE_WALL = register("speculo_cobblestone_wall", () -> new WallBlock(Properties.copy(SpeculativeBlocks.SPECULO_COBBLESTONE.get())));

    public static final RegistryObject<Block> SPECULO_SAND = register("speculo_sand", () -> new SandBlock(16222542, Properties.of(Material.SAND, MaterialColor.COLOR_ORANGE).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> SPECULO_SANDSTONE = register("speculo_sandstone", stone());
    public static final RegistryObject<Block> CUT_SPECULO_SANDSTONE = register("cut_speculo_sandstone", stone());
    public static final RegistryObject<Block> CHISELED_SPECULO_SANDSTONE = register("chiseled_speculo_sandstone", stone());
    public static final RegistryObject<Block> SPECULO_SANDSTONE_STAIRS = register("speculo_sandstone_stairs", () -> new StairBlock(() -> SpeculativeBlocks.SPECULO_SANDSTONE.get().defaultBlockState(), Block.Properties.copy(SpeculativeBlocks.SPECULO_SANDSTONE.get())));
    public static final RegistryObject<Block> SPECULO_SANDSTONE_SLAB = register("speculo_sandstone_slab", () -> new SlabBlock(Properties.copy(SpeculativeBlocks.SPECULO_SANDSTONE.get())));
    public static final RegistryObject<Block> SPECULO_SANDSTONE_WALL = register("speculo_sandstone_wall", () -> new WallBlock(Properties.copy(SpeculativeBlocks.SPECULO_SANDSTONE.get())));

    // Miscellaneous
    public static final RegistryObject<Block> SPECULO_TNT = register("speculo_tnt", SpeculoTNTBlock::new);
    public static final RegistryObject<Block> GREENSTONE_TORCH = BLOCKS.register("greenstone_torch", () -> new SpeculativeTorchBlock(Properties.copy(Blocks.TORCH).lightLevel(state -> 9)));
    public static final RegistryObject<Block> GREENSTONE_WALL_TORCH = BLOCKS.register("greenstone_wall_torch", () -> new SpeculativeTorchBlock.SpeculativeWallTorchBlock(Properties.copy(Blocks.WALL_TORCH).lightLevel(state -> 9).lootFrom(SpeculativeBlocks.GREENSTONE_TORCH)));
    public static final RegistryObject<Block> GREENSTONE_LANTERN = register("greenstone_lantern", () -> new LanternBlock(Properties.copy(Blocks.LANTERN).lightLevel(state -> 9)));

    private static Supplier<Block> stone() {
        return () -> new Block(Properties.of(Material.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops());
    }

    /*private static Block stoneBlock(Supplier<Block> cobblestone) {
        return new SpeculativeDataGenBlock(Properties.of(Material.STONE).strength(1.5F, 6.0F), block -> IDataGenLoot.Helper.createSingleItemTableWithSilkTouch(block, cobblestone.get()));
    }*/

    private static Supplier<Block> oreBlock(float hardness, float resistance) {
        return () -> new Block(Properties.of(Material.METAL).sound(SoundType.METAL).strength(hardness, resistance).requiresCorrectToolForDrops());
    }

    private static Supplier<Block> logBlock(MaterialColor topColor, MaterialColor barkColor) {
        return () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).strength(2.0F).sound(SoundType.WOOD));
    }

    private static Supplier<Block> cobblestone() {
        return () -> new Block(Properties.of(Material.STONE).strength(2.0F, 6.0F).requiresCorrectToolForDrops());
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> sup) {
        return registerWithBlockItem(name, sup, block -> new BlockItem(block, new Item.Properties().tab(Speculative.tab())));
    }

    private static <T extends Block> RegistryObject<T> registerWithBlockItem(String name, Supplier<T> blockSup, Function<T, ? extends Item> itemFunc) {
        RegistryObject<T> ret = BLOCKS.register(name, blockSup);
        BLOCK_ITEMS.register(name, () -> itemFunc.apply(ret.get()));
        return ret;
    }

    private static RegistryObject<Block> registerPotted(RegistryObject<Block> plant) {
        RegistryObject<Block> ret = BLOCKS.register("potted_".concat(plant.getId().getPath()), () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, plant, Properties.copy(Blocks.FLOWER_POT)));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(plant.getId(), ret);
        return ret;
    }

    public static Optional<Item> getItemFromBlock(RegistryObject<Block> block) {
        return BLOCK_ITEMS.getEntries().stream().filter(obj -> obj.getId().equals(block.getId())).findFirst().filter(RegistryObject::isPresent).map(RegistryObject::get);
    }

    public static RegistryObject<Item> getItem(RegistryObject<Block> block) {
        return BLOCK_ITEMS.getEntries().stream().filter(obj -> obj.getId().equals(block.getId())).findFirst().orElseThrow(() -> new NullPointerException("Block with id '" + block.getId() + "' has no direct block item!"));
    }

    public static final class Tags {
        public static final TagKey<Block> SPECULO_LOGS = BLOCKS.createTagKey("speculo_logs");
        public static final TagKey<Block> TOURMALINE_LOGS = BLOCKS.createTagKey("tourmaline_logs");
        public static final TagKey<Block> CARVABLE_BLOCKS = BLOCKS.createTagKey("carvable_blocks");
        public static final TagKey<Block> URANIUM_PROOF = BLOCKS.createTagKey("uranium_proof");
    }
}
