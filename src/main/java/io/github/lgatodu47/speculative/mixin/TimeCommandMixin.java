package io.github.lgatodu47.speculative.mixin;

import io.github.lgatodu47.speculative.common.world.DimensionLevelData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.TimeCommand;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TimeCommand.class)
public class TimeCommandMixin {
    @Redirect(method = "setTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setDayTime(J)V"))
    private static void redirect_setDayTime(ServerLevel level, long time, CommandSourceStack source) {
        if(source.getLevel().getLevelData() instanceof DimensionLevelData) {
            if(level.dimension().equals(source.getLevel().dimension())) {
                level.setDayTime(time);
            }
        } else {
            if(!(level.getLevelData() instanceof DimensionLevelData)) {
                level.setDayTime(time);
            }
        }
    }
}
