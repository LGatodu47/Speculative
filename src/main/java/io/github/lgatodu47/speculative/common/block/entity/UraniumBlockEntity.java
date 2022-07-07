package io.github.lgatodu47.speculative.common.block.entity;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlockEntityTypes;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.util.SpeculativeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.IntSupplier;

public class UraniumBlockEntity extends BlockEntity implements ISpeculativeTickingBlockEntity {
    private static final int DAY_TIME = 24000;
    private static final Random RANDOM = new Random();

    private int currentRadiationTime;
    private int radiationCap;

    public UraniumBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, false);
    }

    public UraniumBlockEntity(BlockPos pos, BlockState state, boolean is234) {
        super(SpeculativeBlockEntityTypes.URANIUM_BLOCK.get(), pos, state);
        this.currentRadiationTime = RANDOM.nextInt(is234 ? 9 * DAY_TIME : 4 * DAY_TIME) + DAY_TIME;
        this.radiationCap = RANDOM.nextInt(200) + 900;
    }

    @Override
    public void tick(Level level) {
        if(currentRadiationTime > 0) {
            if(currentRadiationTime % 20 == 0) {
                if(!level.isClientSide) {
                    level.getEntitiesOfClass(Player.class, AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(worldPosition)).inflate(8)).forEach(player -> {
                        if(!(player.isCreative() || player.isSpectator()) && RANDOM.nextInt(4) == 0) {
                            player.addEffect(new MobEffectInstance(MobEffects.WITHER, 100));
                        }
                    });
                }
            }

            if(currentRadiationTime % radiationCap == 0) {
                if(!level.isClientSide) {
                    BlockPos pos = getRandomPos();
                    if(!level.isEmptyBlock(pos)) {
                        BlockState state = level.getBlockState(pos);
                        if(!state.is(SpeculativeBlocks.Tags.URANIUM_PROOF) && state.canOcclude()) {
                            level.setBlockAndUpdate(pos, ForgeRegistries.BLOCKS.getValue(SpeculativeUtils.getRandomElement(RANDOM, ForgeRegistries.BLOCKS.getKeys())).defaultBlockState());
                        }
                    }
                }
                radiationCap = RANDOM.nextInt(200) + 900;
            }

            --currentRadiationTime;
        }
        else {
            level.setBlockAndUpdate(getBlockPos(), SpeculativeBlocks.URANIUM_238_BLOCK.get().defaultBlockState());
            if(!isRemoved()) {
                setRemoved();
            }
        }
    }

    private BlockPos getRandomPos() {
        final int radius = 5;
        final IntSupplier randValueGenerator = () -> {
            int value = RANDOM.nextInt(radius) + 1;
            return RANDOM.nextBoolean() ? -value : value;
        };

        return getRandomBlockRecursive(randValueGenerator, 0);
    }

    private BlockPos getRandomBlockRecursive(IntSupplier generator, int level) {
        BlockPos result = getBlockPos().offset(generator.getAsInt(), generator.getAsInt(), generator.getAsInt());
        if(this.level.isEmptyBlock(result) && level < 5) {
            result = getRandomBlockRecursive(generator, level + 1);
        }
        return result;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        serialize(nbt);
    }

    public final CompoundTag serialize(CompoundTag nbt) {
        nbt.putInt("CurrentRadiationTime", this.currentRadiationTime);
        nbt.putInt("IrradiationCap", this.radiationCap);
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        deserialize(nbt);
    }

    public final void deserialize(CompoundTag nbt) {
        this.currentRadiationTime = nbt.getInt("CurrentRadiationTime");
        this.radiationCap = nbt.getInt("IrradiationCap");
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
