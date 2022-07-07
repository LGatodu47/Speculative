package io.github.lgatodu47.speculative.common.item;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.init.SpeculativeWorlds;
import io.github.lgatodu47.speculative.common.world.dimension.speculo_world.SpeculoWorldTeleporter;
import io.github.lgatodu47.speculative.util.SpeculativeReflectionHelper;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpeculoosItem extends Item {
    public SpeculoosItem() {
        super(new Properties().durability(16).tab(Speculative.tab()).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.5F).alwaysEat().build()));
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity living, int count) {
        if(living instanceof Player) {
            living.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 0, false, false, false));
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 30, false, false, false));
            living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0, false, false, false));
        }

        super.onUsingTick(stack, living, count);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity living) {
        stack.hurtAndBreak(1, living, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));

        if (living instanceof Player player) {

            if(!worldIn.isClientSide) {
                MinecraftServer server = worldIn.getServer();

                if(server != null) {
                    if (worldIn.dimension().equals(Level.OVERWORLD)) {
                        ServerLevel dimension = server.getLevel(SpeculativeWorlds.SPECULO_WORLD);
                        if(dimension != null) {
                            player.changeDimension(dimension, SpeculoWorldTeleporter.INSTANCE);
                        }
                    } else if (worldIn.dimension().equals(SpeculativeWorlds.SPECULO_WORLD)) {
                        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
                        if(overworld != null) {
                            player.changeDimension(overworld, SpeculoWorldTeleporter.INSTANCE);
                        }
                    } else {
                        player.displayClientMessage(new TranslatableComponent("message.speculative.speculoos_cannot_tp"), true);
                    }
                }
            }
        }

        return this.onFoodEaten(worldIn, stack, living);
    }

    public ItemStack onFoodEaten(Level world, ItemStack stack, LivingEntity living) {
        if (stack.isEdible()) {
            world.playSound(null, living.getX(), living.getY(), living.getZ(), living.getEatingSound(stack), SoundSource.NEUTRAL, 1.0F, 1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);
            // addEatEffect
            SpeculativeReflectionHelper.getMethod(LivingEntity.class, living, "m_21063_", ItemStack.class, Level.class, LivingEntity.class).invoke(stack, world, living);
        }

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 64;
    }
}
