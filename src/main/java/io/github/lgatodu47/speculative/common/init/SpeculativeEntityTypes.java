package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.entity.SpeculoPigEntity;
import io.github.lgatodu47.speculative.common.entity.SpeculoTNTEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Speculative.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpeculativeEntityTypes
{
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Speculative.MODID);

	public static final RegistryObject<EntityType<SpeculoPigEntity>> SPECULO_PIG = ENTITY_TYPES.register("speculo_pig",
			() -> EntityType.Builder.<SpeculoPigEntity>of(SpeculoPigEntity::new, EntityClassification.AMBIENT).sized(0.9F, 0.9F).build(new ResourceLocation(Speculative.MODID, "speculo_pig").toString()));
	public static final RegistryObject<EntityType<SpeculoTNTEntity>> SPECULO_TNT = ENTITY_TYPES.register("speculo_tnt",
			() -> EntityType.Builder.<SpeculoTNTEntity>of(SpeculoTNTEntity::new, EntityClassification.MISC).sized(0.98F, 0.98F).fireImmune().build(new ResourceLocation(Speculative.MODID, "speculo_tnt").toString()));

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(SPECULO_PIG.get(), PigEntity.createAttributes().build());
	}
}
