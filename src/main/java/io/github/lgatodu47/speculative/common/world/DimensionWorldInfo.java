package io.github.lgatodu47.speculative.common.world;

import com.mojang.serialization.Dynamic;
import net.minecraft.command.TimerCallbackManager;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.IServerWorldInfo;

import java.util.UUID;
import java.util.function.Supplier;

public class DimensionWorldInfo implements IServerWorldInfo, ExtendedServerWorldInfo {
    private long gameTime;
    private long dayTime;
    private int clearWeatherTime;
    private boolean raining;
    private int rainTime;
    private boolean thundering;
    private int thunderTime;

    private final IServerConfiguration configuration;
    private final IServerWorldInfo delegate;

    public DimensionWorldInfo(IServerConfiguration configuration, IServerWorldInfo delegate) {
        this.configuration = configuration;
        this.delegate = delegate;
    }

    @Override
    public int getSpawnX() {
        return this.delegate.getSpawnX();
    }

    @Override
    public int getSpawnY() {
        return this.delegate.getSpawnY();
    }

    @Override
    public int getSpawnZ() {
        return this.delegate.getSpawnZ();
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
    public String getWorldName() {
        return this.configuration.getWorldName();
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
    public void setSpawnX(int x) {
    }

    @Override
    public void setSpawnY(int y) {
    }

    @Override
    public void setSpawnZ(int z) {
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
    public boolean areCommandsAllowed() {
        return this.configuration.areCommandsAllowed();
    }

    @Override
    public boolean isInitialized() {
        return this.delegate.isInitialized();
    }

    @Override
    public void setInitialized(boolean initializedIn) {
    }

    @Override
    public GameRules getGameRulesInstance() {
        return this.configuration.getGameRulesInstance();
    }

    @Override
    public WorldBorder.Serializer getWorldBorderSerializer() {
        return this.delegate.getWorldBorderSerializer();
    }

    @Override
    public void setWorldBorderSerializer(WorldBorder.Serializer serializer) {
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
    public TimerCallbackManager<MinecraftServer> getScheduledEvents() {
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

    @Override
    public void setWanderingTraderID(UUID id) {
    }

    @Override
    public void addToCrashReport(CrashReportCategory category) {
        category.addDetail("Derived", true);
        this.delegate.addToCrashReport(category);
    }

    public CompoundNBT serializeData() {
        CompoundNBT nbt = new CompoundNBT();
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

    public static DimensionWorldInfo deserialize(CompoundNBT nbt, IServerConfiguration configuration, IServerWorldInfo delegate) {
        if(nbt == null) return null;

        DimensionWorldInfo info = new DimensionWorldInfo(configuration, delegate);
        info.gameTime = nbt.getLong("Time");
        info.dayTime = nbt.getLong("DayTime");
        info.clearWeatherTime = nbt.getInt("clearWeatherTime");
        info.rainTime = nbt.getInt("rainTime");
        info.raining = nbt.getBoolean("raining");
        info.thunderTime = nbt.getInt("thunderTime");
        info.thundering = nbt.getBoolean("thundering");
        return info;
    }
}
