package io.github.lgatodu47.speculative.client.dri;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.lgatodu47.speculative.Speculative;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

public abstract class SpeculativeDimensionRenderInfo extends DimensionRenderInfo {
    public SpeculativeDimensionRenderInfo(float cloudHeight, boolean hasSky, FogType fogType, boolean isDarker, boolean isBrighter) {
        super(cloudHeight, hasSky, fogType, isDarker, isBrighter);
        this.setWeatherRenderHandler(this::renderWeather);
        this.setWeatherParticleRenderHandler(this::renderWeatherParticles);
        this.setSkyRenderHandler(this::renderSky);
        this.setCloudRenderHandler(this::renderClouds);
    }

    @Override
    public Vector3d func_230494_a_(Vector3d fogColor, float dayTime) {
        return getFogColor(fogColor, dayTime);
    }

    protected abstract Vector3d getFogColor(Vector3d initialFogColor, float dayTime);

    @Override
    public boolean func_230493_a_(int x, int y) {
        return hasNearFog(x, y);
    }

    protected abstract boolean hasNearFog(int x, int y);

    protected void renderWeather(int ticks, float partialTicks, ClientWorld world, Minecraft mc, LightTexture lightmap, double x, double y, double z) {}

    protected void renderWeatherParticles(int ticks, ClientWorld world, Minecraft mc, ActiveRenderInfo activeRenderInfo) {}

    protected void renderSky(int ticks, float partialTicks, MatrixStack stack, ClientWorld world, Minecraft mc) {}

    protected void renderClouds(int ticks, float partialTicks, MatrixStack stack, ClientWorld world, Minecraft mc, double viewEntityX, double viewEntityY, double viewEntityZ) {}

    public static void registerDimensionRenderInfo() {
        registerInfo("speculo_world", new SpeculoWorldRenderInfo());
    }

    private static Field ID_TO_INFO_MAP;

    @SuppressWarnings("unchecked")
    private static void registerInfo(String name, DimensionRenderInfo info) {
        if(ID_TO_INFO_MAP == null) {
            ID_TO_INFO_MAP = ObfuscationReflectionHelper.findField(DimensionRenderInfo.class, "field_239208_a_");
            ID_TO_INFO_MAP.setAccessible(true);
        }

        try {
            ((Object2ObjectMap<ResourceLocation, DimensionRenderInfo>) ID_TO_INFO_MAP.get(null)).put(new ResourceLocation(Speculative.MODID, name), info);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
