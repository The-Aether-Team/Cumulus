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

import javax.annotation.Nullable;

public class MenuHooks {
    /**
     * Sets the last splash for the {@link MenuHelper}.
     * @param screen The {@link Screen} to take the splash from.
     * @param menuHelper The {@link MenuHelper}.
     */
    public static void setLastSplash(Screen screen, MenuHelper menuHelper) {
        if (screen instanceof TitleScreen titleScreen) {
            menuHelper.setLastSplash(((TitleScreenAccessor) titleScreen).cumulus$getSplash());
        }
    }

    /**
     * Prepares the custom menu to apply.
     * @param menuHelper The {@link MenuHelper}.
     */
    public static void prepareCustomMenus(MenuHelper menuHelper) {
        menuHelper.prepareMenu(Menus.MINECRAFT.get());
    }

    /**
     * Resets the dirt backgrounds.
     * @param screen The {@link Screen}.
     * @param menuHelper The {@link MenuHelper}.
     */
    public static void refreshBackgrounds(Screen screen, MenuHelper menuHelper) {
        if (CumulusConfig.CLIENT.enable_menu_api.get() && screen instanceof TitleScreen) {
            menuHelper.resetBackgrounds();
        }
    }

    /**
     * Tracks a fallback screen and background if the current screen doesn't match a one tied to a registered menu.
     * @param screen The {@link Screen}.
     */
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

    /**
     * Applies the currently tracked active menu.
     * @param screen The {@link Screen}.
     * @param menuHelper The {@link MenuHelper}.
     * @return The {@link Screen} to set.
     */
    @Nullable
    public static Screen setupCustomMenu(Screen screen, MenuHelper menuHelper) {
        if (CumulusConfig.CLIENT.enable_menu_api.get() && screen instanceof TitleScreen) {
            return menuHelper.applyMenu(menuHelper.getActiveMenu());
        }
        return screen;
    }

    /**
     * Resets whether menus should fade in to false.
     * @param menuHelper The {@link MenuHelper}.
     */
    public static void resetFade(MenuHelper menuHelper) {
        menuHelper.setShouldFade(false);
    }

    /**
     * Sets up the button that opens the {@link MenuSelectionScreen}.
     * @param screen The current {@link Screen}.
     * @return The {@link Button}.
     */
    @Nullable
    public static Button setupMenuScreenButton(Screen screen) {
        if (CumulusConfig.CLIENT.enable_menu_api.get() && CumulusConfig.CLIENT.enable_menu_list_button.get() && screen instanceof TitleScreen) {
            return Button.builder(Component.translatable("gui.cumulus_menus.button.menu_list"), (pressed) -> Minecraft.getInstance().setScreen(new MenuSelectionScreen(screen))).bounds(screen.width - 62, 4, 58, 20).build();
        }
        return null;
    }
}
