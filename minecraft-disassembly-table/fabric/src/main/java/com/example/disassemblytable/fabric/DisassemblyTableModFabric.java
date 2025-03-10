package com.example.disassemblytable.fabric;

import com.example.disassemblytable.DisassemblyTableMod;
import net.fabricmc.api.ModInitializer;

public class DisassemblyTableModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DisassemblyTableMod.init();
    }
}

