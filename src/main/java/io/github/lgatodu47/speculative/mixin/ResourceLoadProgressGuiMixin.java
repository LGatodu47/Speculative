package io.github.lgatodu47.speculative.mixin;

import net.minecraft.client.gui.ResourceLoadProgressGui;
import net.minecraft.util.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ResourceLoadProgressGui.class)
public class ResourceLoadProgressGuiMixin {
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ColorHelper$PackedColor;packColor(IIII)I"))
    private static int redirect_packColor(int alpha, int red, int green, int blue) {
        return ColorHelper.PackedColor.packColor(alpha, 20, 20, 20);
    }
}
