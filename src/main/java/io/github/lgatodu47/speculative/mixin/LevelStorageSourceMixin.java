package io.github.lgatodu47.speculative.mixin;

import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import io.github.lgatodu47.speculative.common.world.ExtendedServerLevelData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.LevelVersion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelStorageSource.class)
public class LevelStorageSourceMixin {
    @Redirect(method = "lambda$getLevelData$1", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/PrimaryLevelData;parse(Lcom/mojang/serialization/Dynamic;Lcom/mojang/datafixers/DataFixer;ILnet/minecraft/nbt/CompoundTag;Lnet/minecraft/world/level/LevelSettings;Lnet/minecraft/world/level/storage/LevelVersion;Lnet/minecraft/world/level/levelgen/WorldGenSettings;Lcom/mojang/serialization/Lifecycle;)Lnet/minecraft/world/level/storage/PrimaryLevelData;"))
    private static PrimaryLevelData redirect_parse(Dynamic<Tag> dynamic, DataFixer dataFixer, int version, CompoundTag playerNBT, LevelSettings worldSettings, LevelVersion versionData, WorldGenSettings generatorSettings, Lifecycle lifecycle) {
        PrimaryLevelData info = PrimaryLevelData.parse(dynamic, dataFixer, version, playerNBT, worldSettings, versionData, generatorSettings, lifecycle);
        ExtendedServerLevelData.get(info).deserializeDimensionWorldInfo(dynamic);
        return info;
    }
}
