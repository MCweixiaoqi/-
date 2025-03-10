package com.example.disassemblytable.fabric;

import com.example.disassemblytable.inventory.DisassemblyTableMenu;
import com.example.disassemblytable.registry.ModMenuTypes;
import com.example.disassemblytable.screen.DisassemblyTableScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class DisassemblyTableModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenuTypes.DISASSEMBLY_TABLE.get(), DisassemblyTableScreen::new);
    }
}

