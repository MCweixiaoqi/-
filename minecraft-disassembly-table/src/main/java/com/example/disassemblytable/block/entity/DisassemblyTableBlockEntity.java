package com.example.disassemblytable.block.entity;

import com.example.disassemblytable.container.DisassemblyTableContainer;
import com.example.disassemblytable.util.RecipeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DisassemblyTableBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(10) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 20;

    public DisassemblyTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DISASSEMBLY_TABLE_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> DisassemblyTableBlockEntity.this.progress;
                    case 1 -> DisassemblyTableBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> DisassemblyTableBlockEntity.this.progress = pValue;
                    case 1 -> DisassemblyTableBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.disassemblytable.disassembly_table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new DisassemblyTableContainer(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("disassembly_table.progress", progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("disassembly_table.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, DisassemblyTableBlockEntity entity) {
        if (level.isClientSide()) {
            return;
        }

        if (hasRecipe(entity)) {
            entity.progress++;
            setChanged(level, pos, state);

            if (entity.progress >= entity.maxProgress) {
                disassembleItem(entity);
            }
        } else {
            entity.resetProgress();
            setChanged(level, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void disassembleItem(DisassemblyTableBlockEntity entity) {
        if (hasRecipe(entity)) {
            ItemStack inputStack = entity.itemHandler.getStackInSlot(0);
            List<ItemStack> ingredients = RecipeUtil.getDisassemblyIngredients(entity.level, inputStack);

            if (!ingredients.isEmpty()) {
                // Remove the input item
                entity.itemHandler.extractItem(0, 1, false);

                // Add the ingredients to the output slots
                for (int i = 0; i < ingredients.size() && i < 9; i++) {
                    ItemStack ingredient = ingredients.get(i);
                    entity.itemHandler.insertItem(i + 1, ingredient.copy(), false);
                }
            }

            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(DisassemblyTableBlockEntity entity) {
        ItemStack inputStack = entity.itemHandler.getStackInSlot(0);
        if (inputStack.isEmpty()) {
            return false;
        }

        List<ItemStack> ingredients = RecipeUtil.getDisassemblyIngredients(entity.level, inputStack);
        if (ingredients.isEmpty()) {
            return false;
        }

        // Check if there's space in the output slots
        for (int i = 0; i < ingredients.size() && i < 9; i++) {
            ItemStack outputSlot = entity.itemHandler.getStackInSlot(i + 1);
            ItemStack ingredient = ingredients.get(i);

            if (!outputSlot.isEmpty() && 
                (!ItemStack.isSameItemSameTags(outputSlot, ingredient) || 
                 outputSlot.getCount() + ingredient.getCount() > outputSlot.getMaxStackSize())) {
                return false;
            }
        }

        return true;
    }
}

