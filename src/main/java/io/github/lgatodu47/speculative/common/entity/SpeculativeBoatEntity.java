package io.github.lgatodu47.speculative.common.entity;

import com.mojang.datafixers.util.Either;
import io.github.lgatodu47.speculative.common.init.SpeculativeEntityTypes;
import io.github.lgatodu47.speculative.util.SpeculativeBoatType;
import io.github.lgatodu47.speculative.util.SpeculativeReflectionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SpeculativeBoatEntity extends Boat {
    private static final EntityDataAccessor<String> DATA_ID_TYPE = SynchedEntityData.defineId(SpeculativeBoatEntity.class, EntityDataSerializers.STRING);

    public SpeculativeBoatEntity(EntityType<? extends SpeculativeBoatEntity> type, Level level) {
        super(type, level);
    }

    public SpeculativeBoatEntity(Level level, double x, double y, double z) {
        this(SpeculativeEntityTypes.SPECULATIVE_BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE, "minecraft:oak");
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
        setLastYd(this.getDeltaMovement().y);
        if (!this.isPassenger()) {
            if (pOnGround) {
                if (this.fallDistance > 3.0F) {
                    if (getStatus() != Boat.Status.ON_LAND) {
                        this.resetFallDistance();
                        return;
                    }

                    this.causeFallDamage(this.fallDistance, 1.0F, DamageSource.FALL);
                    if (!this.level.isClientSide && !this.isRemoved()) {
                        this.kill();
                        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            for (int i = 0; i < 3; ++i) {
                                this.spawnAtLocation(boatType().<ItemLike>map(Type::getPlanks, type -> type.planks().get()));
                            }

                            for (int j = 0; j < 2; ++j) {
                                this.spawnAtLocation(Items.STICK);
                            }
                        }
                    }
                }

                this.resetFallDistance();
            } else if (!this.level.getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && pY < 0.0D) {
                this.fallDistance -= (float) pY;
            }
        }
    }

    @Override
    public Item getDropItem() {
        return boatType().map(type -> SpeculativeBoatEntity.super.getDropItem(), type -> type.boatItem().get());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        nbt.putString("Type", boatType().map(SpeculativeBoatType::mapVanillaType, SpeculativeBoatType::name).toString());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        if (nbt.contains("Type", 8)) {
            SpeculativeBoatType.byName(new ResourceLocation(nbt.getString("Type"))).ifLeft(this::setType).ifRight(this::setType);
        }
    }

    @Override
    public void setType(Boat.Type type) {
        this.entityData.set(DATA_ID_TYPE, "minecraft:" + type.getName());
    }

    public void setType(SpeculativeBoatType type) {
        this.entityData.set(DATA_ID_TYPE, type.name().toString());
    }

    @Deprecated
    @Override
    public Boat.Type getBoatType() {
        return boatType().left().orElse(Type.OAK);
    }

    public Either<Boat.Type, SpeculativeBoatType> boatType() {
        return SpeculativeBoatType.byName(new ResourceLocation(this.entityData.get(DATA_ID_TYPE)));
    }

    private void setLastYd(double value) {
        SpeculativeReflectionHelper.setFieldValue(Boat.class, this, "f_38281_", value);
    }

    private Status getStatus() {
        return SpeculativeReflectionHelper.getFieldValue(Boat.class, this, "f_38279_", Boat.Status.class).orElseThrow(() -> new RuntimeException("Error when trying to reflect field 'status' from SpeculativeBoatEntity. Please report this error to the mod author."));
    }
}
