package com.example.disassemblytable.registry;

import com.example.disassemblytable.DisassemblyTableMod;
import com.example.disassemblytable.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ModItems {
    public static final Supplier<Item> DISASSEMBLY_TABLE = 
            () -> Services.PLATFORM.register(
                    ResourceKey.createRegistryKey(BuiltInRegistries.ITEM.key()),
                    DisassemblyTableMod.id("disassembly_table"),
                    new BlockItem(ModBlocks.DISASSEMBLY_TABLE.get(), new Item.Properties())
            );

    public static void register() {
        DisassemblyTableMod.LOGGER.info("Registering items for " + DisassemblyTableMod.MOD_ID);
    }
}

