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
                if(!world.isRemote) {
                    world.getEntitiesWithinAABB(PlayerEntity.class, AxisAlignedBB.fromVector(Vector3d.copy(pos)).grow(8)).forEach(player -> {
                        if(!(player.isCreative() || player.isSpectator()) && RANDOM.nextInt(4) == 0) {
                            player.addPotionEffect(new EffectInstance(Effects.WITHER, 100));
                        }
                    });
                }
            }

            if(currentRadiationTime % radiationCap == 0) {
                if(!world.isRemote) {
                    BlockPos pos = getRandomPos();
                    if(!world.isAirBlock(pos)) {
                        BlockState state = world.getBlockState(pos);
                        if(!state.isIn(SpeculativeBlocks.Tags.URANIUM_PROOF) && state.isSolid()) {
                            world.setBlockState(pos, ForgeRegistries.BLOCKS.getValue(SpeculativeUtils.getRandomElement(RANDOM, ForgeRegistries.BLOCKS.getKeys())).getDefaultState());
                        }
                    }
                }
                radiationCap = RANDOM.nextInt(200) + 900;
            }

            --currentRadiationTime;
        }
        else {
            world.setBlockState(getPos(), SpeculativeBlocks.URANIUM_238_BLOCK.get().getDefaultState());
            if(!isRemoved()) {
                remove();
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
        BlockPos result = getPos().add(generator.getAsInt(), generator.getAsInt(), generator.getAsInt());
        if(world.isAirBlock(result) && level < 5) {
            result = getRandomBlockRecursive(generator, level + 1);
        }
        return result;
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);
        return serialize(nbt);
    }

    public final CompoundNBT serialize(CompoundNBT nbt) {
        nbt.putInt("CurrentRadiationTime", this.currentRadiationTime);
        nbt.putInt("IrradiationCap", this.radiationCap);
        return nbt;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        deserialize(nbt);
    }

    public final void deserialize(CompoundNBT nbt) {
        this.currentRadiationTime = nbt.getInt("CurrentRadiationTime");
        this.radiationCap = nbt.getInt("IrradiationCap");
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(world.getBlockState(pkt.getPos()), pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        read(state, tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 0, write(new CompoundNBT()));
    }
}
