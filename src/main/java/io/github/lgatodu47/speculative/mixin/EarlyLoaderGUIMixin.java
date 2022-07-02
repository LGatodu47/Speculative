package io.github.lgatodu47.speculative.mixin;

import net.minecraftforge.fml.client.EarlyLoaderGUI;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EarlyLoaderGUI.class)
public class EarlyLoaderGUIMixin {
    @Redirect(method = "renderBackground", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V", remap = false), remap = false)
    private void redirect_glColor4f(float r, float g, float b, float a) {
        GL11.glColor4f(0.07843F, 0.07843F, 0.07843F, a);
    }
}
