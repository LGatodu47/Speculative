package io.github.lgatodu47.speculative.common.tile;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.init.SpeculativeTileEntityTypes;
import io.github.lgatodu47.speculative.util.SpeculativeUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.IntSupplier;

public class UraniumBlockTileEntity extends TileEntity implements ITickableTileEntity {
    private static final int DAY_TIME = 24000;
    private static final Random RANDOM = new Random();

    private int currentRadiationTime;
    private int radiationCap;

    public UraniumBlockTileEntity() {
        this(false);
    }

    public UraniumBlockTileEntity(boolean is234) {
        super(SpeculativeTileEntityTypes.URANIUM_BLOCK.get());
        this.currentRadiationTime = RANDOM.nextInt(is234 ? 9 * DAY_TIME : 4 * DAY_TIME) + DAY_TIME;
        this.radiationCap = RANDOM.nextInt(200) + 900;
    }

    @Override
    public void tick() {
        if(currentRadiationTime > 0) {
            if(currentRadiationTime % 20 == 0) {
                if(!level.isClientSide) {
                    level.getEntitiesOfClass(PlayerEntity.class, AxisAlignedBB.unitCubeFromLowerCorner(Vector3d.atLowerCornerOf(worldPosition)).inflate(8)).forEach(player -> {
                        if(!(player.isCreative() || player.isSpectator()) && RANDOM.nextInt(4) == 0) {
                            player.addEffect(new EffectInstance(Effects.WITHER, 100));
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
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        return serialize(nbt);
    }

    public final CompoundNBT serialize(CompoundNBT nbt) {
        nbt.putInt("CurrentRadiationTime", this.currentRadiationTime);
        nbt.putInt("IrradiationCap", this.radiationCap);
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        deserialize(nbt);
    }

    public final void deserialize(CompoundNBT nbt) {
        this.currentRadiationTime = nbt.getInt("CurrentRadiationTime");
        this.radiationCap = nbt.getInt("IrradiationCap");
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        load(level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        load(state, tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(worldPosition, 0, save(new CompoundNBT()));
    }
}
