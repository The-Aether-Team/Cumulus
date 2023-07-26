package com.aetherteam.cumulus.client.event.listeners;

import com.aetherteam.cumulus.Cumulus;
import com.aetherteam.cumulus.client.CumulusClient;
import com.aetherteam.cumulus.client.event.hooks.MenuHooks;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cumulus.MODID, value = Dist.CLIENT)
public class MenuListener {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onGuiOpenHighest(ScreenEvent.Opening event) {
        Screen newScreen = event.getNewScreen();
        MenuHooks.prepareCustomMenus(CumulusClient.MENU_HELPER);
        MenuHooks.refreshBackgrounds(newScreen, CumulusClient.MENU_HELPER);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onGuiOpenLow(ScreenEvent.Opening event) {
        Screen screen = event.getScreen();
        Screen newScreen = event.getNewScreen();
        MenuHooks.setLastSplash(screen, CumulusClient.MENU_HELPER);
        MenuHooks.trackFallbacks(newScreen);
        Screen titleScreen = MenuHooks.setupCustomMenu(screen, CumulusClient.MENU_HELPER);
        if (titleScreen != null) {
            event.setNewScreen(titleScreen);
        }
    }

    @SubscribeEvent
    public static void onGuiDraw(ScreenEvent.Render event) {
        MenuHooks.resetFade(CumulusClient.MENU_HELPER);
    }

    @SubscribeEvent
    public static void onGuiInitialize(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof TitleScreen) {
            Button menuSwitchButton = MenuHooks.setupMenuScreenButton(screen);
            if (menuSwitchButton != null) {
                event.addListener(menuSwitchButton);
            }
        }
    }
}
