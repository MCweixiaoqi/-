package com.example.disassemblytable;

import com.example.disassemblytable.platform.Services;
import com.example.disassemblytable.registry.ModBlocks;
import com.example.disassemblytable.registry.ModItems;
import com.example.disassemblytable.registry.ModMenuTypes;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DisassemblyTableMod {
    public static final String MOD_ID = "disassemblytable";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static void init() {
        LOGGER.info("Initializing Disassembly Table Mod");
        ModBlocks.register();
        ModItems.register();
        ModMenuTypes.register();
        
        Services.PLATFORM.registerScreens();
        
        LOGGER.info("Disassembly Table Mod initialized successfully");
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}

