package io.github.lgatodu47.speculative.common.world;

import com.mojang.serialization.Dynamic;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.timers.TimerQueue;
import net.minecraft.CrashReportCategory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.ServerLevelData;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class DimensionLevelData implements ServerLevelData, ExtendedServerLevelData {
    private long gameTime;
    private long dayTime;
    private int clearWeatherTime;
    private boolean raining;
    private int rainTime;
    private boolean thundering;
    private int thunderTime;

    private final WorldData configuration;
    private final ServerLevelData delegate;

    public DimensionLevelData(WorldData configuration, ServerLevelData delegate) {
        this.configuration = configuration;
        this.delegate = delegate;
    }

    @Override
    public int getXSpawn() {
        return this.delegate.getXSpawn();
    }

    @Override
    public int getYSpawn() {
        return this.delegate.getYSpawn();
    }

    @Override
    public int getZSpawn() {
        return this.delegate.getZSpawn();
    }

    @Override
    public float getSpawnAngle() {
        return this.delegate.getSpawnAngle();
    }

    @Override
    public long getGameTime() {
        return this.gameTime;
    }

    @Override
    public long getDayTime() {
        return this.dayTime;
    }

    @Override
    public String getLevelName() {
        return this.configuration.getLevelName();
    }

    @Override
    public int getClearWeatherTime() {
        return this.clearWeatherTime;
    }

    @Override
    public void setClearWeatherTime(int time) {
        this.clearWeatherTime = time;
    }

    @Override
    public boolean isThundering() {
        return this.thundering;
    }

    @Override
    public int getThunderTime() {
        return this.thunderTime;
    }

    @Override
    public boolean isRaining() {
        return this.raining;
    }

    @Override
    public int getRainTime() {
        return this.rainTime;
    }

    @Override
    public GameType getGameType() {
        return this.configuration.getGameType();
    }

    @Override
    public void setXSpawn(int x) {
    }

    @Override
    public void setYSpawn(int y) {
    }

    @Override
    public void setZSpawn(int z) {
    }

    @Override
    public void setSpawnAngle(float angle) {
    }

    @Override
    public void setGameTime(long time) {
        this.gameTime = time;
    }

    @Override
    public void setDayTime(long time) {
        this.dayTime = time;
    }

    @Override
    public void setSpawn(BlockPos spawnPoint, float angle) {
    }

    @Override
    public void setThundering(boolean thunderingIn) {
        this.thundering = thunderingIn;
    }

    @Override
    public void setThunderTime(int time) {
        this.thunderTime = time;
    }

    @Override
    public void setRaining(boolean isRaining) {
        this.raining = isRaining;
    }

    @Override
    public void setRainTime(int time) {
        this.rainTime = time;
    }

    @Override
    public void setGameType(GameType type) {
    }

    @Override
    public boolean isHardcore() {
        return this.configuration.isHardcore();
    }

    @Override
    public boolean getAllowCommands() {
        return this.configuration.getAllowCommands();
    }

    @Override
    public boolean isInitialized() {
        return this.delegate.isInitialized();
    }

    @Override
    public void setInitialized(boolean initializedIn) {
    }

    @Override
    public GameRules getGameRules() {
        return this.configuration.getGameRules();
    }

    @Override
    public WorldBorder.Settings getWorldBorder() {
        return this.delegate.getWorldBorder();
    }

    @Override
    public void setWorldBorder(WorldBorder.Settings serializer) {
    }

    @Override
    public Difficulty getDifficulty() {
        return this.configuration.getDifficulty();
    }

    @Override
    public boolean isDifficultyLocked() {
        return this.configuration.isDifficultyLocked();
    }

    @Override
    public TimerQueue<MinecraftServer> getScheduledEvents() {
        return this.delegate.getScheduledEvents();
    }

    @Override
    public int getWanderingTraderSpawnDelay() {
        return 0;
    }

    @Override
    public void setWanderingTraderSpawnDelay(int delay) {
    }

    @Override
    public int getWanderingTraderSpawnChance() {
        return 0;
    }

    @Override
    public void setWanderingTraderSpawnChance(int chance) {
    }

    @Nullable
    @Override
    public UUID getWanderingTraderId() {
        return null;
    }

    @Override
    public void setWanderingTraderId(UUID id) {
    }

    @Override
    public void fillCrashReportCategory(CrashReportCategory category, LevelHeightAccessor level) {
        category.setDetail("Dimension", true);
        this.delegate.fillCrashReportCategory(category, level);
    }

    public CompoundTag serializeData() {
        CompoundTag nbt = new CompoundTag();
        nbt.putLong("Time", this.gameTime);
        nbt.putLong("DayTime", this.dayTime);
        nbt.putInt("clearWeatherTime", this.clearWeatherTime);
        nbt.putInt("rainTime", this.rainTime);
        nbt.putBoolean("raining", this.raining);
        nbt.putInt("thunderTime", this.thunderTime);
        nbt.putBoolean("thundering", this.thundering);
        return nbt;
    }

    @Override
    public void addDimensionWorldInfo(ResourceKey<Level> world, DimensionLevelData info) {
        ExtendedServerLevelData.get(this.delegate).addDimensionWorldInfo(world, info);
    }

    @Override
    public DimensionLevelData getDimensionWorldInfo(ResourceKey<Level> world, Supplier<DimensionLevelData> defaultValue) {
        return ExtendedServerLevelData.get(this.delegate).getDimensionWorldInfo(world, defaultValue);
    }

    @Override
    public void deserializeDimensionWorldInfo(Dynamic<Tag> dynamic) {
    }

    public static DimensionLevelData deserialize(CompoundTag nbt, WorldData configuration, ServerLevelData delegate) {
        if(nbt == null) return null;

        DimensionLevelData data = new DimensionLevelData(configuration, delegate);
        data.gameTime = nbt.getLong("Time");
        data.dayTime = nbt.getLong("DayTime");
        data.clearWeatherTime = nbt.getInt("clearWeatherTime");
        data.rainTime = nbt.getInt("rainTime");
        data.raining = nbt.getBoolean("raining");
        data.thunderTime = nbt.getInt("thunderTime");
        data.thundering = nbt.getBoolean("thundering");
        return data;
    }
}
