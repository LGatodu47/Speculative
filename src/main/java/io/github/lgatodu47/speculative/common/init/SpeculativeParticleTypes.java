package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = Speculative.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class SpeculativeParticleTypes
{
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Speculative.MODID);
	
	public static final RegistryObject<BasicParticleType> GREEN_FLAME = PARTICLE_TYPES.register("green_flame", () -> new BasicParticleType(false));

	@SubscribeEvent
	public static void registerParticleFactories(ParticleFactoryRegisterEvent event)
	{
		Minecraft.getInstance().particleEngine.register(SpeculativeParticleTypes.GREEN_FLAME.get(), FlameParticle.Factory::new);
	}
}
