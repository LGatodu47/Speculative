package io.github.lgatodu47.speculative.client.event;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.init.SpeculativeBiomes;
import io.github.lgatodu47.speculative.common.init.SpeculativeFluids;
import io.github.lgatodu47.speculative.common.init.SpeculativeWorlds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = Speculative.MODID, value = Dist.CLIENT)
public class FogHandler {
    @SubscribeEvent
    public static void changeFogDensity(FogDensity event) {
        Entity entity = event.getInfo().getRenderViewEntity();
        if (entity.world != null && entity.world.isRemote()) { // The world should always be client, but we never really know
            if (entity.world.getDimensionKey().equals(SpeculativeWorlds.SPECULO_WORLD)) {
                float density = getDensity(Minecraft.getInstance().gameSettings.renderDistanceChunks, (ClientWorld) entity.world, event.getInfo()) * 0.03F;
                if(density >= 0.005F) { // 0.005 is near the default value
                    event.setDensity(density);
                    event.setCanceled(true); // We need to cancel the event in order to apply our fog density.
                }
            }
        }
    }

    private static float getDensity(int renderDistanceChunks, ClientWorld world, ActiveRenderInfo info) {
        BiomeManager manager = world.getBiomeManager();
        Vector3d areaVec = info.getProjectedView().subtract(2, 2, 2).scale(0.25);

        // These are a bunch of magic numbers taken from vanilla, I don't know what all that's stuff means, but I guess you can try to decode it by yourself :)
        float factor = 1F - (float) Math.pow(0.25F + 0.75F * (float) renderDistanceChunks / 32F, 0.25);
        int hasBiome = checkBiomes(SpeculativeBiomes.SPECULO_DESERT, world.getBiome(info.getBlockPos())) ? 1 : 0;
        // Normally I wouldn't need to use vectors, but I'm doing it because of this pretty useful vanilla function
        Vector3d scaleVec = CubicSampler.func_240807_a_(areaVec, (x, y, z) -> checkBiomes(SpeculativeBiomes.SPECULO_DESERT, manager.getBiomeAtPosition(x, y, z))
                ? new Vector3d(1, 1, 1)
                : Vector3d.ZERO);

        return (float) (scaleVec.x + (hasBiome - scaleVec.x) * factor);
    }

    private static boolean checkBiomes(RegistryObject<Biome> moddedBiome, Biome worldBiome) {
        return moddedBiome.getId().equals(worldBiome.getRegistryName());
    }

    @SubscribeEvent
    public static void changeFogColor(FogColors event) {
        Entity entity = event.getInfo().getRenderViewEntity();
        if(entity.isInWater() && event.getInfo().getFluidState().isTagged(SpeculativeFluids.Tags.UNSTABLE_WATER)) {
            // Unstable Water colors
            event.setRed(0.204F);
            event.setGreen(1F);
            event.setBlue(0.58F);
        }
    }
}