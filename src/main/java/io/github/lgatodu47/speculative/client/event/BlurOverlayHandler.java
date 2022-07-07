package io.github.lgatodu47.speculative.client.event;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.init.SpeculativeMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Speculative.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public class BlurOverlayHandler {
    @SubscribeEvent
    public static void renderOverlay(ClientTickEvent event) {
        if (Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().player.hasEffect(SpeculativeMobEffects.C6_BLINDNESS.get())) {
                Minecraft.getInstance().gameRenderer.loadEffect(new ResourceLocation(Speculative.MODID, "shaders/post/blur.json"));
            } else {
                Minecraft.getInstance().gameRenderer.shutdownEffect();
            }
        }
    }
}
