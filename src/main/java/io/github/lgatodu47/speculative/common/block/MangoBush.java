package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.common.init.SpeculativeItems;
import io.github.lgatodu47.speculative.data.loot.IDataGenLoot;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class MangoBush extends Block implements BonemealableBlock, IDataGenLoot {
    private static final IntegerProperty AGE = BlockStateProperties.AGE_1;

    public MangoBush() {
        super(Properties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).noOcclusion().randomTicks());
        registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (state.getValue(AGE) == 1) {
            player.addItem(new ItemStack(SpeculativeItems.STRANGE_MANGO.get()));
            worldIn.setBlock(pos, state.setValue(AGE, 0), 2);
            player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 0.5F, 2F);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        if (!this.canSurvive(state, worldIn, pos)) {
            worldIn.destroyBlock(pos, false);
        } else {
            int chance = rand.nextInt(10);
            if (chance == 0) {
                this.performBonemeal(worldIn, rand, pos, state);
            }
        }
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return (worldIn.getRawBrightness(pos, 0) >= 8 || worldIn.canSeeSky(pos));
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(AGE) < 1;
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlock(pos, state.setValue(AGE, 1), 2);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AGE);
    }

    @Override
    public LootTable.Builder makeLootTable() {
        return Helper.createShearsDispatchTable(this,
                LootItem.lootTableItem(SpeculativeItems.STRANGE_MANGO.get())
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(this)
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MangoBush.AGE, 1))));
    }
}
