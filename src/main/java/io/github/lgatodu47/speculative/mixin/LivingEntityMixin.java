package io.github.lgatodu47.speculative.mixin;

import io.github.lgatodu47.speculative.common.init.SpeculativeMobEffects;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F", remap = false))
    private float redirect_getSlipperiness(BlockState state, LevelReader reader, BlockPos pos, Entity entity) {
        float slipperiness = state.getFriction(reader, pos, entity);

        if(((LivingEntity) (Object) this).hasEffect(SpeculativeMobEffects.SULFURIC_SPEED.get())) {
            slipperiness = 0.98F;
        }

        return slipperiness;
    }
}
