package com.example.disassemblytable.inventory;

import com.example.disassemblytable.block.entity.DisassemblyTableBlockEntity;
import com.example.disassemblytable.registry.ModBlocks;
import com.example.disassemblytable.registry.ModMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DisassemblyTableMenu extends AbstractContainerMenu {
    private final DisassemblyTableBlockEntity blockEntity;
    private final ContainerData data;

    // Client constructor
    public DisassemblyTableMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, null, new SimpleContainerData(2));
    }

    // Server constructor
    public DisassemblyTableMenu(int containerId, Inventory playerInventory, DisassemblyTableBlockEntity entity, ContainerData data) {
        super(ModMenuTypes.DISASSEMBLY_TABLE.get(), containerId);
        this.blockEntity = entity;
        this.data = data;

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        // Input slot
        this.addSlot(new Slot(new DisassemblyTableSlotWrapper(entity, 0), 0, 80, 18) {
            @Override
            public void setChanged() {
                super.setChanged();
                // Slot change is handled in the BlockEntity's setItem method
            }
        });

        // Output slots (3x3 grid)
        int outputStartX = 44;
        int outputStartY = 54;
        int slotIndex = 1;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.addSlot(new OutputSlot(new DisassemblyTableSlotWrapper(entity, slotIndex), slotIndex - 1, 
                        outputStartX + col * 18, outputStartY + row * 18));
                slotIndex++;
            }
        }

        addDataSlots(data);
    }

    // No progress needed as disassembly is instant
    public boolean isReady() {
        return true;
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 10;  // 1 input + 9 output slots

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }
        
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity == null || blockEntity.stillValid(player);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 123 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 181));
        }
    }

    // Output slot that can't accept items
    private static class OutputSlot extends Slot {
        public OutputSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }
    }
}

