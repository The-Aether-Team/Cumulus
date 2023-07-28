package com.aetherteam.cumulus.api;

import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.TitleScreenAccessor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.sounds.Music;

import javax.annotation.Nullable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class MenuHelper {
    @Nullable
    private Menu activeMenu = null;
    @Nullable
    private TitleScreen fallbackTitleScreen = null;
    @Nullable
    private Menu.Background fallbackBackground = null;
    @Nullable
    private String lastSplash = null;
    private boolean shouldFade = true;

    public MenuHelper() { }

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
    public void setActiveMenu(Menu activeMenu) {
        this.activeMenu = activeMenu;
    }

    /**
     * Prepares a menu for application by marking it active if the condition is true.
     * @param menu The {@link Menu} to set active.
     */
    public void prepareMenu(Menu menu) {
        if (menu.getCondition().getAsBoolean()) {
            this.setActiveMenu(menu);
        }
    }

    /**
     * Handles all the menu application behavior.
     * @param menu The {@link Menu} to apply.
     * @return The {@link TitleScreen} corresponding to the applied menu.
     */
    public TitleScreen applyMenu(Menu menu) {
        if (CumulusConfig.CLIENT.enable_menu_api.get()) {
            TitleScreen screen = this.checkFallbackScreen(menu, menu.getScreen());
            if (this.shouldFade()) {
                TitleScreenAccessor defaultMenuAccessor = (TitleScreenAccessor) screen;
                defaultMenuAccessor.cumulus$setFading(true);
                defaultMenuAccessor.cumulus$setFadeInStart(0L);
            }
            Menu.Background background = this.checkFallbackBackground(menu, screen, menu.getBackground());
            this.applyBackgrounds(background);
            if (this.getLastSplash() != null) {
                this.migrateSplash(this.getLastSplash(), screen);
            }
            menu.getApply().run();
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
        if ((screen.getClass() == TitleScreen.class || menu == Menus.MINECRAFT.get()) && this.getFallbackTitleScreen() != null) {
            screen = this.getFallbackTitleScreen();
        }
        return screen;
    }

    /**
     * Checks if there is a fallback {@link Menu.Background} to apply. This is used if another mod has custom dirt backgrounds.
     * @param menu The current {@link Menu}.
     * @param screen The current {@link Screen}.
     * @param background The current {@link Menu.Background}.
     * @return The fallback {@link Menu.Background}.
     */
    private Menu.Background checkFallbackBackground(Menu menu, TitleScreen screen, Menu.Background background) {
        if ((screen.getClass() == TitleScreen.class || menu == Menus.MINECRAFT.get()) && this.getFallbackBackground() != null) {
            background = this.getFallbackBackground();
        }
        return background;
    }

    /**
     * Resets the active menu to the default Minecraft menu.
     */
    public void clearActiveMenu() {
        this.activeMenu = Menus.MINECRAFT.get();
    }

    /**
     * @return The active menu's {@link TitleScreen}.
     */
    public TitleScreen getActiveScreen() {
        return this.getActiveMenu() != null ? this.getActiveMenu().getScreen() : null;
    }

    /**
     * @return The active menu's {@link Music}.
     */
    public Music getActiveMusic() {
        return this.getActiveMenu() != null ? this.getActiveMenu().getMusic() : null;
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
    public void setFallbackTitleScreen(TitleScreen fallbackTitleScreen) {
        this.fallbackTitleScreen = fallbackTitleScreen;
    }

    /**
     * @return The fallback {@link Menu.Background}.
     */
    @Nullable
    public Menu.Background getFallbackBackground() {
        return this.fallbackBackground;
    }

    /**
     * Sets the fallback {@link Menu.Background}.
     * @param fallbackBackground The {@link Menu.Background}.
     */
    public void setFallbackBackground(Menu.Background fallbackBackground) {
        this.fallbackBackground = fallbackBackground;
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
    public void setLastSplash(String lastSplash) {
        this.lastSplash = lastSplash;
    }

    /**
     * Migrates a splash message between screens.
     * @param originalSplash The original splash {@link String} to transfer to a new screen.
     * @param newScreen The new {@link TitleScreen} to get the splash.
     */
    public void migrateSplash(String originalSplash, TitleScreen newScreen) {
        TitleScreenAccessor newScreenAccessor = (TitleScreenAccessor) newScreen;
        newScreenAccessor.cumulus$setSplash(originalSplash);
    }

    /**
     * Sets a custom splash message under special conditions.
     * @param screen The {@link TitleScreen}.
     * @param condition The {@link Calendar} {@link Predicate} for when to display the splash.
     * @param splash The {@link String} for the splash to display.
     */
    public void setCustomSplash(TitleScreen screen, Predicate<Calendar> condition, String splash) {
        TitleScreenAccessor screenAccessor = (TitleScreenAccessor) screen;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (condition.test(calendar)) {
            screenAccessor.cumulus$setSplash(splash);
        }
    }

    /**
     * Applies a background.
     * @param background The {@link com.aetherteam.cumulus.api.Menu.Background}.
     */
    public void applyBackgrounds(Menu.Background background) {
        Menu.Background.apply(background);
    }

    /**
     * Resets the background.
     */
    public void resetBackgrounds() {
        Menu.Background.reset();
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

