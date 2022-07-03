package io.github.lgatodu47.speculative.client.dri;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.Random;

import static net.minecraft.client.renderer.WorldRenderer.getLightColor;

public class SpeculoWorldRenderInfo extends SpeculativeDimensionRenderInfo {
    public SpeculoWorldRenderInfo() {
        super(128, true, DimensionRenderInfo.FogType.NORMAL, false, false);
        setCloudRenderHandler(null);
        setSkyRenderHandler(null);
        setWeatherParticleRenderHandler(null);
    }

    @Override
    protected Vector3d getFogColor(Vector3d initialFogColor, float dayTime) {
        return initialFogColor;
    }

    @Override
    protected boolean hasNearFog(int x, int y) {
        return false;
    }

    private static final ResourceLocation RAIN_TEXTURES = new ResourceLocation("textures/environment/rain.png");
    private static final ResourceLocation SNOW_TEXTURES = new ResourceLocation("textures/environment/snow.png");

    @Override
    protected void renderWeather(int ticks, float partialTicks, ClientWorld world, Minecraft mc, LightTexture lightmap, double x, double y, double z) {
        float f = world.getRainLevel(partialTicks);
        if (!(f <= 0.0F)) {
            lightmap.turnOnLightLayer();

            int floorX = MathHelper.floor(x);
            int floorY = MathHelper.floor(y);
            int floorZ = MathHelper.floor(z);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();

            RenderSystem.enableAlphaTest();
            RenderSystem.disableCull();
            RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.defaultAlphaFunc();
            RenderSystem.enableDepthTest();
            int distance = 5;
            if (Minecraft.useFancyGraphics()) {
                distance = 10;
            }

            RenderSystem.depthMask(Minecraft.useShaderTransparency());

            int i1 = -1;
            float f1 = ticks + partialTicks;
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            BlockPos.Mutable mutablePos = new BlockPos.Mutable();

            for(int renderZ = floorZ - distance; renderZ <= floorZ + distance; ++renderZ) {
                for(int renderX = floorX - distance; renderX <= floorX + distance; ++renderX) {
                    int index = (renderZ - floorZ + 16) * 32 + renderX - floorX + 16;
                    double d0 = getRainSizeX(mc.levelRenderer, index) * 0.5D;
                    double d1 = getRainSizeZ(mc.levelRenderer, index) * 0.5D;
                    mutablePos.set(renderX, 0, renderZ);
                    Biome biome = world.getBiome(mutablePos);

                    if (biome.getPrecipitation() != Biome.RainType.NONE) {
                        int blockY = world.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING, mutablePos).getY();
                        int rainMinY = floorY - distance;
                        int rainY = floorY + distance;
                        if (rainMinY < blockY) {
                            rainMinY = blockY;
                        }

                        if (rainY < blockY) {
                            rainY = blockY;
                        }

                        int l2 = blockY;
                        if (blockY < floorY) {
                            l2 = floorY;
                        }

                        if (rainMinY != rainY) {
                            Random random = new Random((long) renderX * renderX * 3121 + renderX * 45238971L ^ (long) renderZ * renderZ * 418711 + renderZ * 13761L);
                            mutablePos.set(renderX, rainMinY, renderZ);
                            float temperature = biome.getTemperature(mutablePos);
                            if (temperature >= 0.15F) {
                                if (i1 != 0) {
                                    if (i1 >= 0) {
                                        tessellator.end();
                                    }

                                    i1 = 0;
                                    mc.getTextureManager().bind(RAIN_TEXTURES);
                                    bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE);
                                }

                                int i3 = ticks + renderX * renderX * 3121 + renderX * 45238971 + renderZ * renderZ * 418711 + renderZ * 13761 & 31;
                                float f3 = -((float)i3 + partialTicks) / 32.0F * (3.0F + random.nextFloat());
                                double d2 = (double)((float)renderX + 0.5F) - x;
                                double d4 = (double)((float)renderZ + 0.5F) - z;
                                float f4 = MathHelper.sqrt(d2 * d2 + d4 * d4) / (float)distance;
                                float f5 = ((1.0F - f4 * f4) * 0.5F + 0.5F) * f;
                                mutablePos.set(renderX, l2, renderZ);
                                int j3 = getLightColor(world, mutablePos);
                                bufferbuilder.vertex((double)renderX - x - d0 + 0.5D, (double)rainY - y, (double)renderZ - z - d1 + 0.5D).uv(0.0F, (float)rainMinY * 0.25F + f3).color(0.302F, 0.165F, 0, f5).uv2(j3).endVertex();
                                bufferbuilder.vertex((double)renderX - x + d0 + 0.5D, (double)rainY - y, (double)renderZ - z + d1 + 0.5D).uv(1.0F, (float)rainMinY * 0.25F + f3).color(0.302F, 0.165F, 0F, f5).uv2(j3).endVertex();
                                bufferbuilder.vertex((double)renderX - x + d0 + 0.5D, (double)rainMinY - y, (double)renderZ - z + d1 + 0.5D).uv(1.0F, (float)rainY * 0.25F + f3).color(0.302F, 0.165F, 0F, f5).uv2(j3).endVertex();
                                bufferbuilder.vertex((double)renderX - x - d0 + 0.5D, (double)rainMinY - y, (double)renderZ - z - d1 + 0.5D).uv(0.0F, (float)rainY * 0.25F + f3).color(0.302F, 0.165F, 0F, f5).uv2(j3).endVertex();
                            } else {
                                if (i1 != 1) {
                                    if (i1 >= 0) {
                                        tessellator.end();
                                    }

                                    i1 = 1;
                                    mc.getTextureManager().bind(SNOW_TEXTURES);
                                    bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE);
                                }

                                float f6 = -((float)(ticks & 511) + partialTicks) / 512.0F;
                                float f7 = (float)(random.nextDouble() + (double)f1 * 0.01D * (double)((float)random.nextGaussian()));
                                float f8 = (float)(random.nextDouble() + (double)(f1 * (float)random.nextGaussian()) * 0.001D);
                                double d3 = (double)((float)renderX + 0.5F) - x;
                                double d5 = (double)((float)renderZ + 0.5F) - z;
                                float f9 = MathHelper.sqrt(d3 * d3 + d5 * d5) / (float)distance;
                                float f10 = ((1.0F - f9 * f9) * 0.3F + 0.5F) * f;
                                mutablePos.set(renderX, l2, renderZ);
                                int k3 = getLightColor(world, mutablePos);
                                int l3 = k3 >> 16 & '\uffff';
                                int i4 = (k3 & '\uffff') * 3;
                                int j4 = (l3 * 3 + 240) / 4;
                                int k4 = (i4 * 3 + 240) / 4;
                                bufferbuilder.vertex((double)renderX - x - d0 + 0.5D, (double)rainY - y, (double)renderZ - z - d1 + 0.5D).uv(0.0F + f7, (float)rainMinY * 0.25F + f6 + f8).color(1.0F, 1.0F, 1.0F, f10).uv2(k4, j4).endVertex();
                                bufferbuilder.vertex((double)renderX - x + d0 + 0.5D, (double)rainY - y, (double)renderZ - z + d1 + 0.5D).uv(1.0F + f7, (float)rainMinY * 0.25F + f6 + f8).color(1.0F, 1.0F, 1.0F, f10).uv2(k4, j4).endVertex();
                                bufferbuilder.vertex((double)renderX - x + d0 + 0.5D, (double)rainMinY - y, (double)renderZ - z + d1 + 0.5D).uv(1.0F + f7, (float)rainY * 0.25F + f6 + f8).color(1.0F, 1.0F, 1.0F, f10).uv2(k4, j4).endVertex();
                                bufferbuilder.vertex((double)renderX - x - d0 + 0.5D, (double)rainMinY - y, (double)renderZ - z - d1 + 0.5D).uv(0.0F + f7, (float)rainY * 0.25F + f6 + f8).color(1.0F, 1.0F, 1.0F, f10).uv2(k4, j4).endVertex();
                            }
                        }
                    }
                }
            }

            if (i1 >= 0) {
                tessellator.end();
            }

            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            RenderSystem.defaultAlphaFunc();
            RenderSystem.disableAlphaTest();
            lightmap.turnOffLightLayer();
        }
    }

    private static Field RAIN_SIZE_X;
    private static Field RAIN_SIZE_Z;

    private static float getRainSizeX(WorldRenderer renderer, int index) {
        if(RAIN_SIZE_X == null) {
            RAIN_SIZE_X = ObfuscationReflectionHelper.findField(WorldRenderer.class, "rainSizeX");
            RAIN_SIZE_X.setAccessible(true);
        }
        try {
            return ((float[]) RAIN_SIZE_X.get(renderer))[index];
        } catch (IllegalAccessException ignored) {
        }

        return 1.0f;
    }

    private static float getRainSizeZ(WorldRenderer renderer, int index) {
        if(RAIN_SIZE_Z == null) {
            RAIN_SIZE_Z = ObfuscationReflectionHelper.findField(WorldRenderer.class, "rainSizeZ");
            RAIN_SIZE_Z.setAccessible(true);
        }
        try {
            return ((float[]) RAIN_SIZE_Z.get(renderer))[index];
        } catch (IllegalAccessException ignored) {
        }

        return 1.0f;
    }
}
