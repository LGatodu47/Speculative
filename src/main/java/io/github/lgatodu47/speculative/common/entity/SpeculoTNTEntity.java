package io.github.lgatodu47.speculative.common.entity;

import io.github.lgatodu47.speculative.common.init.SpeculativeEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SpeculoTNTEntity extends TNTEntity {
    @Nullable
    protected LivingEntity igniter;

    public SpeculoTNTEntity(EntityType<? extends SpeculoTNTEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public SpeculoTNTEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
        this(SpeculativeEntityTypes.SPECULO_TNT.get(), worldIn);
        this.setPosition(x, y, z);
        double d0 = worldIn.rand.nextDouble() * (double)((float)Math.PI * 2F);
        this.setMotion(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        setFuse(200);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.igniter = igniter;
    }

    @Override
    protected void explode() {
        this.world.createExplosion(this, this.getPosX(), this.getPosYHeight(0.0625D), this.getPosZ(), 12.0F, Explosion.Mode.BREAK);
    }

    @Override
    @Nullable
    public LivingEntity getIgniter() {
        return igniter;
    }
}
