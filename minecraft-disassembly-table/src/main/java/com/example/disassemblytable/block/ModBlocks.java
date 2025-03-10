package com.example.disassemblytable.block;

import com.example.disassemblytable.DisassemblyTableMod;
import com.example.disassemblytable.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DisassemblyTableMod.MOD_ID);

    public static final RegistryObject<Block> DISASSEMBLY_TABLE = registerBlock("disassembly_table",
            () -> new DisassemblyTableBlock(BlockBehaviour.Properties.of(Material.WOOD)
                    .strength(2.5f)
                    .sound(SoundType.WOOD)
                    .requiresCorrectToolForDrops()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}

