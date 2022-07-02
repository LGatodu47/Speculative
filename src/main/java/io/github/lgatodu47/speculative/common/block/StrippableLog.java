package io.github.lgatodu47.speculative.common.block;

import java.util.function.Supplier;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class StrippableLog extends RotatedPillarBlock {
    private final Supplier<Block> strippedVariant;

    public StrippableLog(Supplier<Block> strippedVariant, Properties properties) {
        super(properties);
        this.strippedVariant = strippedVariant;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.getHeldItem(handIn).getItem() instanceof AxeItem) {
            if (this.strippedVariant.get() == null) {
                Speculative.LOGGER.error(getRegistryName() + " is missing a stripped variant! This class shouldn't be used if this block is not strippable!");
                return ActionResultType.FAIL;
            } else {
                world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!world.isRemote) {
                    world.setBlockState(pos, strippedVariant.get().getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)), 11);
					EquipmentSlotType slotType = handIn.equals(Hand.MAIN_HAND) ? EquipmentSlotType.MAINHAND : EquipmentSlotType.OFFHAND;
					player.getHeldItem(handIn).damageItem(1, player, (living) -> {
						living.sendBreakAnimation(slotType);
					});
					return ActionResultType.SUCCESS;
				}
            }
        }
        return ActionResultType.PASS;
    }
}
