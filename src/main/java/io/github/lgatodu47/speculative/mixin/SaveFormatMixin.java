package io.github.lgatodu47.speculative.mixin;

import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import io.github.lgatodu47.speculative.common.world.ExtendedServerWorldInfo;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.SaveFormat;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraft.world.storage.VersionData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SaveFormat.class)
public class SaveFormatMixin {
    @Redirect(method = "lambda$getReader$4", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/storage/ServerWorldInfo;decodeWorldInfo(Lcom/mojang/serialization/Dynamic;Lcom/mojang/datafixers/DataFixer;ILnet/minecraft/nbt/CompoundNBT;Lnet/minecraft/world/WorldSettings;Lnet/minecraft/world/storage/VersionData;Lnet/minecraft/world/gen/settings/DimensionGeneratorSettings;Lcom/mojang/serialization/Lifecycle;)Lnet/minecraft/world/storage/ServerWorldInfo;"))
    private static ServerWorldInfo redirect_decodeWorldInfo(Dynamic<INBT> dynamic, DataFixer dataFixer, int version, CompoundNBT playerNBT, WorldSettings worldSettings, VersionData versionData, DimensionGeneratorSettings generatorSettings, Lifecycle lifecycle) {
        ServerWorldInfo info = ServerWorldInfo.decodeWorldInfo(dynamic, dataFixer, version, playerNBT, worldSettings, versionData, generatorSettings, lifecycle);
        ExtendedServerWorldInfo.get(info).deserializeDimensionWorldInfo(dynamic);
        return info;
    }
}
