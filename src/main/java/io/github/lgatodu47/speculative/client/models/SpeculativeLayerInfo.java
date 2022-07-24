package io.github.lgatodu47.speculative.client.models;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.util.Lazy;

import java.util.Optional;
import java.util.function.Supplier;

public class SpeculativeLayerInfo {
    private final ModelLayerLocation location;
    private final Lazy<LayerDefinition> layerDefinition;

    public SpeculativeLayerInfo(ModelLayerLocation location, Supplier<LayerDefinition> layerDefinitionSup) {
        this.location = location;
        this.layerDefinition = layerDefinitionSup instanceof Lazy<LayerDefinition> lazy ? lazy : Lazy.of(layerDefinitionSup);
    }

    public final void register(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(location, layerDefinition);
    }

    public final ModelPart bake(EntityRendererProvider.Context ctx) {
        return ctx.bakeLayer(location);
    }

    public ModelLayerLocation location() {
        return location;
    }

    public Optional<LayerDefinition> definition() {
        return Optional.ofNullable(layerDefinition.get());
    }
}
