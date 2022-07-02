package io.github.lgatodu47.speculative.mixin;

import io.github.lgatodu47.speculative.common.world.DimensionWorldInfo;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IServerWorldInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow
    @Final
    private IServerWorldInfo serverWorldInfo;

    @Shadow
    public abstract void setDayTime(long time);

    @Inject(method = "tickWorld", at = @At("TAIL"))
    private void inject_tickWorld(CallbackInfo ci) {
        if (this.serverWorldInfo instanceof DimensionWorldInfo) {
            this.serverWorldInfo.setGameTime(this.serverWorldInfo.getGameTime() + 1L);

            if (this.serverWorldInfo.getGameRulesInstance().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
                this.setDayTime(this.serverWorldInfo.getDayTime() + 1L);
            }
        }
    }
}
