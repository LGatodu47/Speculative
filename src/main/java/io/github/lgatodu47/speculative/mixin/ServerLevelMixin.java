package io.github.lgatodu47.speculative.mixin;

import io.github.lgatodu47.speculative.common.world.DimensionLevelData;
import net.minecraft.world.level.GameRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @Shadow
    @Final
    private ServerLevelData serverLevelData;

    @Shadow
    public abstract void setDayTime(long time);

    @Inject(method = "tickTime", at = @At("TAIL"))
    private void inject_tickTime(CallbackInfo ci) {
        if (this.serverLevelData instanceof DimensionLevelData) {
            this.serverLevelData.setGameTime(this.serverLevelData.getGameTime() + 1L);

            if (this.serverLevelData.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                this.setDayTime(this.serverLevelData.getDayTime() + 1L);
            }
        }
    }
}
