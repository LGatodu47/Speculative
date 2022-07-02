package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.container.NuclearWorkbenchContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class NuclearWorkbenchBlock extends Block {
    private static final ITextComponent TITLE = new TranslationTextComponent("container." + Speculative.MODID + ".nuclear_workbench");

    public NuclearWorkbenchBlock() {
        super(Properties.create(Material.IRON).hardnessAndResistance(8.0F).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.NETHERITE));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if(!world.isRemote) {
            NetworkHooks.openGui((ServerPlayerEntity) player, state.getContainer(world, pos));
            return ActionResultType.CONSUME;
        }

        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public INamedContainerProvider getContainer(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedContainerProvider((windowId, playerInv, player) -> new NuclearWorkbenchContainer(windowId, playerInv, IWorldPosCallable.of(world, pos)), TITLE);
    }
}
