package io.github.lgatodu47.speculative.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public final class SpeculativeUtils {
    public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> type, World world) {
        return world != null ? world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == type).collect(Collectors.toSet()) : Collections.emptySet();
    }

    @OnlyIn(Dist.CLIENT)
    public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> type) {
        ClientWorld world = Minecraft.getInstance().level;
        return world != null ? world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == type).collect(Collectors.toSet()) : Collections.emptySet();
    }

    public static <E> E getRandomElement(Random random, Collection<E> collection) {
        int num = (int) (random.nextDouble() * collection.size());
        for (E element : collection)
            if (--num < 0) return element;
        throw new AssertionError();
    }
}
