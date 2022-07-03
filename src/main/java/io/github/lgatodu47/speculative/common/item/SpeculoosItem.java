package io.github.lgatodu47.speculative.common.item;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.init.SpeculativeWorlds;
import io.github.lgatodu47.speculative.common.world.dimension.speculo_world.SpeculoWorldTeleporter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.item.Item.Properties;

public class SpeculoosItem extends Item {
    public SpeculoosItem() {
        super(new Properties().durability(16).tab(Speculative.tab()).food(new Food.Builder().nutrition(1).saturationMod(0.5F).alwaysEat().build()));
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        return super.onItemUseFirst(stack, context);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity living, int count) {
        if(living instanceof PlayerEntity) {
            living.addEffect(new EffectInstance(Effects.CONFUSION, 60, 0, false, false, false));
            living.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 30, false, false, false));
            living.addEffect(new EffectInstance(Effects.BLINDNESS, 60, 0, false, false, false));
        }

        super.onUsingTick(stack, living, count);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity living) {
        stack.hurtAndBreak(1, living, (p) -> p.broadcastBreakEvent(EquipmentSlotType.MAINHAND));

        if (living instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) living;

            if(!worldIn.isClientSide) {
                MinecraftServer server = worldIn.getServer();

                if(server != null) {
                    if (worldIn.dimension().equals(World.OVERWORLD)) {
                        ServerWorld dimension = server.getLevel(SpeculativeWorlds.SPECULO_WORLD);
                        if(dimension != null) {
                            player.changeDimension(dimension, SpeculoWorldTeleporter.INSTANCE);
                        }
                    } else if (worldIn.dimension().equals(SpeculativeWorlds.SPECULO_WORLD)) {
                        ServerWorld overworld = server.getLevel(World.OVERWORLD);
                        if(overworld != null) {
                            player.changeDimension(overworld, SpeculoWorldTeleporter.INSTANCE);
                        }
                    } else {
                        player.displayClientMessage(new TranslationTextComponent("message.speculative.speculoos_cannot_tp"), true);
                    }
                }
            }
        }

        return this.onFoodEaten(worldIn, stack, living);
    }

    public ItemStack onFoodEaten(World world, ItemStack stack, LivingEntity living) {
        if (stack.isEdible()) {
            world.playSound(null, living.getX(), living.getY(), living.getZ(), living.getEatingSound(stack), SoundCategory.NEUTRAL, 1.0F, 1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);

            Method applyFoodEffects = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "addEatEffect", ItemStack.class, World.class, LivingEntity.class);
            applyFoodEffects.setAccessible(true);
            try {
                applyFoodEffects.invoke(living, stack, world, living);
            } catch (IllegalAccessException e) {
                Speculative.LOGGER.error("Cannot access method 'applyFoodEffects' in LivingEntity class!", e);
            } catch (IllegalArgumentException e) {
                Speculative.LOGGER.error("Something changed about the arguments of the method 'applyFoodEffects' in LivingEntity class!", e);
            } catch (InvocationTargetException e) {
                Speculative.LOGGER.error("An exception was caught when invoking 'applyFoodEffects' in LivingEntity. Pretty weird as the method doesn't throw any checked exception...");
            }
        }

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 64;
    }
}
