package com.example.disassemblytable.platform.services;

import com.example.disassemblytable.block.entity.DisassemblyTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IPlatformHelper {
    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Register an object to a registry.
     *
     * @param registry The registry to register to.
     * @param id The ID of the object.
     * @param object The object to register.
     * @param <T> The type of the object.
     * @return The registered object.
     */
    <T> T register(ResourceKey<? extends Registry<T>> registry, ResourceLocation id, T object);

    /**
     * Register screens for the mod.
     */
    void registerScreens();

    /**
     * Open a menu for a player.
     *
     * @param player The player to open the menu for.
     * @param blockEntity The block entity to open the menu for.
     * @param pos The position of the block entity.
     */
    void openMenu(ServerPlayer player, DisassemblyTableBlockEntity blockEntity, BlockPos pos);

    /**
     * Drop the contents of a container.
     *
     * @param level The level to drop the contents in.
     * @param pos The position to drop the contents at.
     * @param items The items to drop.
     */
    void dropContents(Level level, BlockPos pos, NonNullList<ItemStack> items);
}

