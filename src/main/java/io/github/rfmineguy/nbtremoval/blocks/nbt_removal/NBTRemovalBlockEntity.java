package io.github.rfmineguy.nbtremoval.blocks.nbt_removal;

import io.github.rfmineguy.nbtremoval.NBTRemovalMod;
import io.github.rfmineguy.nbtremoval.register.ModRegistration;
import io.github.rfmineguy.nbtremoval.util.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NBTRemovalBlockEntity extends BlockEntity implements TickableBlockEntity {
    private int totalRemovedNbt;
    private final ItemStackHandler inputInventory = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            NBTRemovalBlockEntity.this.setChanged();
        }
    };
    private final LazyOptional<ItemStackHandler> inputInventoryOptional = LazyOptional.of(() -> inputInventory);

    private final ItemStackHandler outputInventory = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            NBTRemovalBlockEntity.this.setChanged();
        }
    };
    private final LazyOptional<ItemStackHandler> outputInventoryOptional = LazyOptional.of(() -> outputInventory);

    public NBTRemovalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModRegistration.BLOCK_ENTITY_NBT_REMOVAL.get(), pPos, pBlockState);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        CompoundTag nbtRemovalModId = pTag.getCompound(NBTRemovalMod.MODID);
        this.totalRemovedNbt = nbtRemovalModId.getInt("totalRemovedNbt");
        this.inputInventory.deserializeNBT(nbtRemovalModId.getCompound("inputInventory"));
        this.outputInventory.deserializeNBT(nbtRemovalModId.getCompound("outputInventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        CompoundTag nbtRemovalModId = new CompoundTag();
        nbtRemovalModId.putInt("totalRemovedNbt", totalRemovedNbt);
        nbtRemovalModId.put("inputInventory", inputInventory.serializeNBT());
        nbtRemovalModId.put("outputInventory", outputInventory.serializeNBT());
        pTag.put(NBTRemovalMod.MODID, nbtRemovalModId);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.inputInventoryOptional.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.inputInventoryOptional.invalidate();
        this.outputInventoryOptional.invalidate();
    }

    public int getTotalRemovedNbt() {
        setChanged();
        return this.totalRemovedNbt;
    }

    public ItemStackHandler getInputInventory() {
        return inputInventory;
    }

    public ItemStack getStackInSlot(int slot) {
        return inputInventory.getStackInSlot(slot);
    }

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide)
            return;
        System.out.println("Hello from nbt remover");
    }
}
