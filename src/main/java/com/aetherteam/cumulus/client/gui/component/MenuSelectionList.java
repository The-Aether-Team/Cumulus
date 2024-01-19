package com.aetherteam.cumulus.client.gui.component;

import com.aetherteam.cumulus.api.Menu;
import com.aetherteam.cumulus.client.gui.screen.MenuSelectionScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import java.util.List;

public class MenuSelectionList extends ObjectSelectionList<MenuSelectionList.MenuEntry> {
    private final MenuSelectionScreen parent;

    private static final int ENTRY_PADDING = 2;

    public MenuSelectionList(MenuSelectionScreen parent, int width, int height, int top, int bottom, int itemHeight) {
        super(parent.getMinecraft(), width, height, top, bottom, itemHeight);
        this.parent = parent;
        this.refreshList();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.hovered = this.isMouseOver((double)pMouseX, (double)pMouseY) ? this.getEntryAtPosition((double)pMouseX, (double)pMouseY) : null;
        boolean j1;
        if (this.renderBackground) {
            pGuiGraphics.setColor(0.125F, 0.125F, 0.125F, 1.0F);
            j1 = true;
            pGuiGraphics.blit(Screen.BACKGROUND_LOCATION, this.x0, this.y0, (float)this.x1, (float)(this.y1 + (int)this.getScrollAmount()), this.x1 - this.x0, this.y1 - this.y0, 32, 32);
            pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        }

        this.enableScissor(pGuiGraphics);
        int l1;
        int k1;
        if (this.renderHeader) {
            k1 = this.getRowLeft();
            l1 = this.y0 + 4 - (int)this.getScrollAmount();
            this.renderHeader(pGuiGraphics, k1, l1);
        }

        this.renderList(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.disableScissor();
        if (this.renderBackground) {
            j1 = true;
            pGuiGraphics.fillGradient(RenderType.guiOverlay(), this.x0, this.y0, this.x1, this.y0 + 4, -16777216, 0, 0);
            pGuiGraphics.fillGradient(RenderType.guiOverlay(), this.x0, this.y1 - 4, this.x1, this.y1, 0, -16777216, 0);
        }

        k1 = this.getMaxScroll();
        if (k1 > 0) {
            l1 = this.getScrollbarPosition();
            int k = (int)((float)((this.y1 - this.y0) * (this.y1 - this.y0)) / (float)this.getMaxPosition());
            k = Mth.clamp(k, 32, this.y1 - this.y0 - 8);
            int l = (int)this.getScrollAmount() * (this.y1 - this.y0 - k) / k1 + this.y0;
            if (l < this.y0) {
                l = this.y0;
            }

            pGuiGraphics.fill(l1, this.y0, l1 + 6, this.y1, -16777216);
            pGuiGraphics.blitSprite(SCROLLER_SPRITE, l1, l, 6, k);
        }

        this.renderDecorations(pGuiGraphics, pMouseX, pMouseY);
        RenderSystem.disableBlend();
    }

    @Override
    protected void renderSelection(GuiGraphics guiGraphics, int top, int width, int height, int outerColor, int innerColor) {
        int i = this.x0 + (this.width - width) / 2;
        int j = this.x0 + (this.width + width) / 2;
        guiGraphics.fill(i + 1, top - 3, j - 7, top + height + 1, -1);
    }

    @Override
    protected int getScrollbarPosition() {
        return (this.parent.width / 2) + (this.parent.frameWidth / 2) - 18;
    }

    @Override
    public int getRowWidth() {
        return 115;
    }

    public void refreshList() {
        this.clearEntries();
        this.parent.buildMenuList(this::addEntry, (menu) -> new MenuEntry(this.parent, menu));
    }

    public class MenuEntry extends ObjectSelectionList.Entry<MenuEntry> {
        private final MenuSelectionScreen parent;
        private final Menu menu;

        public MenuEntry(MenuSelectionScreen parent, Menu menu) {
            this.parent = parent;
            this.menu = menu;
        }

        @Override
        public Component getNarration() {
            return this.menu.getName();
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            guiGraphics.fillGradient(left, top - ENTRY_PADDING, left + MenuSelectionList.this.getRowWidth() - (ENTRY_PADDING * 2) - 6, top + MenuSelectionList.this.itemHeight - (ENTRY_PADDING * 2), -10066330, -8750470);
            poseStack.popPose();
            RenderSystem.setShaderColor(1, 1, 1, 1);
            poseStack.pushPose();
            guiGraphics.blit(this.menu.getIcon(), left + ENTRY_PADDING + 1, top + 1, 0, 0, 16, 16, 16, 16);
            poseStack.popPose();

            Font font = this.parent.getFontRenderer();
            int fontWidth = MenuSelectionList.this.getRowWidth() - (ENTRY_PADDING * 2) - 24;
            List<FormattedCharSequence> lines = font.split(this.menu.getName(), fontWidth);


            int length = 1;
            for (FormattedCharSequence line : lines) {
                int y = top + (length * 10) - ((lines.size() * 10) / 2);
                guiGraphics.drawString(font, line, left + ENTRY_PADDING + 21, y, 0xFFFFFF);
                length++;
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            this.parent.setSelected(this);
            MenuSelectionList.this.setSelected(this);
            return false;
        }

        public Menu getMenu() {
            return this.menu;
        }
    }
}
