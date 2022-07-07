package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.item.SpeculativeFoodItem;
import io.github.lgatodu47.speculative.common.item.SpeculativeFoods;
import io.github.lgatodu47.speculative.common.item.SpeculoosItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class SpeculativeItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Speculative.MODID);

    public static final RegistryObject<Item> SPECULO_GEM = ITEMS.register("speculo_gem", basicItem());
    public static final RegistryObject<Item> SULFURIC_WATER_BUCKET = ITEMS.register("sulfuric_water_bucket", () -> new BucketItem(SpeculativeFluids.SULFURIC_WATER.getStillFluid(), props().stacksTo(1)));
    public static final RegistryObject<Item> ORANGE_FRUIT = ITEMS.register("orange_fruit", () -> new SpeculativeFoodItem(SpeculativeFoods.ORANGE_FRUIT));
    public static final RegistryObject<Item> SPECULOOS = ITEMS.register("speculoos", SpeculoosItem::new);
    public static final RegistryObject<Item> RADIOACTIVE_DIAMOND = ITEMS.register("radioactive_diamond", basicItem());
    public static final RegistryObject<Item> URANIUM_238 = ITEMS.register("uranium_238", basicItem());
    public static final RegistryObject<Item> URANIUM_325 = ITEMS.register("uranium_235", basicItem());
    public static final RegistryObject<Item> URANIUM_234 = ITEMS.register("uranium_234", basicItem());
    public static final RegistryObject<Item> SPECULO_WOOD_SIGN = ITEMS.register("speculo_wood_sign", () -> new SignItem(props(), SpeculativeBlocks.SPECULO_WOOD_STANDING_SIGN.get(), SpeculativeBlocks.SPECULO_WOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> GRAPHITE_INGOT = ITEMS.register("graphite_ingot", basicItem());
    public static final RegistryObject<Item> URANINITE_INGOT = ITEMS.register("uraninite_ingot", basicItem());
    public static final RegistryObject<Item> SPECULO_PIG_SPAWN_EGG = ITEMS.register("speculo_pig_spawn_egg", () -> new ForgeSpawnEggItem(SpeculativeEntityTypes.SPECULO_PIG, 15552522, 16743462, props()));
    public static final RegistryObject<Item> STRANGE_MANGO = ITEMS.register("strange_mango", () -> new SpeculativeFoodItem(SpeculativeFoods.STRANGE_MANGO));
    public static final RegistryObject<Item> GREENSTONE_TORCH = ITEMS.register("greenstone_torch", () -> new StandingAndWallBlockItem(SpeculativeBlocks.GREENSTONE_TORCH.get(), SpeculativeBlocks.GREENSTONE_WALL_TORCH.get(), props()));
    public static final RegistryObject<Item> UNSTABLE_WATER_BUCKET = ITEMS.register("unstable_water_bucket", () -> new BucketItem(SpeculativeFluids.UNSTABLE_WATER.getStillFluid(), props().stacksTo(1)));
    public static final RegistryObject<Item> SPECULO_PORKCHOP = ITEMS.register("speculo_porkchop", () -> new SpeculativeFoodItem(SpeculativeFoods.SPECULO_PORKCHOP));
    public static final RegistryObject<Item> COOKED_SPECULO_PORKCHOP = ITEMS.register("cooked_speculo_porkchop", () -> new SpeculativeFoodItem(SpeculativeFoods.COOKED_SPECULO_PORKCHOP));
    public static final RegistryObject<Item> LIQUID_NITROGEN_BUCKET = ITEMS.register("liquid_nitrogen_bucket", () -> new BucketItem(SpeculativeFluids.LIQUID_NITROGEN.getStillFluid(), props().stacksTo(1)));
    public static final RegistryObject<Item> TOURMALINE_WOOD_SIGN = ITEMS.register("tourmaline_wood_sign", () -> new SignItem(props(), SpeculativeBlocks.TOURMALINE_WOOD_STANDING_SIGN.get(), SpeculativeBlocks.TOURMALINE_WOOD_WALL_SIGN.get()));

    private static Supplier<Item> basicItem() {
        return () -> new Item(props());
    }

    private static Properties props() {
        return new Properties().tab(Speculative.tab());
    }
}
