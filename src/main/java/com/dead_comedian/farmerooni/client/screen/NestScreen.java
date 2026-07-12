package com.dead_comedian.farmerooni.client.screen;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.menu.NestMenu;
import com.dead_comedian.farmerooni.registries.FarmerooniAttachments;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class NestScreen extends AbstractContainerScreen<NestMenu> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "textures/gui/nest_gui.png");
    private static final ResourceLocation RESIDENT_SPRITE =
            ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "container/nest/resident");

    private int counteroosey=0;

    public NestScreen(NestMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }


    @Override
    protected void init() {
        super.init();
        this.titleLabelY = -11;
        this.inventoryLabelY = 74;
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - 175) / 2;
        int y = (height - 202) / 2;
        int x1 = (width - 145) / 2;
        int y2 = (height - 58) / 2;
        g.blit(TEXTURE, x, y, 0, 0, 176, 186, 176, 186);
        g.blitSprite(RESIDENT_SPRITE, 144, 13, 0, 0, x1, y2, 18 * this.getMenu().blockEntity.getData(FarmerooniAttachments.RESIDENT_COUNT), 13);
    }

}
