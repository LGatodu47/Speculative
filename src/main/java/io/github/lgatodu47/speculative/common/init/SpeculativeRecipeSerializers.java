package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.recipe.CentrifugeRecipe;
import io.github.lgatodu47.speculative.common.recipe.NuclearWorkbenchShapedRecipe;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.client.RecipeBookRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SpeculativeRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Speculative.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, Speculative.MODID);

    public static final RecipeType<CentrifugeRecipe> CENTRIFUGE_TYPE = registerType("centrifuge");
    public static final RegistryObject<RecipeSerializer<?>> CENTRIFUGE_SERIALIZER = SERIALIZERS.register("centrifuge", CentrifugeRecipe.Serializer::new);
    public static final RecipeType<NuclearWorkbenchShapedRecipe> NUCLEAR_WORKBENCH_TYPE = registerType("nuclear_workbench");
    public static final RegistryObject<RecipeSerializer<?>> NUCLEAR_WORKBENCH_SHAPED_SERIALIZER = SERIALIZERS.register("nuclear_workbench_shaped", NuclearWorkbenchShapedRecipe.Serializer::new);

    private static <T extends Recipe<?>> RecipeType<T> registerType(String id) {
        RecipeType<T> type = new RecipeType<>() {
            @Override
            public String toString() {
                return Speculative.MODID + ':' + id;
            }
        };
        RECIPE_TYPES.register(id, () -> type);
        RecipeBookRegistry.addCategoriesFinder(type, recipe -> RecipeBookCategories.UNKNOWN);
        return type;
    }
}
