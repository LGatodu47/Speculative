package io.github.lgatodu47.speculative.data.loot;

import com.google.common.base.Preconditions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

public interface ISelfDropBlockLoot extends IDataGenLoot {
    @Override
    default LootTable.Builder makeLootTable() {
        Preconditions.checkState(this instanceof Block, "Interface ISelfDropBlockLoot can only be implemented on block classes!");
        return Helper.createSingleItemTable((Block) this);
    }
}
