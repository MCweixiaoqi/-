package com.example.disassemblytable.registry;

import com.example.disassemblytable.DisassemblyTableMod;
import com.example.disassemblytable.block.entity.DisassemblyTableBlockEntity;
import com.example.disassemblytable.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final Supplier<BlockEntityType<DisassemblyTableBlockEntity>> DISASSEMBLY_TABLE = 
            () -> Services.PLATFORM.register(
                    ResourceKey.createRegistryKey(BuiltInRegistries.BLOCK_ENTITY_TYPE.key()),
                    DisassemblyTableMod.id("disassembly_table"),
                    BlockEntityType.Builder.of(
                            DisassemblyTableBlockEntity::new, 
                            ModBlocks.DISASSEMBLY_TABLE.get()
                    ).build(null)
            );

    public static void register() {
        DisassemblyTableMod.LOGGER.info("Registering block entities for " + DisassemblyTableMod.MOD_ID);
    }
}

