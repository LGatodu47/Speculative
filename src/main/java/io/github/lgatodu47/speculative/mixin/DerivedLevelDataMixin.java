package io.github.lgatodu47.speculative.mixin;

import com.mojang.serialization.Dynamic;
import io.github.lgatodu47.speculative.common.world.DimensionLevelData;
import io.github.lgatodu47.speculative.common.world.ExtendedServerLevelData;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Supplier;

@Mixin(DerivedLevelData.class)
public class DerivedLevelDataMixin implements ExtendedServerLevelData {
    @Shadow @Final private ServerLevelData wrapped;

    @Override
    public void addDimensionWorldInfo(ResourceKey<Level> world, DimensionLevelData info) {
        ExtendedServerLevelData.get(this.wrapped).addDimensionWorldInfo(world, info);
    }

    @Override
    public DimensionLevelData getDimensionWorldInfo(ResourceKey<Level> world, Supplier<DimensionLevelData> defaultValue) {
        return ExtendedServerLevelData.get(this.wrapped).getDimensionWorldInfo(world, defaultValue);
    }

    @Override
    public void deserializeDimensionWorldInfo(Dynamic<Tag> dynamic) {
    }
}
