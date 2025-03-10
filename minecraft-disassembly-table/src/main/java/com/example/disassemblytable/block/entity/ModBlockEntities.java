package com.example.disassemblytable.block.entity;

import com.example.disassemblytable.DisassemblyTableMod;
import com.example.disassemblytable.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DisassemblyTableMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<DisassemblyTableBlockEntity>> DISASSEMBLY_TABLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("disassembly_table_block_entity", () ->
                    BlockEntityType.Builder.of(DisassemblyTableBlockEntity::new,
                            ModBlocks.DISASSEMBLY_TABLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}

