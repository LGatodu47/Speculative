package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.entity.AncientLordEntity;
import io.github.lgatodu47.speculative.common.entity.SpeculativeBoatEntity;
import io.github.lgatodu47.speculative.common.entity.SpeculoPigEntity;
import io.github.lgatodu47.speculative.common.entity.SpeculoTNTEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Pig;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Speculative.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpeculativeEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Speculative.MODID);

    public static final RegistryObject<EntityType<SpeculoPigEntity>> SPECULO_PIG = ENTITY_TYPES.register("speculo_pig",
            () -> EntityType.Builder.of(SpeculoPigEntity::new, MobCategory.AMBIENT).sized(0.9F, 0.9F).build(new ResourceLocation(Speculative.MODID, "speculo_pig").toString()));
    public static final RegistryObject<EntityType<SpeculoTNTEntity>> SPECULO_TNT = ENTITY_TYPES.register("speculo_tnt",
            () -> EntityType.Builder.<SpeculoTNTEntity>of(SpeculoTNTEntity::new, MobCategory.MISC).sized(0.98F, 0.98F).fireImmune().build(new ResourceLocation(Speculative.MODID, "speculo_tnt").toString()));
    public static final RegistryObject<EntityType<SpeculativeBoatEntity>> SPECULATIVE_BOAT = ENTITY_TYPES.register("speculative_boat",
            () -> EntityType.Builder.<SpeculativeBoatEntity>of(SpeculativeBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build(new ResourceLocation(Speculative.MODID, "speculative_boat").toString()));
    public static final RegistryObject<EntityType<AncientLordEntity>> ANCIENT_LORD = ENTITY_TYPES.register("ancient_lord",
            () -> EntityType.Builder.of(AncientLordEntity::new, MobCategory.CREATURE).sized(0.6F, 1.95F).clientTrackingRange(10).build(new ResourceLocation(Speculative.MODID, "ancient_lord").toString()));

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(SPECULO_PIG.get(), Pig.createAttributes().build());
        event.put(ANCIENT_LORD.get(), Mob.createMobAttributes().build());
    }
}
