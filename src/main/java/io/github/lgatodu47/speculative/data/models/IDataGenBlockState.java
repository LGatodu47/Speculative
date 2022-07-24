package io.github.lgatodu47.speculative.data.models;

import com.google.common.base.Preconditions;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.Function;

// Unused
public interface IDataGenBlockState {
    void createBlockState(BlockStateProvider provider, Function<ModelFile[], ModelFileHelper> helperFactory);

    interface BlockImpl extends IDataGenBlockState {
        default Block impl_asBlock() {
            Preconditions.checkState(this instanceof Block, "IDataGenBlockState can only be implemented on block classes!");
            return (Block) this;
        }
    }
}
