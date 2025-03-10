package com.example.disassemblytable.inventory;

import com.example.disassemblytable.block.entity.DisassemblyTableBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DisassemblyTableSlotWrapper implements Container {
    private final DisassemblyTableBlockEntity blockEntity;
    private final int slot;

    public DisassemblyTableSlotWrapper(DisassemblyTableBlockEntity blockEntity, int slot) {
        this.blockEntity = blockEntity;
        this.slot = slot;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return blockEntity == null || blockEntity.getItem(slot).isEmpty();
    }

    @Override
    public ItemStack getItem(int index) {
        return blockEntity == null ? ItemStack.EMPTY : blockEntity.getItem(slot);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return blockEntity == null ? ItemStack.EMPTY : blockEntity.removeItem(slot, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return blockEntity == null ? ItemStack.EMPTY : blockEntity.removeItemNoUpdate(slot);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        if (blockEntity != null) {
            blockEntity.setItem(slot, stack);
        }
    }

    @Override
    public void setChanged() {
        if (blockEntity != null) {
            blockEntity.setChanged();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity == null || blockEntity.stillValid(player);
    }

    @Override
    public void clearContent() {
        if (blockEntity != null) {
            blockEntity.setItem(slot, ItemStack.EMPTY);
        }
    }
}

