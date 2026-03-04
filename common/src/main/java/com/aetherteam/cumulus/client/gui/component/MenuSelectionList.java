package com.aetherteam.cumulus.client.gui.component;

import com.aetherteam.cumulus.api.Menu;
import com.aetherteam.cumulus.client.gui.screen.MenuSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class MenuSelectionList extends ObjectSelectionList<MenuSelectionList.MenuEntry> {
    private final MenuSelectionScreen parent;

    private static final int ENTRY_PADDING = 2;

    public MenuSelectionList(MenuSelectionScreen parent, int width, int height, int y, int itemHeight) {
        super(Minecraft.getInstance(), width, height, y, itemHeight);
        this.parent = parent;
        this.refreshList();
    }

    @Override
    protected void renderSelection(GuiGraphics guiGraphics, MenuEntry entry, int backgroundColor) {
        int i = this.getX() + (this.width - width) / 2;
        int j = this.getX() + (this.width + width) / 2;
        guiGraphics.fill(i + 1, entry.getY() - 3, j - 7, entry.getY() + this.height + 1, -1);
    }

    @Override
    protected void renderListBackground(GuiGraphics guiGraphics) { }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) { }

    @Override
    protected int scrollBarX() {
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

    public class MenuEntry extends Entry<MenuEntry> {
        private final MenuSelectionScreen parent;
        private final Menu menu;

        public MenuEntry(MenuSelectionScreen parent, Menu menu) {
            this.parent = parent;
            this.menu = menu;
        }

        @Override
        public Component getNarration() {
            return this.menu.name();
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            guiGraphics.fillGradient(this.getX(), this.getY() - ENTRY_PADDING, this.getX() + MenuSelectionList.this.getRowWidth() - (ENTRY_PADDING * 2) - 6, this.getY() + MenuSelectionList.this.contentHeight() - (ENTRY_PADDING * 2), -10066330, -8750470);
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.menu.icon(), this.getX() + ENTRY_PADDING + 1, this.getY() + 1, 0, 0, 16, 16, 16, 16);

            Font font = this.parent.getFontRenderer();
            int fontWidth = MenuSelectionList.this.getRowWidth() - (ENTRY_PADDING * 2) - 24;
            List<FormattedCharSequence> lines = font.split(this.menu.name(), fontWidth);

            int length = 1;
            for (FormattedCharSequence line : lines) {
                int y = this.getY() + (length * 10) - ((lines.size() * 10) / 2);
                guiGraphics.drawString(font, line, this.getX() + ENTRY_PADDING + 21, y, 0xFFFFFFFF);
                length++;
            }
        }

        @Override
        public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
            this.parent.setSelected(this);
            MenuSelectionList.this.setSelected(this);
            return false;
        }

        public Menu getMenu() {
            return this.menu;
        }
    }
}
