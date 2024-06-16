package io.github.rfmineguy.nbtremoval.util;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public interface TickableBlockEntity {
    void tick();

    static <T extends BlockEntity> BlockEntityTicker<T> getTickerHelper(Level l) {
        return l.isClientSide ? null : (level, blockPos, blockState, t) -> ((TickableBlockEntity)t).tick();
    }
}
