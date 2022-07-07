package io.github.lgatodu47.speculative.common.recipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.lgatodu47.speculative.common.init.SpeculativeRecipeSerializers;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IShapedRecipe;

import java.util.Map;
import java.util.Set;

public class NuclearWorkbenchShapedRecipe implements IShapedRecipe<CraftingContainer> {
    private final ResourceLocation id;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final int recipeWidth, recipeHeight;

    public NuclearWorkbenchShapedRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack result, int recipeWidth, int recipeHeight) {
        this.id = id;
        this.ingredients = ingredients;
        this.result = result;
        this.recipeWidth = recipeWidth;
        this.recipeHeight = recipeHeight;
    }

    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) {
        for (int x = 0; x <= inv.getWidth() - this.recipeWidth; ++x) {
            for (int y = 0; y <= inv.getHeight() - this.recipeHeight; ++y) {
                if (this.checkMatch(inv, x, y, true)) {
                    return true;
                }

                if (this.checkMatch(inv, x, y, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkMatch(CraftingContainer inv, int width, int height, boolean reverse) {
        for (int x = 0; x < inv.getWidth(); ++x) {
            for (int y = 0; y < inv.getHeight(); ++y) {
                int offsetX = x - width;
                int offsetY = y - height;
                Ingredient input = Ingredient.EMPTY;

                if (offsetX >= 0 && offsetY >= 0 && offsetX < this.recipeWidth && offsetY < this.recipeHeight) {
                    if (reverse) {
                        input = this.ingredients.get(this.recipeWidth - offsetX - 1 + offsetY * this.recipeWidth);
                    } else {
                        input = this.ingredients.get(offsetX + offsetY * this.recipeWidth);
                    }
                }

                if (!input.test(inv.getItem(x + y * inv.getWidth()))) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return SpeculativeRecipeSerializers.NUCLEAR_WORKBENCH_TYPE;
    }

    @Override
    public int getRecipeWidth() {
        return this.recipeWidth;
    }

    @Override
    public int getRecipeHeight() {
        return this.recipeHeight;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public static class Serializer extends AbstractRecipeSerializer<NuclearWorkbenchShapedRecipe> {
        public NuclearWorkbenchShapedRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Map<String, Ingredient> key = deserializeKey(GsonHelper.getAsJsonObject(json, "key"));
            String[] pattern = shrink(patternFromJson(GsonHelper.getAsJsonArray(json, "pattern"), 3, 3));
            int recipeWidth = pattern[0].length();
            int recipeHeight = pattern.length;
            NonNullList<Ingredient> ingredients = deserializeIngredients(pattern, key, recipeWidth, recipeHeight);
            ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);
            return new NuclearWorkbenchShapedRecipe(recipeId, ingredients, result, recipeWidth, recipeHeight);
        }

        public NuclearWorkbenchShapedRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            int recipeWidth = buffer.readVarInt();
            int recipeHeight = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(recipeWidth * recipeHeight, Ingredient.EMPTY);

            for (int k = 0; k < ingredients.size(); ++k) {
                ingredients.set(k, Ingredient.fromNetwork(buffer));
            }

            ItemStack result = buffer.readItem();
            return new NuclearWorkbenchShapedRecipe(id, ingredients, result, recipeWidth, recipeHeight);
        }

        public void toNetwork(FriendlyByteBuf buffer, NuclearWorkbenchShapedRecipe recipe) {
            buffer.writeVarInt(recipe.recipeWidth);
            buffer.writeVarInt(recipe.recipeHeight);

            for (Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.result);
        }

        private static NonNullList<Ingredient> deserializeIngredients(String[] pattern, Map<String, Ingredient> keys, int patternWidth, int patternHeight) {
            NonNullList<Ingredient> ingredients = NonNullList.withSize(patternWidth * patternHeight, Ingredient.EMPTY);
            Set<String> rows = Sets.newHashSet(keys.keySet());
            rows.remove(" ");

            for (int i = 0; i < pattern.length; ++i) {
                for (int j = 0; j < pattern[i].length(); ++j) {
                    String s = pattern[i].substring(j, j + 1);
                    Ingredient ingredient = keys.get(s);
                    if (ingredient == null) {
                        throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                    }

                    rows.remove(s);
                    ingredients.set(j + patternWidth * i, ingredient);
                }
            }

            if (!rows.isEmpty()) {
                throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + rows);
            } else {
                return ingredients;
            }
        }

        public static String[] shrink(String... toShrink) {
            int firstNonSpace = Integer.MAX_VALUE;
            int j = 0;
            int k = 0;
            int l = 0;

            for (int index = 0; index < toShrink.length; ++index) {
                String str = toShrink[index];
                firstNonSpace = Math.min(firstNonSpace, firstNonSpace(str));
                int lastNonSpace = lastNonSpace(str);
                j = Math.max(j, lastNonSpace);
                if (lastNonSpace < 0) {
                    if (k == index) {
                        ++k;
                    }

                    ++l;
                } else {
                    l = 0;
                }
            }

            if (toShrink.length == l) {
                return new String[0];
            } else {
                String[] astring = new String[toShrink.length - l - k];

                for (int k1 = 0; k1 < astring.length; ++k1) {
                    astring[k1] = toShrink[k1 + k].substring(firstNonSpace, j + 1);
                }

                return astring;
            }
        }

        private static int firstNonSpace(String str) {
            int i = 0;
            while(i < str.length() && str.charAt(i) == ' ') {
                i++;
            }

            return i;
        }

        private static int lastNonSpace(String str) {
            int i = str.length() - 1;
            while(i >= 0 && str.charAt(i) == ' ') {
                i--;
            }

            return i;
        }

        public static String[] patternFromJson(JsonArray array, int maxHeight, int maxWidth) {
            String[] rows = new String[array.size()];
            if (rows.length > maxHeight) {
                throw new JsonSyntaxException("Invalid pattern: too many rows, " + maxHeight + " is maximum");
            } else if (rows.length == 0) {
                throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
            } else {
                for (int i = 0; i < rows.length; ++i) {
                    String row = GsonHelper.convertToString(array.get(i), "pattern[" + i + "]");
                    if (row.length() > maxWidth) {
                        throw new JsonSyntaxException("Invalid pattern: too many columns, " + maxWidth + " is maximum");
                    }

                    if (i > 0 && rows[0].length() != row.length()) {
                        throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                    }

                    rows[i] = row;
                }

                return rows;
            }
        }

        public static Map<String, Ingredient> deserializeKey(JsonObject json) {
            Map<String, Ingredient> map = Maps.newHashMap();

            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                if (entry.getKey().length() != 1) {
                    throw new JsonSyntaxException("Invalid key entry: '" + (String) entry.getKey() + "' is an invalid symbol (must be 1 character only).");
                }

                if (" ".equals(entry.getKey())) {
                    throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
                }

                map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
            }

            map.put(" ", Ingredient.EMPTY);
            return map;
        }
    }
}
