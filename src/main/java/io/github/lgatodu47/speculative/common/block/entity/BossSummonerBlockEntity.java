package io.github.lgatodu47.speculative.common.block.entity;

import com.google.common.collect.Lists;
import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.container.BossSummonerMenu;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlockEntityTypes;
import io.github.lgatodu47.speculative.util.SpeculativeItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class BossSummonerBlockEntity extends SpeculativeBlockEntity implements ISpeculativeTickingBlockEntity {
    private List<ExtendedBeamSegment> beams = Lists.newArrayList();
    private int beamHeight = -1;
    private boolean activated = false;

    public BossSummonerBlockEntity(BlockPos pos, BlockState state) {
        super(SpeculativeBlockEntityTypes.BOSS_SUMMONER.get(), pos, state, new SpeculativeItemStackHandler(13));
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container." + Speculative.MODID + ".boss_summoner");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new BossSummonerMenu(id, player, this);
    }

    @Override
    public void tick(Level level) {
        checkActivate();

        int x = this.worldPosition.getX();
        int y = this.worldPosition.getY();
        int z = this.worldPosition.getZ();
        BlockPos pos;
        if (this.beamHeight < y) {
            pos = this.worldPosition;
            this.beams = Lists.newArrayList();
            this.beamHeight = pos.getY() - 1;
        } else {
            pos = new BlockPos(x, this.beamHeight + 1, z);
        }

        ExtendedBeamSegment beam = this.beams.isEmpty() ? null : this.beams.get(this.beams.size() - 1);
        int height = this.level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);

        if (activated) {
            for (int i1 = 0; i1 < 10 && pos.getY() <= height; ++i1) {
                float[] color = DyeColor.WHITE.getTextureDiffuseColors();
                if (this.beams.size() <= 1) {
                    beam = new ExtendedBeamSegment(color);
                    this.beams.add(beam);
                } else if (beam != null) {
                    beam.increaseHeight();
                }

                pos = pos.above();
                ++this.beamHeight;
            }
        }


        if (this.beamHeight >= height) {
            this.beamHeight = -1;
            if (!this.level.isClientSide) {
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
    public AABB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    public static class ExtendedBeamSegment extends BeaconBlockEntity.BeaconBeamSection {
        public ExtendedBeamSegment(float[] colors) {
            super(colors);
        }

        @Override
        public void increaseHeight() {
            super.increaseHeight();
        }
    }
}
