package io.github.lgatodu47.speculative.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.lgatodu47.speculative.common.init.SpeculativeRecipeSerializers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public class CentrifugeRecipe implements Recipe<RecipeWrapper> {
    private final ResourceLocation id;
    private final StackedIngredient input1, input2;
    private final ItemStack output;

    public CentrifugeRecipe(ResourceLocation id, StackedIngredient input1, StackedIngredient input2, @Nonnull ItemStack output) {
        this.id = id;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level worldIn) {
        return input1.test(inv.getItem(0)) && input2.test(inv.getItem(1));
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv) {
        return this.output.copy();
    }

    @Override
    public ItemStack getResultItem() {
        return this.output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SpeculativeRecipeSerializers.CENTRIFUGE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return SpeculativeRecipeSerializers.CENTRIFUGE_TYPE;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, input1.ingredient, input2.ingredient);
    }

    public StackedIngredient getInput1() {
        return input1;
    }

    public StackedIngredient getInput2() {
        return input2;
    }

    public static class Serializer extends AbstractRecipeSerializer<CentrifugeRecipe> {
        @Override
        public CentrifugeRecipe fromJson(ResourceLocation id, JsonObject json) {
            StackedIngredient input1 = StackedIngredient.deserialize(GsonHelper.getAsJsonObject(json, "input1"));
            StackedIngredient input2 = StackedIngredient.deserialize(GsonHelper.getAsJsonObject(json, "input2"));

            if (!json.has("output"))
                throw new JsonSyntaxException("Missing output, expected to find a string or object");
            ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);

            return new CentrifugeRecipe(id, input1, input2, output);
        }

        @Override
        public CentrifugeRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            StackedIngredient input1 = StackedIngredient.read(buffer);
            StackedIngredient input2 = StackedIngredient.read(buffer);
            ItemStack output = buffer.readItem();

            return new CentrifugeRecipe(id, input1, input2, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CentrifugeRecipe recipe) {
            recipe.getInput1().write(buffer);
            recipe.getInput2().write(buffer);
            buffer.writeItemStack(recipe.getResultItem(), false);
        }
    }

    public static final class StackedIngredient {
        public final Ingredient ingredient;
        public final int amount;

        private StackedIngredient(Ingredient ingredient, int amount) {
            this.ingredient = ingredient;
            this.amount = amount;
        }

        boolean test(ItemStack stack) {
            return ingredient.test(stack) && stack.getCount() >= amount;
        }

        void write(FriendlyByteBuf buffer) {
            buffer.writeInt(amount);
            ingredient.toNetwork(buffer);
        }

        static StackedIngredient deserialize(JsonElement json) {
            if(json == null || json.isJsonNull()) {
                throw new JsonSyntaxException("Item cannot be null");
            }

            int amount = 1;
            Ingredient ingredient;
            if(json.isJsonObject()) {
                JsonObject obj = json.getAsJsonObject();

                if(GsonHelper.isValidNode(obj, "amount")) {
                    amount = GsonHelper.getAsInt(obj, "amount");
                }

                if(GsonHelper.isValidNode(obj, "ingredient")) {
                    ingredient = Ingredient.fromJson(obj.get("ingredient"));
                }
                else {
                    ingredient = Ingredient.fromJson(obj);
                }
            }
            else {
                ingredient = Ingredient.fromJson(json);
            }

            return new StackedIngredient(ingredient, amount);
        }

        static StackedIngredient read(FriendlyByteBuf buffer) {
            int amount = buffer.readVarInt();
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            return new StackedIngredient(ingredient, amount);
        }
    }
}
