package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.recipe.CentrifugeRecipe;
import io.github.lgatodu47.speculative.common.recipe.NuclearWorkbenchShapedRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SpeculativeRecipeSerializers {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Speculative.MODID);

    public static final IRecipeType<CentrifugeRecipe> CENTRIFUGE_TYPE = registerType("centrifuge");
    public static final RegistryObject<IRecipeSerializer<?>> CENTRIFUGE_SERIALIZER = RECIPE_SERIALIZER.register("centrifuge", CentrifugeRecipe.Serializer::new);
    public static final IRecipeType<NuclearWorkbenchShapedRecipe> NUCLEAR_WORKBENCH_TYPE = registerType("nuclear_workbench");
    public static final RegistryObject<IRecipeSerializer<?>> NUCLEAR_WORKBENCH_SHAPED_SERIALIZER = RECIPE_SERIALIZER.register("nuclear_workbench_shaped", NuclearWorkbenchShapedRecipe.Serializer::new);

    private static <T extends IRecipe<?>> IRecipeType<T> registerType(String id) {
        return IRecipeType.register(new ResourceLocation(Speculative.MODID, id).toString());
    }
}
