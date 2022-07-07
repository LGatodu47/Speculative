package io.github.lgatodu47.speculative.common.entity;

import io.github.lgatodu47.speculative.common.init.SpeculativeEntityTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class SpeculoTNTEntity extends PrimedTnt {
    @Nullable
    protected LivingEntity igniter;

    public SpeculoTNTEntity(EntityType<? extends SpeculoTNTEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public SpeculoTNTEntity(Level worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
        this(SpeculativeEntityTypes.SPECULO_TNT.get(), worldIn);
        this.setPos(x, y, z);
        double d0 = worldIn.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, 0.2F, -Math.cos(d0) * 0.02D);
        setFuse(200);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.igniter = igniter;
    }

    @Override
    protected void explode() {
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 12.0F, Explosion.BlockInteraction.BREAK);
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        return igniter;
    }
}
