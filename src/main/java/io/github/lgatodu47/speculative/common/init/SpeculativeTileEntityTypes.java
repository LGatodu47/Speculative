package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.tile.BossSummonerTileEntity;
import io.github.lgatodu47.speculative.common.tile.CentrifugeTileEntity;
import io.github.lgatodu47.speculative.common.tile.SpeculoosSummonerTileEntity;
import io.github.lgatodu47.speculative.common.tile.UraniumBlockTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SpeculativeTileEntityTypes
{
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Speculative.MODID);

    public static final RegistryObject<TileEntityType<SpeculoosSummonerTileEntity>> SPECULOOS_SUMMONER_TILE_ENTITY = TILE_ENTITY_TYPES.register("speculoos_summoner_tile_entity", () -> TileEntityType.Builder.create(SpeculoosSummonerTileEntity::new, SpeculativeBlocks.SPECULOOS_SUMMONER.get()).build(null));
    public static final RegistryObject<TileEntityType<CentrifugeTileEntity>> CENTRIFUGE_TILE_ENTITY = TILE_ENTITY_TYPES.register("centrifuge_tile_entity", () -> TileEntityType.Builder.create(CentrifugeTileEntity::new, SpeculativeBlocks.CENTRIFUGE.get()).build(null));
    public static final RegistryObject<TileEntityType<BossSummonerTileEntity>> BOSS_SUMMONER = TILE_ENTITY_TYPES.register("boss_summoner", () -> TileEntityType.Builder.create(BossSummonerTileEntity::new, SpeculativeBlocks.SPECULO_BOSS_SUMMONER.get()).build(null));
    public static final RegistryObject<TileEntityType<UraniumBlockTileEntity>> URANIUM_BLOCK = TILE_ENTITY_TYPES.register("uranium_block", () -> TileEntityType.Builder.create(UraniumBlockTileEntity::new, SpeculativeBlocks.URANIUM_238_BLOCK.get(), SpeculativeBlocks.URANIUM_235_BLOCK.get(), SpeculativeBlocks.URANIUM_234_BLOCK.get()).build(null));
}
