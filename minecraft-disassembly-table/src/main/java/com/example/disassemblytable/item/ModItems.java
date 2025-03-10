package com.example.disassemblytable.item;

import com.example.disassemblytable.DisassemblyTableMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
            DeferredRegister.create(ForgeRegistries.ITEMS, DisassemblyTableMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

