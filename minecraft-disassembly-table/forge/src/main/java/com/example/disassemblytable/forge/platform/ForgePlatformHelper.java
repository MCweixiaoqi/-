package com.example.disassemblytable.forge.platform;

import com.example.disassemblytable.block.entity.DisassemblyTableBlockEntity;
import com.example.disassemblytable.platform.services.IPlatformHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.RegisterEvent;

public class ForgePlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public <T> T register(ResourceKey<? extends Registry<T>> registry, ResourceLocation id, T object) {
        // In Forge, registration is handled through the event system
        return object;
    }

    @Override
    public void registerScreens() {
        // Screens are registered in the client setup event
    }

    @Override
    public void openMenu(ServerPlayer player, DisassemblyTableBlockEntity blockEntity, BlockPos pos) {
        NetworkHooks.openScreen(player, blockEntity, pos);
    }

    @Override
    public void dropContents(Level level, BlockPos pos, NonNullList<ItemStack> items) {
        Containers.dropContents(level, pos, items);
    }
}

