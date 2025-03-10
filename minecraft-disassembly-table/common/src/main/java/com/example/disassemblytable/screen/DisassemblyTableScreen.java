package com.example.disassemblytable.screen;

import com.example.disassemblytable.DisassemblyTableMod;
import com.example.disassemblytable.inventory.DisassemblyTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DisassemblyTableScreen extends AbstractContainerScreen<DisassemblyTableMenu> {
    private static final ResourceLocation TEXTURE = DisassemblyTableMod.id("textures/gui/disassembly_table_gui.png");

    public DisassemblyTableScreen(DisassemblyTableMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        
        // No progress bar needed as disassembly is instant
        // Instead, we can add a visual indicator that the table is ready
        graphics.blit(TEXTURE, x + 80, y + 38, 176, 17, 16, 16);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        renderTooltip(graphics, mouseX, mouseY);
    }
}

