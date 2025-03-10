package com.example.disassemblytable;

import com.example.disassemblytable.block.ModBlocks;
import com.example.disassemblytable.block.entity.ModBlockEntities;
import com.example.disassemblytable.container.ModContainers;
import com.example.disassemblytable.item.ModItems;
import com.example.disassemblytable.screen.DisassemblyTableScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DisassemblyTableMod.MOD_ID)
public class DisassemblyTableMod {
    public static final String MOD_ID = "disassemblytable";
    private static final Logger LOGGER = LogManager.getLogger();

    public DisassemblyTableMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModContainers.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Disassembly Table Mod Setup");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(ModContainers.DISASSEMBLY_TABLE_CONTAINER.get(), 
                DisassemblyTableScreen::new);
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}

