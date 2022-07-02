package io.github.lgatodu47.speculative.mixin;

import io.github.lgatodu47.speculative.common.init.SpeculativeEffects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getSlipperiness(Lnet/minecraft/world/IWorldReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)F", remap = false))
    private float redirect_getSlipperiness(BlockState state, IWorldReader reader, BlockPos pos, Entity entity) {
        float slipperiness = state.getSlipperiness(reader, pos, entity);

        if(((LivingEntity) (Object) this).isPotionActive(SpeculativeEffects.SULFURIC_SPEED.get())) {
            slipperiness = 0.98F;
        }

        return slipperiness;
    }
}
