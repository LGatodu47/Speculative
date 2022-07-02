package io.github.lgatodu47.speculative.mixin;

import io.github.lgatodu47.speculative.common.world.DimensionWorldInfo;
import net.minecraft.command.CommandSource;
import net.minecraft.command.impl.TimeCommand;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TimeCommand.class)
public class TimeCommandMixin {
    @Redirect(method = "setTime(Lnet/minecraft/command/CommandSource;I)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/server/ServerWorld;setDayTime(J)V"))
    private static void redirect_setDayTime(ServerWorld world, long time, CommandSource source) {
        if(source.getWorld().getWorldInfo() instanceof DimensionWorldInfo) {
            if(world.getDimensionKey().equals(source.getWorld().getDimensionKey())) {
                world.setDayTime(time);
            }
        } else {
            if(!(world.getWorldInfo() instanceof DimensionWorldInfo)) {
                world.setDayTime(time);
            }
        }
    }
}
