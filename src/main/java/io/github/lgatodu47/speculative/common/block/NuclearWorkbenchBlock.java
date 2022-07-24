package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.container.NuclearWorkbenchMenu;
import io.github.lgatodu47.speculative.data.loot.ISelfDropBlockLoot;
import io.github.lgatodu47.speculative.data.models.IDataGenBlockState;
import io.github.lgatodu47.speculative.data.models.ModelFileHelper;
import io.github.lgatodu47.speculative.data.tags.IHarvestableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.function.Function;

public class NuclearWorkbenchBlock extends Block implements ISelfDropBlockLoot, IHarvestableBlock {
    private static final Component TITLE = new TranslatableComponent("container." + Speculative.MODID + ".nuclear_workbench");

    public NuclearWorkbenchBlock() {
        super(Properties.of(Material.METAL).strength(8.0F).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops());
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rayTraceResult) {
        if (!world.isClientSide) {
            NetworkHooks.openGui((ServerPlayer) player, state.getMenuProvider(world, pos));
            return InteractionResult.CONSUME;
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        return new SimpleMenuProvider((windowId, playerInv, player) -> new NuclearWorkbenchMenu(windowId, playerInv, ContainerLevelAccess.create(world, pos)), TITLE);
    }

    @Nullable
    @Override
    public TierType getTierType() {
        return TierType.IRON;
    }

    @Nullable
    @Override
    public ToolType getToolType() {
        return ToolType.PICKAXE;
    }
}
