package io.github.lgatodu47.speculative.common.tile;

import com.google.common.collect.Lists;
import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.container.BossSummonerContainer;
import io.github.lgatodu47.speculative.common.init.SpeculativeTileEntityTypes;
import io.github.lgatodu47.speculative.util.SpeculativeItemStackHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Items;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.Heightmap;

import java.util.List;

public class BossSummonerTileEntity extends SpeculativeLockableTileEntity implements ITickableTileEntity {
    private List<ExtendedBeamSegment> beams = Lists.newArrayList();
    private int beamHeight = -1;
    private boolean activated = false;

    public BossSummonerTileEntity() {
        super(SpeculativeTileEntityTypes.BOSS_SUMMONER.get(), new SpeculativeItemStackHandler(13));
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Speculative.MODID + ".boss_summoner");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new BossSummonerContainer(id, player, this);
    }

    @Override
    public void tick() {
        checkActivate();

        int x = this.pos.getX();
        int y = this.pos.getY();
        int z = this.pos.getZ();
        BlockPos pos;
        if (this.beamHeight < y) {
            pos = this.pos;
            this.beams = Lists.newArrayList();
            this.beamHeight = pos.getY() - 1;
        } else {
            pos = new BlockPos(x, this.beamHeight + 1, z);
        }

        ExtendedBeamSegment beam = this.beams.isEmpty() ? null : this.beams.get(this.beams.size() - 1);
        int height = this.world.getHeight(Heightmap.Type.WORLD_SURFACE, x, z);

        if (activated) {
            for (int i1 = 0; i1 < 10 && pos.getY() <= height; ++i1) {
                float[] color = DyeColor.WHITE.getColorComponentValues();
                if (this.beams.size() <= 1) {
                    beam = new ExtendedBeamSegment(color);
                    this.beams.add(beam);
                } else if (beam != null) {
                    beam.incrementHeight();
                }

                pos = pos.up();
                ++this.beamHeight;
            }
        }


        if (this.beamHeight >= height) {
            this.beamHeight = -1;
            if (!this.world.isRemote) {
                if (activated) {
                    //this.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE);

                    /*for (ServerPlayerEntity serverplayerentity : this.world.getEntitiesWithinAABB(ServerPlayerEntity.class, new AxisAlignedBB(x, y, z, x, (y - 4), z).grow(10.0D, 5.0D, 10.0D))) {
                    }*/
                }/* else if (flag && !flag1) {
					this.playSound(SoundEvents.BLOCK_BEACON_DEACTIVATE);
				}*/
            }
        }
    }

    private void checkActivate() {
        activated = inv.getStackInSlot(0).getItem() == Items.DIAMOND;
    }

    public List<ExtendedBeamSegment> getBeamSegments() {
        return beams;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    public static class ExtendedBeamSegment extends BeaconTileEntity.BeamSegment {
        public ExtendedBeamSegment(float[] colors) {
            super(colors);
        }

        @Override
        public void incrementHeight() {
            super.incrementHeight();
        }
    }
}
