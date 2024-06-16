package io.github.rfmineguy.nbtremoval.register;

import io.github.rfmineguy.nbtremoval.NBTRemovalMod;
import io.github.rfmineguy.nbtremoval.blocks.nbt_removal.NBTRemovalBlock;
import io.github.rfmineguy.nbtremoval.blocks.nbt_removal.NBTRemovalBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRegistration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NBTRemovalMod.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NBTRemovalMod.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, NBTRemovalMod.MODID);

    public static final RegistryObject<Block>     BLOCK_NBT_REMOVAL = BLOCKS.register("nbt_removal_block", NBTRemovalBlock::new);
    public static final RegistryObject<BlockItem> BLOCK_ITEM_NBT_REMOVAL = ITEMS.register("nbt_removal_block", () -> new BlockItem(BLOCK_NBT_REMOVAL.get(), new Item.Properties()));

    public static final RegistryObject<BlockEntityType<NBTRemovalBlockEntity>> BLOCK_ENTITY_NBT_REMOVAL =
            BLOCK_ENTITY_TYPES.register("nbt_removal_block",
                    () -> BlockEntityType.Builder.of(NBTRemovalBlockEntity::new, BLOCK_NBT_REMOVAL.get()).build(null));
    public static void registerAll(IEventBus bus) {
        ITEMS.register(bus);
        BLOCKS.register(bus);
        BLOCK_ENTITY_TYPES.register(bus);
    }
}
