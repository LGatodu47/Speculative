package io.github.lgatodu47.speculative.mixin;

import io.github.lgatodu47.speculative.common.world.DimensionWorldInfo;
import io.github.lgatodu47.speculative.common.world.ExtendedServerWorldInfo;
import io.github.lgatodu47.speculative.util.DimensionUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.listener.IChunkStatusListener;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.spawner.ISpecialSpawner;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraft.world.storage.SaveFormat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Shadow @Final protected IServerConfiguration serverConfig;

    @ModifyArg(method = "func_240787_a_", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/server/ServerWorld;<init>(Lnet/minecraft/server/MinecraftServer;Ljava/util/concurrent/Executor;Lnet/minecraft/world/storage/SaveFormat$LevelSave;Lnet/minecraft/world/storage/IServerWorldInfo;Lnet/minecraft/util/RegistryKey;Lnet/minecraft/world/DimensionType;Lnet/minecraft/world/chunk/listener/IChunkStatusListener;Lnet/minecraft/world/gen/ChunkGenerator;ZJLjava/util/List;Z)V", ordinal = 1), index = 3)
    private IServerWorldInfo modifyArg_ServerWorldInit(MinecraftServer server, Executor executor, SaveFormat.LevelSave save, IServerWorldInfo info, RegistryKey<World> dimension, DimensionType dimensionType, IChunkStatusListener statusListener, ChunkGenerator chunkGenerator, boolean isDebug, long seed, List<ISpecialSpawner> specialSpawners, boolean shouldBeTicking) {
        if(DimensionUtil.hasDimensionWorldInfo(dimension)) {
            return ExtendedServerWorldInfo.get(this.serverConfig.getServerWorldInfo()).getDimensionWorldInfo(dimension, () -> new DimensionWorldInfo(this.serverConfig, this.serverConfig.getServerWorldInfo()));
        }

        return info;
    }
}
