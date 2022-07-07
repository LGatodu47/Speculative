package io.github.lgatodu47.speculative.mixin;

import io.github.lgatodu47.speculative.common.world.DimensionLevelData;
import io.github.lgatodu47.speculative.common.world.ExtendedServerLevelData;
import io.github.lgatodu47.speculative.util.DimensionUtil;
import net.minecraft.core.Holder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Shadow @Final protected WorldData worldData;

    @ModifyArg(method = "createLevels", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;<init>(Lnet/minecraft/server/MinecraftServer;Ljava/util/concurrent/Executor;Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lnet/minecraft/world/level/storage/ServerLevelData;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/Holder;Lnet/minecraft/server/level/progress/ChunkProgressListener;Lnet/minecraft/world/level/chunk/ChunkGenerator;ZJLjava/util/List;Z)V", ordinal = 1), index = 3)
    private ServerLevelData modifyArg_ServerLevelInit(MinecraftServer server, Executor executor, LevelStorageSource.LevelStorageAccess save, ServerLevelData info, ResourceKey<Level> dimension, Holder<DimensionType> dimensionType, ChunkProgressListener statusListener, ChunkGenerator chunkGenerator, boolean isDebug, long seed, List<CustomSpawner> specialSpawners, boolean shouldBeTicking) {
        if(DimensionUtil.hasDimensionWorldInfo(dimension)) {
            return ExtendedServerLevelData.get(this.worldData.overworldData()).getDimensionWorldInfo(dimension, () -> new DimensionLevelData(this.worldData, this.worldData.overworldData()));
        }
        return info;
    }
}
