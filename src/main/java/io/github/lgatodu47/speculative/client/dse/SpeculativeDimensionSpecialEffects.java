package io.github.lgatodu47.speculative.client.dse;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.util.SpeculativeReflectionHelper;
import io.github.lgatodu47.speculative.util.SpeculativeUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;

public abstract class SpeculativeDimensionSpecialEffects extends DimensionSpecialEffects {
    public SpeculativeDimensionSpecialEffects(float cloudHeight, boolean hasSky, SkyType fogType, boolean isDarker, boolean isBrighter) {
        super(cloudHeight, hasSky, fogType, isDarker, isBrighter);
        this.setWeatherRenderHandler(this::renderWeather);
        this.setWeatherParticleRenderHandler(this::renderWeatherParticles);
        this.setSkyRenderHandler(this::renderSky);
        this.setCloudRenderHandler(this::renderClouds);
    }

    protected void renderWeather(int ticks, float partialTicks, ClientLevel world, Minecraft mc, LightTexture lightmap, double x, double y, double z) {}

    protected void renderWeatherParticles(int ticks, ClientLevel world, Minecraft mc, Camera activeRenderInfo) {}

    protected void renderSky(int ticks, float partialTicks, PoseStack stack, ClientLevel world, Minecraft mc) {}

    protected void renderClouds(int ticks, float partialTicks, PoseStack stack, ClientLevel world, Minecraft mc, double viewEntityX, double viewEntityY, double viewEntityZ) {}

    public static void registerDimensionRenderInfo() {
        registerInfo("speculo_world", new SpeculoWorldEffects());
    }

    private static void registerInfo(String name, DimensionSpecialEffects info) {
        SpeculativeReflectionHelper.getStaticFieldValue(DimensionSpecialEffects.class, "f_108857_", SpeculativeUtils.<Object2ObjectMap<ResourceLocation, DimensionSpecialEffects>>classHack(Object2ObjectMap.class))
                .ifPresent(map -> map.put(new ResourceLocation(Speculative.MODID, name), info));
    }
}
