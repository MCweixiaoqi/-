package com.example.disassemblytable.forge;

import com.example.disassemblytable.DisassemblyTableMod;
import com.example.disassemblytable.inventory.DisassemblyTableMenu;
import com.example.disassemblytable.registry.ModMenuTypes;
import com.example.disassemblytable.screen.DisassemblyTableScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DisassemblyTableMod.MOD_ID)
public class DisassemblyTableModForge {
    public DisassemblyTableModForge() {
        // Submit our event bus to let architectury register our content
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::clientSetup);

        DisassemblyTableMod.init();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @OnlyIn(Dist.CLIENT)
    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.DISASSEMBLY_TABLE.get(), DisassemblyTableScreen::new);
        });
    }
}

