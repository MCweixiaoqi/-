package com.example.disassemblytable.registry;

import com.example.disassemblytable.DisassemblyTableMod;
import com.example.disassemblytable.block.DisassemblyTableBlock;
import com.example.disassemblytable.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class ModBlocks {
    public static final Supplier<Block> DISASSEMBLY_TABLE = 
            () -> Services.PLATFORM.register(
                    ResourceKey.createRegistryKey(BuiltInRegistries.BLOCK.key()),
                    DisassemblyTableMod.id("disassembly_table"),
                    new DisassemblyTableBlock(BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .strength(2.5f)
                            .sound(SoundType.WOOD)
                            .requiresCorrectToolForDrops())
            );

    public static void register() {
        DisassemblyTableMod.LOGGER.info("Registering blocks for " + DisassemblyTableMod.MOD_ID);
    }
}

