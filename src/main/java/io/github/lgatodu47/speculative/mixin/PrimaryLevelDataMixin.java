package io.github.lgatodu47.speculative.mixin;

import com.mojang.serialization.Dynamic;
import io.github.lgatodu47.speculative.common.world.DimensionLevelData;
import io.github.lgatodu47.speculative.common.world.ExtendedServerLevelData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(PrimaryLevelData.class)
public class PrimaryLevelDataMixin implements ExtendedServerLevelData {
    @Unique
    private final Map<ResourceKey<Level>, DimensionLevelData> dimensionWorldInfo = new HashMap<>();

    @Inject(method = "setTagData", at = @At("TAIL"))
    private void inject_setTagData(RegistryAccess registry, CompoundTag nbt, CompoundTag playerNBT, CallbackInfo ci) {
        CompoundTag infoNbt = new CompoundTag();
        dimensionWorldInfo.forEach((key, info) -> infoNbt.put(key.location().toString(), info.serializeData()));
        nbt.put("speculative:dimension_info", infoNbt);
    }

    @Override
    public void addDimensionWorldInfo(ResourceKey<Level> world, DimensionLevelData info) {
        if(!dimensionWorldInfo.containsKey(world)) {
            dimensionWorldInfo.put(world, info);
        }
    }

    @Override
    public DimensionLevelData getDimensionWorldInfo(ResourceKey<Level> world, Supplier<DimensionLevelData> defaultValue) {
        if(dimensionWorldInfo.containsKey(world)) {
            return dimensionWorldInfo.get(world);
        }
        DimensionLevelData info = defaultValue.get();
        addDimensionWorldInfo(world, info);
        return info;
    }

    @Override
    public void deserializeDimensionWorldInfo(Dynamic<Tag> dynamic) {
        dynamic.get("speculative:dimension_info").result().map(Dynamic::getValue).filter(CompoundTag.class::isInstance).map(CompoundTag.class::cast).ifPresent(nbt -> {
            for(String key : nbt.getAllKeys()) {
                ResourceKey<Level> dimension = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(key));
                DimensionLevelData info = DimensionLevelData.deserialize(nbt.getCompound(key), (PrimaryLevelData) (Object) this, (PrimaryLevelData) (Object) this);
                if(info != null) {
                    dimensionWorldInfo.put(dimension, info);
                }
            }
        });
    }
}
