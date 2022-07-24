package io.github.lgatodu47.speculative.mixin;

import io.github.lgatodu47.speculative.util.SpeculativeWoodTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
    @Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
    private void inject_isValid(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if(this.equals(BlockEntityType.SIGN)) {
            cir.setReturnValue(SpeculativeWoodTypes.SIGN_BLOCKS_VIEW.contains(state.getBlock()));
        }
    }
}
