package io.github.lgatodu47.speculative.mixin;

import com.mojang.serialization.Dynamic;
import io.github.lgatodu47.speculative.common.world.DimensionWorldInfo;
import io.github.lgatodu47.speculative.common.world.ExtendedServerWorldInfo;
import net.minecraft.nbt.INBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.IServerWorldInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Supplier;

@Mixin(DerivedWorldInfo.class)
public class DerivedWorldInfoMixin implements ExtendedServerWorldInfo {
    @Shadow @Final private IServerWorldInfo delegate;

    @Override
    public void addDimensionWorldInfo(RegistryKey<World> world, DimensionWorldInfo info) {
        ExtendedServerWorldInfo.get(this.delegate).addDimensionWorldInfo(world, info);
    }

    @Override
    public DimensionWorldInfo getDimensionWorldInfo(RegistryKey<World> world, Supplier<DimensionWorldInfo> defaultValue) {
        return ExtendedServerWorldInfo.get(this.delegate).getDimensionWorldInfo(world, defaultValue);
    }

    @Override
    public void deserializeDimensionWorldInfo(Dynamic<INBT> dynamic) {
    }
}
