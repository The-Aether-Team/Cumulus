package com.aetherteam.cumulus.client.event.listeners;

import com.aetherteam.cumulus.api.MenuHelper;
import com.aetherteam.cumulus.client.CumulusClient;
import com.aetherteam.cumulus.client.OpeningScreenEvents;
import com.aetherteam.cumulus.client.event.hooks.MenuHooks;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.jetbrains.annotations.Nullable;

public class MenuListener {
    /**
     * @see MenuHooks#setLastSplash(Screen, MenuHelper)
     * @see MenuHooks#trackFallbacks(Screen)
     */
    @Nullable
    public static Screen onGuiOpenLow(Screen newScreen) {
        MenuHooks.setLastSplash(newScreen, CumulusClient.MENU_HELPER);
        MenuHooks.trackFallbacks(newScreen);
        return MenuHooks.setupCustomMenu(newScreen, CumulusClient.MENU_HELPER);
    }

    /**
     * @see MenuHooks#resetFade(MenuHelper)
     */
    public static void onGuiDraw() {
        MenuHooks.resetFade(CumulusClient.MENU_HELPER);
    }

    /**
     * @see MenuHooks#setupMenuScreenButton(Screen)
     */
    public static void onGuiInitialize(Screen screen) {
        if (screen instanceof TitleScreen) {
            Button menuSwitchButton = MenuHooks.setupMenuScreenButton(screen);
            if (menuSwitchButton != null) {
                Screens.getButtons(screen).add(menuSwitchButton);
            }

            Button toggleWorldButton = MenuHooks.setupToggleWorldButton(screen);
            if (toggleWorldButton != null) {
                Screens.getButtons(screen).add(toggleWorldButton);
            }

            Button quickLoadButton = MenuHooks.setupQuickLoadButton(screen);
            if (quickLoadButton != null) {
                Screens.getButtons(screen).add(quickLoadButton);
            }
        }
    }

    public static void initEvents() {
        ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            ScreenEvents.afterRender(screen).register((screen1, drawContext, mouseX, mouseY, tickDelta) -> onGuiDraw());
        });
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> onGuiInitialize(screen));
        OpeningScreenEvents.POST.register((oldScreen, newScreen) -> onGuiOpenLow(newScreen));
    }
}
