package io.github.lgatodu47.speculative.mixin;

import io.github.lgatodu47.speculative.common.entity.SpeculoTNTEntity;
import io.github.lgatodu47.speculative.common.init.SpeculativeEntityTypes;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetHandler.class)
public class ClientPlayNetHandlerMixin {
    @Shadow
    private ClientWorld world;

    @Inject(method = "handleSpawnObject", at = @At("TAIL"))
    public void inject_handleSpawnObject(SSpawnObjectPacket pkt, CallbackInfo ci) {
        double x = pkt.getX();
        double y = pkt.getY();
        double z = pkt.getZ();
        EntityType<?> type = pkt.getType();
        Entity entity = null;

        if (type == SpeculativeEntityTypes.SPECULO_TNT.get()) {
            entity = new SpeculoTNTEntity(this.world, x, y, z, null);
        }

        if (entity != null) {
            int id = pkt.getEntityID();
            entity.setPacketCoordinates(x, y, z);
            entity.moveForced(x, y, z);
            entity.rotationPitch = (float) (pkt.getPitch() * 360) / 256.0F;
            entity.rotationYaw = (float) (pkt.getYaw() * 360) / 256.0F;
            entity.setEntityId(id);
            entity.setUniqueId(pkt.getUniqueId());
            this.world.addEntity(id, entity);
        }
    }
}
