package com.aetherteam.cumulus.client.gui.screen;

import com.aetherteam.cumulus.Cumulus;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.api.Menu;
import com.aetherteam.cumulus.api.Menus;
import com.aetherteam.cumulus.client.CumulusClient;
import com.aetherteam.cumulus.client.gui.component.MenuSelectionList;
import com.aetherteam.cumulus.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class MenuSelectionScreen extends Screen {
    public static final ResourceLocation LIST_FRAME = ResourceLocation.fromNamespaceAndPath(Cumulus.MODID, "textures/gui/menu_api/list.png");
    private static final int EXTERIOR_WIDTH_PADDING = 13;
    private static final int EXTERIOR_TOP_PADDING = 28;
    private static final int EXTERIOR_BOTTOM_PADDING = 33;

    public final int frameWidth = 141;
    public final int frameHeight = 168;

    private final List<Menu> menus;
    private final Screen parentScreen;
    private MenuSelectionList menuList;
    private Button launchButton;
    @Nullable
    private MenuSelectionList.MenuEntry selected = null;

    private boolean setup = false;

    public MenuSelectionScreen(Screen parentScreen) {
        super(Component.literal(""));
        this.parentScreen = parentScreen;
        this.menus = Menus.getMenus();
    }

    @Override
    public void init() {
        this.menuList = new MenuSelectionList(this, this.frameWidth - (EXTERIOR_WIDTH_PADDING * 2), this.frameHeight, (this.height / 2) - (this.frameHeight / 2) + EXTERIOR_TOP_PADDING, 24);
        this.menuList.setX((this.width / 2) - (this.frameWidth / 2) + EXTERIOR_WIDTH_PADDING);
        this.addRenderableWidget(this.menuList);

        this.launchButton = Button.builder(Component.translatable("gui.cumulus_menus.button.menu_launch"), press -> {
            if (this.selected != null) {
                CumulusConfig.CLIENT.active_menu.set(this.selected.getMenu().toString());
                CumulusConfig.CLIENT.active_menu.save();
                CumulusClient.MENU_HELPER.setShouldFade(true);
                Minecraft.getInstance().setScreen(CumulusClient.MENU_HELPER.applyMenu(this.selected.getMenu()));
                Minecraft.getInstance().getMusicManager().stopPlaying();
            }
        }).bounds((this.width / 2) - (this.frameWidth / 2) + 34, (this.height / 2) + (this.frameHeight / 2) - 27, 72, 20).build();
        this.addRenderableWidget(this.launchButton);
        this.launchButton.active = false;
    }

    @Override
    public void tick() {
        if (!this.setup) {
            this.menuList.refreshList();
            this.launchButton.active = this.selected != null;
            this.setup = true;
        }
    }

    private boolean hasRendererBackground = false;

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        this.renderListFrame(guiGraphics);
        this.menuList.render(guiGraphics, mouseX, mouseY, partialTick);

        hasRendererBackground = true;

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        hasRendererBackground = false;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (hasRendererBackground) return;
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
    }

    private void renderListFrame(GuiGraphics guiGraphics) {
        guiGraphics.blit(LIST_FRAME, (this.width / 2) - (this.frameWidth / 2), this.height / 2 - (this.frameHeight / 2), 0.0F, 0.0F, 141, 168, 256, 256);
        guiGraphics.drawCenteredString(this.getFontRenderer(), Component.translatable("gui.cumulus_menus.title.menu_selection"), this.width / 2, ((this.height / 2) - (this.frameHeight / 2)) + 11, 0xFFFFFF);
    }

    public <T extends ObjectSelectionList.Entry<T>> void buildMenuList(Consumer<T> menuListViewConsumer, Function<Menu, T> newEntry) {
        this.menus.forEach((menu) -> menuListViewConsumer.accept(newEntry.apply(menu)));
    }

    public Font getFontRenderer() {
        return this.font;
    }

    public void setSelected(MenuSelectionList.MenuEntry entry) {
        this.selected = entry == this.selected ? null : entry;
        this.launchButton.active = this.selected != null;
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parentScreen);
    }
}
