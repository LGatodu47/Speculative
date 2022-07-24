package io.github.lgatodu47.speculative.common.item;

import io.github.lgatodu47.speculative.common.entity.SpeculativeBoatEntity;
import io.github.lgatodu47.speculative.util.SpeculativeBoatType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class SpeculativeBoatItem extends Item {
    private static final Set<SpeculativeBoatItem> BOAT_ITEMS = new HashSet<>();
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final SpeculativeBoatType type;
    private final DispenseItemBehavior dispenseItemBehavior;

    public SpeculativeBoatItem(SpeculativeBoatType type, Properties pProperties) {
        super(pProperties);
        this.type = type;
        this.dispenseItemBehavior = createDispenseItemBehaviour(type);
        BOAT_ITEMS.add(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        HitResult res = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (res.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(stack);
        } else {
            Vec3 view = player.getViewVector(1.0F);
            List<Entity> entities = level.getEntities(player, player.getBoundingBox().expandTowards(view.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!entities.isEmpty()) {
                Vec3 eye = player.getEyePosition();

                for (Entity entity : entities) {
                    AABB aabb = entity.getBoundingBox().inflate((double) entity.getPickRadius());
                    if (aabb.contains(eye)) {
                        return InteractionResultHolder.pass(stack);
                    }
                }
            }

            if (res.getType() == HitResult.Type.BLOCK) {
                SpeculativeBoatEntity boat = new SpeculativeBoatEntity(level, res.getLocation().x, res.getLocation().y, res.getLocation().z);
                boat.setType(this.type);
                boat.setYRot(player.getYRot());
                if (!level.noCollision(boat, boat.getBoundingBox())) {
                    return InteractionResultHolder.fail(stack);
                } else {
                    if (!level.isClientSide) {
                        level.addFreshEntity(boat);
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, new BlockPos(res.getLocation()));
                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass(stack);
            }
        }
    }

    private static DispenseItemBehavior createDispenseItemBehaviour(SpeculativeBoatType type) {
        return new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior delegate = new DefaultDispenseItemBehavior();

            @Override
            public ItemStack execute(BlockSource source, ItemStack stack) {
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                Level level = source.getLevel();
                double x = source.x() + (double)((float)direction.getStepX() * 1.125F);
                double y = source.y() + (double)((float)direction.getStepY() * 1.125F);
                double z = source.z() + (double)((float)direction.getStepZ() * 1.125F);
                BlockPos front = source.getPos().relative(direction);
                double yOffset;
                if (level.getFluidState(front).is(FluidTags.WATER)) {
                    yOffset = 1.0D;
                } else {
                    if (!level.getBlockState(front).isAir() || !level.getFluidState(front.below()).is(FluidTags.WATER)) {
                        return this.delegate.dispense(source, stack);
                    }

                    yOffset = 0.0D;
                }

                SpeculativeBoatEntity boat = new SpeculativeBoatEntity(level, x, y + yOffset, z);
                boat.setType(type);
                boat.setYRot(direction.toYRot());
                level.addFreshEntity(boat);
                stack.shrink(1);
                return stack;
            }

            @Override
            protected void playSound(BlockSource source) {
                source.getLevel().levelEvent(1000, source.getPos(), 0);
            }
        };
    }

    public static void setupDispenseItemBehaviours() {
        BOAT_ITEMS.forEach(boat -> DispenserBlock.registerBehavior(boat, boat.dispenseItemBehavior));
    }
}
