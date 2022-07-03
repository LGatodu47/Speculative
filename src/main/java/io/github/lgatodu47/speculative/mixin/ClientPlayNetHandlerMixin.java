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
    private ClientWorld level;

    @Inject(method = "handleAddEntity", at = @At("TAIL"))
    public void inject_handleAddEntity(SSpawnObjectPacket pkt, CallbackInfo ci) {
        double x = pkt.getX();
        double y = pkt.getY();
        double z = pkt.getZ();
        EntityType<?> type = pkt.getType();
        Entity entity = null;

        if (type == SpeculativeEntityTypes.SPECULO_TNT.get()) {
            entity = new SpeculoTNTEntity(this.level, x, y, z, null);
        }

        if (entity != null) {
            int id = pkt.getId();
            entity.setPacketCoordinates(x, y, z);
            entity.moveTo(x, y, z);
            entity.xRot = (float) (pkt.getxRot() * 360) / 256.0F;
            entity.yRot = (float) (pkt.getyRot() * 360) / 256.0F;
            entity.setId(id);
            entity.setUUID(pkt.getUUID());
            this.level.putNonPlayerEntity(id, entity);
        }
    }
}
