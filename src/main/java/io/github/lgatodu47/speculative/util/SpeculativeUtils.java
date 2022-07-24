package io.github.lgatodu47.speculative.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SpeculativeUtils {
    public static Set<Recipe<?>> findRecipesByType(RecipeType<?> type, Level level) {
        return level != null ? level.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == type).collect(Collectors.toSet()) : Collections.emptySet();
    }

    @OnlyIn(Dist.CLIENT)
    public static Set<Recipe<?>> findRecipesByType(RecipeType<?> type) {
        ClientLevel level = Minecraft.getInstance().level;
        return level != null ? level.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == type).collect(Collectors.toSet()) : Collections.emptySet();
    }

    public static <E> E getRandomElement(Random random, Collection<E> collection) {
        int num = (int) (random.nextDouble() * collection.size());
        for (E element : collection)
            if (--num < 0) return element;
        throw new AssertionError();
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> classHack(Class<?> clazz) {
        return (Class<T>) clazz;
    }

    public static <T, I> Collection<I> filterEntries(DeferredRegister<T> register, Class<I> filter) {
        return register.getEntries()
                .stream()
                .filter(RegistryObject::isPresent)
                .map(RegistryObject::get)
                .filter(filter::isInstance)
                .map(filter::cast)
                .toList();
    }
}
