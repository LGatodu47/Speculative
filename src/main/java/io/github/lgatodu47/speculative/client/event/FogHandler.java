package io.github.lgatodu47.speculative.client.event;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.init.SpeculativeBiomes;
import io.github.lgatodu47.speculative.common.init.SpeculativeFluids;
import io.github.lgatodu47.speculative.common.init.SpeculativeWorlds;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.CubicSampler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = Bus.FORGE, modid = Speculative.MODID, value = Dist.CLIENT)
public class FogHandler {
    @SubscribeEvent
    public static void onRenderFog(RenderFogEvent event) {
        Entity entity = event.getCamera().getEntity();
        if (entity.level.isClientSide()) { // The world should always be client, but we never really know
            if (entity.level.dimension().equals(SpeculativeWorlds.SPECULO_WORLD)) {
                float density = getDensity(Minecraft.getInstance().options.renderDistance, (ClientLevel) entity.level, event.getCamera()) * 0.03F;
                if(density >= 0.005F) {
                    event.setNearPlaneDistance(0 * (density - 0.005F));
                    event.setFarPlaneDistance(event.getFarPlaneDistance() * (1 - density * 24));
                    event.setCanceled(true); // We need to cancel the event in order to apply our fog density.
                }
            }
        }
    }

    private static float getDensity(int renderDistanceChunks, ClientLevel world, Camera camera) {
        BiomeManager manager = world.getBiomeManager();
        Vec3 areaVec = camera.getPosition().subtract(2, 2, 2).scale(0.25);

        // These are a bunch of magic numbers taken from vanilla, I don't know what all that's stuff means, but I guess you can try to decode it by yourself :)
        float factor = 1F - (float) Math.pow(0.25F + 0.75F * (float) renderDistanceChunks / 32F, 0.25);
        int hasBiome = checkBiomes(SpeculativeBiomes.SPECULO_DESERT, world.getBiome(camera.getBlockPosition()).value()) ? 1 : 0;
        // Normally I wouldn't need to use vectors, but I'm doing it because of this pretty useful vanilla function
        Vec3 scaleVec = CubicSampler.gaussianSampleVec3(areaVec, (x, y, z) -> checkBiomes(SpeculativeBiomes.SPECULO_DESERT, manager.getNoiseBiomeAtQuart(x, y, z).value())
                ? new Vec3(1, 1, 1)
                : Vec3.ZERO);

        return (float) (scaleVec.x + (hasBiome - scaleVec.x) * factor);
    }

    private static boolean checkBiomes(RegistryObject<Biome> moddedBiome, Biome worldBiome) {
        if(worldBiome == null) {
            return false;
        }
        return moddedBiome.getId().equals(worldBiome.getRegistryName());
    }

    @SubscribeEvent
    public static void changeFogColor(FogColors event) {
        Entity entity = event.getCamera().getEntity();
        if(entity.isInWater() && entity.level.getFluidState(event.getCamera().getBlockPosition()).is(SpeculativeFluids.Tags.UNSTABLE_WATER)) {
            // Unstable Water colors
            event.setRed(0.204F);
            event.setGreen(1F);
            event.setBlue(0.58F);
        }
    }
}