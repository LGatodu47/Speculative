package io.github.lgatodu47.speculative.common.init;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.container.*;
import io.github.lgatodu47.speculative.common.tile.BossSummonerTileEntity;
import io.github.lgatodu47.speculative.common.tile.CentrifugeTileEntity;
import io.github.lgatodu47.speculative.common.tile.SpeculoosSummonerTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SpeculativeContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Speculative.MODID);

    public static final RegistryObject<ContainerType<SpeculoosSummonerContainer>> SPECULOOS_SUMMONER = CONTAINER_TYPES.register("speculoos_summoner_container", make(SpeculoosSummonerTileEntity.class, SpeculoosSummonerContainer::new));
    public static final RegistryObject<ContainerType<CentrifugeContainer>> CENTRIFUGE_CONTAINER = CONTAINER_TYPES.register("centrifuge_container", make(CentrifugeTileEntity.class, CentrifugeContainer::new));
    public static final RegistryObject<ContainerType<BossSummonerContainer>> BOSS_SUMMONER = CONTAINER_TYPES.register("boss_summoner", make(BossSummonerTileEntity.class, BossSummonerContainer::new));
    public static final RegistryObject<ContainerType<NuclearWorkbenchContainer>> NUCLEAR_WORKBENCH = CONTAINER_TYPES.register("nuclear_workbench", () -> IForgeContainerType.create(NuclearWorkbenchContainer::new));

    private static <T extends TileEntity, C extends SpeculativeContainer<T>> Supplier<ContainerType<C>> make(Class<T> tileClass, SpeculativeContainerFactory<T, C> factory) {
        return () -> IForgeContainerType.create((windowId, inv, data) -> factory.create(windowId, inv, getTileEntity(tileClass, inv, data)));
    }

    private static <T extends TileEntity> T getTileEntity(Class<T> clazz, PlayerInventory playerInv, PacketBuffer data) {
        TileEntity tile = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (clazz.isInstance(tile)) {
            return clazz.cast(tile);
        }
        throw new IllegalStateException("Tile entity is not correct! " + tile);
    }

    interface SpeculativeContainerFactory<T extends TileEntity, C extends SpeculativeContainer<T>> {
        C create(int windowId, PlayerInventory inv, T tile);
    }
}
