package com.aetherteam.cumulus.client.event.hooks;

import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.api.Menu;
import com.aetherteam.cumulus.api.MenuHelper;
import com.aetherteam.cumulus.api.Menus;
import com.aetherteam.cumulus.client.CumulusClient;
import com.aetherteam.cumulus.client.gui.screen.MenuSelectionScreen;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.TabButtonAccessor;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.TitleScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;

public class MenuHooks {
    public static void setLastSplash(Screen screen, MenuHelper menuHelper) {
        if (screen instanceof TitleScreen titleScreen) {
            menuHelper.setLastSplash(((TitleScreenAccessor) titleScreen).cumulus$getSplash());
        }
    }

    public static void prepareCustomMenus(MenuHelper menuHelper) {
        menuHelper.prepareMenu(Menus.MINECRAFT.get());
    }

    public static void refreshBackgrounds(Screen screen, MenuHelper menuHelper) {
        if (CumulusConfig.CLIENT.enable_menu_api.get() && screen instanceof TitleScreen) {
            menuHelper.resetBackgrounds();
        }
    }

    public static void trackFallbacks(Screen screen) {
        if (screen instanceof TitleScreen titleScreen) {
            if (CumulusConfig.CLIENT.enable_menu_api.get()) {
                if (!CumulusClient.MENU_HELPER.doesScreenMatchMenu(titleScreen) || screen.getClass() == TitleScreen.class) {
                    CumulusClient.MENU_HELPER.setFallbackTitleScreen(titleScreen);
                    CumulusClient.MENU_HELPER.setFallbackBackground(new Menu.Background().regularBackground(GuiComponent.BACKGROUND_LOCATION).darkBackground(GuiComponent.LIGHT_DIRT_BACKGROUND).headerSeparator(CreateWorldScreen.HEADER_SEPERATOR).footerSeparator(CreateWorldScreen.FOOTER_SEPERATOR).tabButton(TabButtonAccessor.cumulus$getTextureLocation()));
                }
            } else if (screen.getClass() == TitleScreen.class) {
                CumulusClient.MENU_HELPER.setFallbackTitleScreen(titleScreen);
            }
        }
    }

    public static Screen setupCustomMenu(Screen screen, MenuHelper menuHelper) {
        if (CumulusConfig.CLIENT.enable_menu_api.get() && screen instanceof TitleScreen) {
            return menuHelper.applyMenu(menuHelper.getActiveMenu());
        }
        return screen;
    }

    public static void resetFade(MenuHelper menuHelper) {
        menuHelper.setShouldFade(false);
    }

    public static Button setupMenuScreenButton(Screen screen) {
        if (CumulusConfig.CLIENT.enable_menu_api.get() && CumulusConfig.CLIENT.enable_menu_list_button.get() && screen instanceof TitleScreen) {
            return Button.builder(Component.translatable("gui.cumulus_menus.button.menu_list"), (pressed) -> Minecraft.getInstance().setScreen(new MenuSelectionScreen(screen))).bounds(screen.width - 62, 4, 58, 20).build();
        }
        return null;
    }
}
