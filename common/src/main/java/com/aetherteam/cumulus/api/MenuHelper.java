package com.aetherteam.cumulus.api;

import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.ScreenAccessor;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.SplashRendererAccessor;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.TitleScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.sounds.Music;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class MenuHelper {
    @Nullable
    private Menu activeMenu = Menus.MINECRAFT;
    @Nullable
    private TitleScreen fallbackTitleScreen = null;
    @Nullable
    private String lastSplash = null;
    private boolean shouldFade = true;

    /**
     * @return The currently active and displaying {@link Menu}.
     */
    @Nullable
    public Menu getActiveMenu() {
        return this.activeMenu;
    }

    /**
     * Sets the active and displaying {@link Menu}.
     * @param activeMenu The {@link Menu}.
     */
    public void setActiveMenu(@Nullable Menu activeMenu) {
        this.activeMenu = activeMenu;
    }

    /**
     * Handles all the menu application behavior.
     * @param menu The {@link Menu} to apply.
     * @return The {@link TitleScreen} corresponding to the applied menu.
     */
    @Nullable
    public TitleScreen applyMenu(Menu menu) {
        if (CumulusConfig.CLIENT.enable_menu_api.get()) {
            this.setActiveMenu(menu);
            TitleScreen screen = this.checkFallbackScreen(menu, menu.screen());
            if (this.shouldFade()) {
                TitleScreenAccessor defaultMenuAccessor = (TitleScreenAccessor) screen;
                defaultMenuAccessor.cumulus$setFading(true);
                defaultMenuAccessor.cumulus$setFadeInStart(0L);
            }
            ScreenAccessor.cumulus$setCubeMap(menu.panorama());
            ScreenAccessor.cumulus$setPanorama(new PanoramaRenderer(menu.panorama()));
            if (this.getLastSplash() != null) {
                this.migrateSplash(this.getLastSplash(), screen);
            }
            menu.apply().run();
            return screen;
        }
        return this.getFallbackTitleScreen();
    }

    /**
     * Checks if there is a fallback {@link TitleScreen} to apply. This is used if another mod has a custom {@link TitleScreen}.
     * @param menu The current {@link Menu}.
     * @param screen The current {@link Screen}.
     * @return The fallback {@link TitleScreen}.
     */
    private TitleScreen checkFallbackScreen(Menu menu, TitleScreen screen) {
        if ((screen.getClass() == TitleScreen.class || menu == Menus.MINECRAFT) && this.getFallbackTitleScreen() != null) {
            screen = this.getFallbackTitleScreen();
        }
        return screen;
    }

    /**
     * Resets the active menu to the default Minecraft menu.
     */
    public void clearActiveMenu() {
        this.activeMenu = Menus.MINECRAFT;
    }

    /**
     * @return The active menu's {@link TitleScreen}.
     */
    @Nullable
    public TitleScreen getActiveScreen() {
        return this.getActiveMenu() != null ? this.getActiveMenu().screen() : null;
    }

    /**
     * @return The active menu's {@link Music}.
     */
    @Nullable
    public Music getActiveMusic() {
        return this.getActiveMenu() != null ? this.getActiveMenu().music() : null;
    }

    /**
     * @return The fallback {@link TitleScreen} for other mods' screens.
     */
    @Nullable
    public TitleScreen getFallbackTitleScreen() {
        return this.fallbackTitleScreen;
    }

    /**
     * Sets the fallback {@link TitleScreen}.
     * @param fallbackTitleScreen The {@link TitleScreen}.
     */
    public void setFallbackTitleScreen(@Nullable TitleScreen fallbackTitleScreen) {
        this.fallbackTitleScreen = fallbackTitleScreen;
    }

    /**
     * @return The {@link String} for the last displayed splash.
     */
    @Nullable
    public String getLastSplash() {
        return this.lastSplash;
    }

    /**
     * Sets the last displayed splash.
     * @param lastSplash The splash {@link String}.
     */
    public void setLastSplash(@Nullable String lastSplash) {
        this.lastSplash = lastSplash;
    }

    /**
     * Migrates a splash message between screens.
     * @param originalSplash The original splash {@link String} to transfer to a new screen.
     * @param newScreen The new {@link TitleScreen} to get the splash.
     */
    public void migrateSplash(String originalSplash, TitleScreen newScreen) {
        TitleScreenAccessor newScreenAccessor = (TitleScreenAccessor) newScreen;
        if (newScreenAccessor.cumulus$getSplash() == null) {
            newScreenAccessor.setSplash(Minecraft.getInstance().getSplashManager().getSplash());
        }
        SplashRendererAccessor splashRendererAccessor = (SplashRendererAccessor) newScreenAccessor.cumulus$getSplash();
        splashRendererAccessor.cumulus$setSplash(originalSplash);
    }

    /**
     * Sets a custom splash message under special conditions.
     * @param screen The {@link TitleScreen}.
     * @param condition The {@link Calendar} {@link Predicate} for when to display the splash.
     * @param splash The {@link String} for the splash to display.
     */
    public void setCustomSplash(TitleScreen screen, Predicate<Calendar> condition, String splash) {
        TitleScreenAccessor screenAccessor = (TitleScreenAccessor) screen;
        SplashRendererAccessor splashRendererAccessor = (SplashRendererAccessor) screenAccessor.cumulus$getSplash();
        if (splashRendererAccessor != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            if (condition.test(calendar)) {
                splashRendererAccessor.cumulus$setSplash(splash);
            }
        }
    }

    /**
     * Checks if a screen class matches an existing menu.
     * @param titleScreen The {@link TitleScreen}.
     * @return Whether the screen matches, as a {@link Boolean}.
     */
    public boolean doesScreenMatchMenu(TitleScreen titleScreen) {
        boolean matches = false;
        List<Screen> menuScreens = Menus.getMenuScreens();
        for (Screen screen : menuScreens) {
            if (titleScreen.getClass().equals(screen.getClass())) {
                matches = true;
                break;
            }
        }
        return matches;
    }

    /**
     * @return Whether the menu should fade in when opened, as a {@link Boolean}.
     */
    public boolean shouldFade() {
        return this.shouldFade;
    }

    /**
     * Sets whether the menu should fade in when opened.
     * @param shouldFade The {@link Boolean} value.
     */
    public void setShouldFade(boolean shouldFade) {
        this.shouldFade = shouldFade;
    }
}

