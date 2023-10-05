package com.aetherteam.cumulus.client.event.listeners;

import com.aetherteam.cumulus.api.MenuHelper;
import com.aetherteam.cumulus.client.CumulusClient;
import com.aetherteam.cumulus.client.event.hooks.MenuHooks;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class MenuListener {
    public static void init() {
        ScreenEvents.BEFORE_INIT.register(MenuListener::onGuiDraw);
        ScreenEvents.AFTER_INIT.register(MenuListener::onGuiInitialize);
    }

    /**
     * @see MenuHooks#prepareCustomMenus(MenuHelper)
     * @see MenuHooks#refreshBackgrounds(Screen, MenuHelper)
     * @see com.aetherteam.cumulus.mixin.mixins.client.MinecraftMixinHighest#setScreen(Screen, CallbackInfo, LocalRef).
     */
    public static void onGuiOpenHighest(Screen newScreen) {
        MenuHooks.prepareCustomMenus(CumulusClient.MENU_HELPER);
        MenuHooks.refreshBackgrounds(newScreen, CumulusClient.MENU_HELPER);
    }

    /**
     * @see MenuHooks#setLastSplash(Screen, MenuHelper)
     * @see MenuHooks#trackFallbacks(Screen)
     * @see com.aetherteam.cumulus.mixin.mixins.client.MinecraftMixinLow#setScreen(Screen)
     */
    @Nullable
    public static Screen onGuiOpenLow(Screen screen) {
        MenuHooks.setLastSplash(screen, CumulusClient.MENU_HELPER);
        MenuHooks.trackFallbacks(screen);
        return MenuHooks.setupCustomMenu(screen, CumulusClient.MENU_HELPER);
    }

    /**
     * @see MenuHooks#resetFade(MenuHelper)
     */
    public static void onGuiDraw(Minecraft client, Screen screen, int scaledWidth, int scaledHeight) {
        ScreenEvents.afterRender(screen).register((renderScreen, matrices, mouseX, mouseY, tickDelta) -> MenuHooks.resetFade(CumulusClient.MENU_HELPER));
    }

    /**
     * @see MenuHooks#setupMenuScreenButton(Screen)
     */
    public static void onGuiInitialize(Minecraft client, Screen screen, int scaledWidth, int scaledHeight) {
        if (screen instanceof TitleScreen) {
            Button menuSwitchButton = MenuHooks.setupMenuScreenButton(screen);
            if (menuSwitchButton != null) {
                Screens.getButtons(screen).add(menuSwitchButton);
            }
        }
    }
}
