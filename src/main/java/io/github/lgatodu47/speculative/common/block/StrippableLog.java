package io.github.lgatodu47.speculative.common.block;

import java.util.function.Supplier;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class StrippableLog extends RotatedPillarBlock {
    private final Supplier<Block> strippedVariant;

    public StrippableLog(Supplier<Block> strippedVariant, Properties properties) {
        super(properties);
        this.strippedVariant = strippedVariant;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getItemInHand(handIn).getItem() instanceof AxeItem) {
            if (this.strippedVariant.get() == null) {
                Speculative.LOGGER.error(getRegistryName() + " is missing a stripped variant! This class shouldn't be used if this block is not strippable!");
                return InteractionResult.FAIL;
            } else {
                world.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!world.isClientSide) {
                    world.setBlock(pos, strippedVariant.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 11);
					EquipmentSlot slotType = handIn.equals(InteractionHand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
					player.getItemInHand(handIn).hurtAndBreak(1, player, (living) -> {
						living.broadcastBreakEvent(slotType);
					});
					return InteractionResult.SUCCESS;
				}
            }
        }
        return InteractionResult.PASS;
    }
}
