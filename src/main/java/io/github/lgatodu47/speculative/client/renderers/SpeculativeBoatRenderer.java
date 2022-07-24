package io.github.lgatodu47.speculative.client.renderers;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import io.github.lgatodu47.speculative.common.entity.SpeculativeBoatEntity;
import io.github.lgatodu47.speculative.util.SpeculativeBoatType;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

import java.util.Map;
import java.util.function.Function;

public class SpeculativeBoatRenderer extends BoatRenderer {
    private final Map<ResourceLocation, Pair<ResourceLocation, BoatModel>> boatResources;

    public SpeculativeBoatRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.boatResources = SpeculativeBoatType.allNames().stream().collect(ImmutableMap.toImmutableMap(Function.identity(), id ->
                Pair.of(new ResourceLocation(id.getNamespace(), "textures/entity/boat/" + id.getPath() + ".png"), new BoatModel(ctx.bakeLayer(new ModelLayerLocation(new ResourceLocation(id.getNamespace(), "boat/" + id.getPath()), "main"))))));
    }

    @Override
    public Pair<ResourceLocation, BoatModel> getModelWithLocation(Boat boat) {
        if(boat instanceof SpeculativeBoatEntity speculativeBoat) {
            return boatResources.get(speculativeBoat.boatType().map(SpeculativeBoatType::mapVanillaType, SpeculativeBoatType::name));
        }
        return super.getModelWithLocation(boat);
    }
}
