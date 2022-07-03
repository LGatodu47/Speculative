package io.github.lgatodu47.speculative.common.world.dimension.speculo_world;

import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.function.Function;

public class SpeculoWorldTeleporter implements ITeleporter {
    public static final SpeculoWorldTeleporter INSTANCE = new SpeculoWorldTeleporter();

    @Nullable
    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo) {
        int x = MathHelper.floor(entity.getX());
        int z = MathHelper.floor(entity.getZ());
        int y = destWorld.getChunk(x >> 4, z >> 4).getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x & 15, z & 15) + 1;
        return new PortalInfo(new Vector3d(x + 0.5, y, z + 0.5), Vector3d.ZERO, entity.yRot, entity.xRot);
    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        return repositionEntity.apply(false);
    }
}
