package com.example.disassemblytable.registry;

import com.example.disassemblytable.DisassemblyTableMod;
import com.example.disassemblytable.inventory.DisassemblyTableMenu;
import com.example.disassemblytable.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final Supplier<MenuType<DisassemblyTableMenu>> DISASSEMBLY_TABLE = 
            () -> Services.PLATFORM.register(
                    ResourceKey.createRegistryKey(BuiltInRegistries.MENU.key()),
                    DisassemblyTableMod.id("disassembly_table"),
                    new MenuType<>(DisassemblyTableMenu::new)
            );

    public static void register() {
        DisassemblyTableMod.LOGGER.info("Registering menu types for " + DisassemblyTableMod.MOD_ID);
    }
}

