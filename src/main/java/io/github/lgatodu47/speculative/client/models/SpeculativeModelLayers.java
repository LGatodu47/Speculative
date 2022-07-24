package io.github.lgatodu47.speculative.client.models;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.util.Lazy;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public final class SpeculativeModelLayers {
    private static final Set<SpeculativeLayerInfo> INFOS = new HashSet<>();

    private static final Lazy<LayerDefinition> VILLAGER_LAYER = Lazy.of(() -> LayerDefinition.create(VillagerModel.createBodyModel(), 64, 64));
    public static final SpeculativeLayerInfo ANCIENT_LORD = register("ancient_lord", VILLAGER_LAYER);

    private static SpeculativeLayerInfo register(String name, Supplier<LayerDefinition> layerDefSup) {
        return register(name, "main", layerDefSup);
    }

    private static SpeculativeLayerInfo register(String name, String model, Supplier<LayerDefinition> layerDefSup) {
        return register(new ModelLayerLocation(new ResourceLocation(Speculative.MODID, name), model), layerDefSup);
    }

    private static SpeculativeLayerInfo register(ModelLayerLocation location, Supplier<LayerDefinition> layerDefSup) {
        SpeculativeLayerInfo info = new SpeculativeLayerInfo(location, layerDefSup);
        INFOS.add(info);
        return info;
    }

    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        INFOS.forEach(info -> info.register(event));
    }
}
