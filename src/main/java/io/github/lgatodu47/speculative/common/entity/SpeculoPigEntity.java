package io.github.lgatodu47.speculative.common.entity;

import io.github.lgatodu47.speculative.common.init.SpeculativeEntityTypes;
import io.github.lgatodu47.speculative.common.init.SpeculativeItems;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SpeculoPigEntity extends PigEntity {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(SpeculativeItems.ORANGE_FRUIT.get());

    public SpeculoPigEntity(EntityType<? extends SpeculoPigEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(Items.CARROT_ON_A_STICK), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    public void thunderHit(ServerWorld world, LightningBoltEntity lightning) {
        super.thunderHit(world, lightning);
    }

    @Override
    public PigEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
        return SpeculativeEntityTypes.SPECULO_PIG.get().create(this.level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }
}
