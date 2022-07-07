package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.container.*;
import io.github.lgatodu47.speculative.common.block.entity.BossSummonerBlockEntity;
import io.github.lgatodu47.speculative.common.block.entity.CentrifugeBlockEntity;
import io.github.lgatodu47.speculative.common.block.entity.SpeculoosSummonerBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SpeculativeMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Speculative.MODID);

    public static final RegistryObject<MenuType<SpeculoosSummonerMenu>> SPECULOOS_SUMMONER = MENU_TYPES.register("speculoos_summoner_container", make(SpeculoosSummonerBlockEntity.class, SpeculoosSummonerMenu::new));
    public static final RegistryObject<MenuType<CentrifugeMenu>> CENTRIFUGE_CONTAINER = MENU_TYPES.register("centrifuge_container", make(CentrifugeBlockEntity.class, CentrifugeMenu::new));
    public static final RegistryObject<MenuType<BossSummonerMenu>> BOSS_SUMMONER = MENU_TYPES.register("boss_summoner", make(BossSummonerBlockEntity.class, BossSummonerMenu::new));
    public static final RegistryObject<MenuType<NuclearWorkbenchMenu>> NUCLEAR_WORKBENCH = MENU_TYPES.register("nuclear_workbench", () -> IForgeMenuType.create(NuclearWorkbenchMenu::new));

    private static <T extends BlockEntity, M extends SpeculativeMenu<T>> Supplier<MenuType<M>> make(Class<T> blockEntityClass, SpeculativeMenuFactory<T, M> factory) {
        return () -> IForgeMenuType.create((windowId, inv, data) -> factory.create(windowId, inv, getBlockEntity(blockEntityClass, inv, data)));
    }

    private static <T extends BlockEntity> T getBlockEntity(Class<T> clazz, Inventory playerInv, FriendlyByteBuf data) {
        BlockEntity tile = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (clazz.isInstance(tile)) {
            return clazz.cast(tile);
        }
        throw new IllegalStateException("Block Entity is not correct! " + tile);
    }

    interface SpeculativeMenuFactory<T extends BlockEntity, M extends SpeculativeMenu<T>> {
        M create(int windowId, Inventory inv, T tile);
    }
}
