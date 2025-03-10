package com.example.disassemblytable.block.entity;

import com.example.disassemblytable.inventory.DisassemblyTableMenu;
import com.example.disassemblytable.platform.Services;
import com.example.disassemblytable.registry.ModBlockEntities;
import com.example.disassemblytable.util.RecipeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DisassemblyTableBlockEntity extends BlockEntity implements MenuProvider {
    private final NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY);

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return 0; // No progress needed as disassembly is instant
        }

        @Override
        public void set(int index, int value) {
            // No-op as disassembly is instant
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public DisassemblyTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DISASSEMBLY_TABLE.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.disassemblytable.disassembly_table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new DisassemblyTableMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, items);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, items);
    }

    public void tick() {
        if (level == null || level.isClientSide) return;
        
        // Try to disassemble immediately when an item is placed
        if (!items.get(0).isEmpty()) {
            disassembleItem();
        }
    }

    private void disassembleItem() {
        if (level == null) return;
        
        ItemStack inputStack = items.get(0);
        if (inputStack.isEmpty()) return;
        
        // Check for enchantments first
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(inputStack);
        List<ItemStack> ingredients = RecipeUtil.getDisassemblyIngredients(level, inputStack);
        
        // Calculate how many output slots we need
        int requiredSlots = ingredients.size() + (enchantments.isEmpty() ? 0 : enchantments.size());
        
        // Check if there's enough space in output slots
        if (!hasEnoughSpaceForOutputs(ingredients, enchantments)) {
            return;
        }
        
        // Remove the input item
        items.get(0).shrink(1);
        
        // Add the ingredients to the output slots
        int slotIndex = 1;
        
        // Add base ingredients
        for (ItemStack ingredient : ingredients) {
            if (slotIndex > 9) break; // Safety check
            
            ItemStack outputSlot = items.get(slotIndex);
            if (outputSlot.isEmpty()) {
                items.set(slotIndex, ingredient.copy());
            } else if (ItemStack.isSameItemSameTags(outputSlot, ingredient)) {
                outputSlot.grow(ingredient.getCount());
            }
            slotIndex++;
        }
        
        // Add enchanted books if there are enchantments
        if (!enchantments.isEmpty()) {
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                if (slotIndex > 9) break; // Safety check
                
                ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
                EnchantmentHelper.setEnchantments(Map.of(entry.getKey(), entry.getValue()), enchantedBook);
                
                items.set(slotIndex, enchantedBook);
                slotIndex++;
            }
        }
        
        setChanged();
    }
    
    private boolean hasEnoughSpaceForOutputs(List<ItemStack> ingredients, Map<Enchantment, Integer> enchantments) {
        List<ItemStack> allOutputs = new ArrayList<>(ingredients);
        
        // Add placeholder for enchanted books
        for (int i = 0; i < enchantments.size(); i++) {
            allOutputs.add(new ItemStack(Items.ENCHANTED_BOOK));
        }
        
        if (allOutputs.size() > 9) {
            return false; // Too many outputs
        }
        
        // Check if existing items in output slots are compatible
        int slotIndex = 1;
        for (ItemStack output : allOutputs) {
            ItemStack existingItem = items.get(slotIndex);
            
            if (!existingItem.isEmpty() && 
                (!ItemStack.isSameItemSameTags(existingItem, output) || 
                 existingItem.getCount() + output.getCount() > existingItem.getMaxStackSize())) {
                return false;
            }
            
            slotIndex++;
        }
        
        return true;
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        
        // Trigger immediate disassembly when an item is placed in the input slot
        if (slot == 0 && !stack.isEmpty() && level != null && !level.isClientSide) {
            disassembleItem();
        }
    }

    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    public ItemStack removeItem(int slot, int amount) {
        ItemStack result = ContainerHelper.removeItem(items, slot, amount);
        if (!result.isEmpty()) {
            setChanged();
        }
        return result;
    }

    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    public int getMaxStackSize() {
        return 64;
    }

    public boolean stillValid(Player player) {
        if (level == null || level.getBlockEntity(worldPosition) != this) {
            return false;
        }
        return player.distanceToSqr(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    public void clearContent() {
        items.clear();
    }

    public void dropContents() {
        if (level != null) {
            Services.PLATFORM.dropContents(level, worldPosition, items);
        }
    }
}

