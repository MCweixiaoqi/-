package com.example.disassemblytable.fabric.platform;

import com.example.disassemblytable.block.entity.DisassemblyTableBlockEntity;
import com.example.disassemblytable.platform.services.IPlatformHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <T> T register(ResourceKey<? extends Registry<T>> registry, ResourceLocation id, T object) {
        return Registry.register(FabricLoader.getInstance().getGameInstance().registryAccess().registryOrThrow(registry), id, object);
    }

    @Override
    public void registerScreens() {
        // Screens are registered in the client initializer
    }

    @Override
    public void openMenu(ServerPlayer player, DisassemblyTableBlockEntity blockEntity, BlockPos pos) {
        player.openMenu(new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                buf.writeBlockPos(pos);
            }

            @Override
            public Component getDisplayName() {
                return blockEntity.getDisplayName();
            }

            @Override
            public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
                return blockEntity.createMenu(containerId, inventory, player);
            }
        });
    }

    @Override
    public void dropContents(Level level, BlockPos pos, NonNullList<ItemStack> items) {
        Containers.dropContents(level, pos, items);
    }
}

