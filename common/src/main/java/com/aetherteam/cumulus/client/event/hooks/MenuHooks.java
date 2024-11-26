package com.aetherteam.cumulus.client.event.hooks;

import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.api.MenuHelper;
import com.aetherteam.cumulus.api.Menus;
import com.aetherteam.cumulus.client.CumulusClient;
import com.aetherteam.cumulus.client.gui.screen.MenuSelectionScreen;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.SplashRendererAccessor;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.TitleScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class MenuHooks {
    /**
     * Sets the last splash for the {@link MenuHelper}.
     * @param screen The {@link Screen} to take the splash from.
     * @param menuHelper The {@link MenuHelper}.
     */
    public static void setLastSplash(Screen screen, MenuHelper menuHelper) {
        if (screen instanceof TitleScreen titleScreen) {
            TitleScreenAccessor screenAccessor = (TitleScreenAccessor) titleScreen;
            SplashRendererAccessor splashRendererAccessor = (SplashRendererAccessor) screenAccessor.cumulus$getSplash();
            if (splashRendererAccessor != null) {
                menuHelper.setLastSplash(splashRendererAccessor.cumulus$getSplash());
            }
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
        if (screen instanceof TitleScreen && CumulusConfig.CLIENT.enable_menu_api.get()) {
            return menuHelper.applyMenu(Menus.get(ResourceLocation.parse(CumulusConfig.CLIENT.active_menu.get())));
        }
        return null;
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
