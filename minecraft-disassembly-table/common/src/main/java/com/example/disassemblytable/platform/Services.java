package com.example.disassemblytable.platform;

import com.example.disassemblytable.DisassemblyTableMod;
import com.example.disassemblytable.block.entity.DisassemblyTableBlockEntity;
import com.example.disassemblytable.platform.services.IPlatformHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ServiceLoader;

public class Services {
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        DisassemblyTableMod.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

    public static <T> T register(ResourceKey<? extends Registry<T>> registry, String name, T object) {
        return PLATFORM.register(registry, DisassemblyTableMod.id(name), object);
    }
}

