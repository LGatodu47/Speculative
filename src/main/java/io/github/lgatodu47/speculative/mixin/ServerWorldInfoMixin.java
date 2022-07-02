package io.github.lgatodu47.speculative.mixin;

import com.mojang.serialization.Dynamic;
import io.github.lgatodu47.speculative.common.world.DimensionWorldInfo;
import io.github.lgatodu47.speculative.common.world.ExtendedServerWorldInfo;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.storage.ServerWorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(ServerWorldInfo.class)
public class ServerWorldInfoMixin implements ExtendedServerWorldInfo {
    @Unique
    private final Map<RegistryKey<World>, DimensionWorldInfo> dimensionWorldInfo = new HashMap<>();

    @Inject(method = "serialize(Lnet/minecraft/util/registry/DynamicRegistries;Lnet/minecraft/nbt/CompoundNBT;Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
    private void inject_serialize(DynamicRegistries registry, CompoundNBT nbt, CompoundNBT playerNBT, CallbackInfo ci) {
        CompoundNBT infoNbt = new CompoundNBT();
        dimensionWorldInfo.forEach((key, info) -> infoNbt.put(key.getLocation().toString(), info.serializeData()));
        nbt.put("speculative:dimension_info", infoNbt);
    }

    @Override
    public void addDimensionWorldInfo(RegistryKey<World> world, DimensionWorldInfo info) {
        if(!dimensionWorldInfo.containsKey(world)) {
            dimensionWorldInfo.put(world, info);
        }
    }

    @Override
    public DimensionWorldInfo getDimensionWorldInfo(RegistryKey<World> world, Supplier<DimensionWorldInfo> defaultValue) {
        if(dimensionWorldInfo.containsKey(world)) {
            return dimensionWorldInfo.get(world);
        }
        DimensionWorldInfo info = defaultValue.get();
        addDimensionWorldInfo(world, info);
        return info;
    }

    @Override
    public void deserializeDimensionWorldInfo(Dynamic<INBT> dynamic) {
        dynamic.get("speculative:dimension_info").result().map(Dynamic::getValue).filter(CompoundNBT.class::isInstance).map(CompoundNBT.class::cast).ifPresent(nbt -> {
            for(String key : nbt.keySet()) {
                RegistryKey<World> dimension = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(key));
                DimensionWorldInfo info = DimensionWorldInfo.deserialize(nbt.getCompound(key), (ServerWorldInfo) (Object) this, (ServerWorldInfo) (Object) this);
                if(info != null) {
                    dimensionWorldInfo.put(dimension, info);
                }
            }
        });
    }
}
