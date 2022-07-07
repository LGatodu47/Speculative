package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.block.entity.BossSummonerBlockEntity;
import io.github.lgatodu47.speculative.common.block.entity.CentrifugeBlockEntity;
import io.github.lgatodu47.speculative.common.block.entity.SpeculoosSummonerBlockEntity;
import io.github.lgatodu47.speculative.common.block.entity.UraniumBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SpeculativeBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Speculative.MODID);

    public static final RegistryObject<BlockEntityType<SpeculoosSummonerBlockEntity>> SPECULOOS_SUMMONER = BLOCK_ENTITY_TYPES.register("speculoos_summoner", () -> BlockEntityType.Builder.of(SpeculoosSummonerBlockEntity::new, SpeculativeBlocks.SPECULOOS_SUMMONER.get()).build(null));
    public static final RegistryObject<BlockEntityType<CentrifugeBlockEntity>> CENTRIFUGE = BLOCK_ENTITY_TYPES.register("centrifuge", () -> BlockEntityType.Builder.of(CentrifugeBlockEntity::new, SpeculativeBlocks.CENTRIFUGE.get()).build(null));
    public static final RegistryObject<BlockEntityType<BossSummonerBlockEntity>> BOSS_SUMMONER = BLOCK_ENTITY_TYPES.register("boss_summoner", () -> BlockEntityType.Builder.of(BossSummonerBlockEntity::new, SpeculativeBlocks.SPECULO_BOSS_SUMMONER.get()).build(null));
    public static final RegistryObject<BlockEntityType<UraniumBlockEntity>> URANIUM_BLOCK = BLOCK_ENTITY_TYPES.register("uranium_block", () -> BlockEntityType.Builder.of(UraniumBlockEntity::new, SpeculativeBlocks.URANIUM_238_BLOCK.get(), SpeculativeBlocks.URANIUM_235_BLOCK.get(), SpeculativeBlocks.URANIUM_234_BLOCK.get()).build(null));
}
