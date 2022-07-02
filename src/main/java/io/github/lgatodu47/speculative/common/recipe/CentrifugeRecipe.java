package io.github.lgatodu47.speculative.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.lgatodu47.speculative.common.init.SpeculativeRecipeSerializers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public class CentrifugeRecipe implements IRecipe<RecipeWrapper> {
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
    public boolean matches(RecipeWrapper inv, World worldIn) {
        return input1.test(inv.getStackInSlot(0)) && input2.test(inv.getStackInSlot(1));
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return this.output.copy();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.output;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SpeculativeRecipeSerializers.CENTRIFUGE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return SpeculativeRecipeSerializers.CENTRIFUGE_TYPE;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.from(Ingredient.EMPTY, input1.ingredient, input2.ingredient);
    }

    public StackedIngredient getInput1() {
        return input1;
    }

    public StackedIngredient getInput2() {
        return input2;
    }

    public static class Serializer extends AbstractRecipeSerializer<CentrifugeRecipe> {
        @Override
        public CentrifugeRecipe read(ResourceLocation id, JsonObject json) {
            StackedIngredient input1 = StackedIngredient.deserialize(JSONUtils.getJsonObject(json, "input1"));
            StackedIngredient input2 = StackedIngredient.deserialize(JSONUtils.getJsonObject(json, "input2"));

            if (!json.has("output"))
                throw new JsonSyntaxException("Missing output, expected to find a string or object");
            ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);

            return new CentrifugeRecipe(id, input1, input2, output);
        }

        @Override
        public CentrifugeRecipe read(ResourceLocation id, PacketBuffer buffer) {
            StackedIngredient input1 = StackedIngredient.read(buffer);
            StackedIngredient input2 = StackedIngredient.read(buffer);
            ItemStack output = buffer.readItemStack();

            return new CentrifugeRecipe(id, input1, input2, output);
        }

        @Override
        public void write(PacketBuffer buffer, CentrifugeRecipe recipe) {
            recipe.getInput1().write(buffer);
            recipe.getInput2().write(buffer);
            buffer.writeItemStack(recipe.getRecipeOutput(), false);
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

        void write(PacketBuffer buffer) {
            buffer.writeInt(amount);
            ingredient.write(buffer);
        }

        static StackedIngredient deserialize(JsonElement json) {
            if(json == null || json.isJsonNull()) {
                throw new JsonSyntaxException("Item cannot be null");
            }

            int amount = 1;
            Ingredient ingredient;
            if(json.isJsonObject()) {
                JsonObject obj = json.getAsJsonObject();

                if(JSONUtils.hasField(obj, "amount")) {
                    amount = JSONUtils.getInt(obj, "amount");
                }

                if(JSONUtils.hasField(obj, "ingredient")) {
                    ingredient = Ingredient.deserialize(obj.get("ingredient"));
                }
                else {
                    ingredient = Ingredient.deserialize(obj);
                }
            }
            else {
                ingredient = Ingredient.deserialize(json);
            }

            return new StackedIngredient(ingredient, amount);
        }

        static StackedIngredient read(PacketBuffer buffer) {
            int amount = buffer.readVarInt();
            Ingredient ingredient = Ingredient.read(buffer);
            return new StackedIngredient(ingredient, amount);
        }
    }
}