package com.example.disassemblytable.container;

import com.example.disassemblytable.DisassemblyTableMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, DisassemblyTableMod.MOD_ID);

    public static final RegistryObject<MenuType<DisassemblyTableContainer>> DISASSEMBLY_TABLE_CONTAINER =
            registerMenuType(DisassemblyTableContainer::new, "disassembly_table_container");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                 String name) {
        return CONTAINERS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}

