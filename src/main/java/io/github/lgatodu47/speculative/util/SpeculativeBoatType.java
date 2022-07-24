package io.github.lgatodu47.speculative.util;

import com.mojang.datafixers.util.Either;
import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.init.SpeculativeItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public record SpeculativeBoatType(ResourceLocation name, Supplier<Block> planks, Supplier<Item> boatItem) {
    private static final Map<ResourceLocation, SpeculativeBoatType> BOAT_TYPES = new HashMap<>();

    public SpeculativeBoatType(ResourceLocation name, Supplier<Block> planks, Supplier<Item> boatItem) {
        this.name = name;
        this.planks = planks;
        this.boatItem = boatItem;
        BOAT_TYPES.putIfAbsent(name, this); // Prevents overrides
    }

    @Override
    public String toString() {
        return name().toString();
    }

    public static Either<Boat.Type, SpeculativeBoatType> byName(ResourceLocation name) {
        if(!BOAT_TYPES.containsKey(name)) {
            return Either.left(Boat.Type.byName(name.getPath()));
        }
        return Either.right(BOAT_TYPES.get(name));
    }

    public static Set<ResourceLocation> names() {
        return BOAT_TYPES.keySet();
    }

    public static Set<ResourceLocation> allNames() {
        Set<ResourceLocation> ids = new HashSet<>(names());
        ids.addAll(Arrays.stream(Boat.Type.values()).map(SpeculativeBoatType::mapVanillaType).collect(Collectors.toSet()));
        return ids;
    }

    public static ResourceLocation mapVanillaType(Boat.Type type) {
        return new ResourceLocation("minecraft", type.getName());
    }

    public static final SpeculativeBoatType SPECULO_WOOD = new SpeculativeBoatType(
            new ResourceLocation(Speculative.MODID, "speculo_wood"),
            SpeculativeBlocks.SPECULO_PLANKS,
            SpeculativeItems.SPECULO_WOOD_BOAT
    );

    public static final SpeculativeBoatType TOURMALINE_WOOD = new SpeculativeBoatType(
            new ResourceLocation(Speculative.MODID, "tourmaline_wood"),
            SpeculativeBlocks.TOURMALINE_PLANKS,
            SpeculativeItems.TOURMALINE_WOOD_BOAT
    );
}
