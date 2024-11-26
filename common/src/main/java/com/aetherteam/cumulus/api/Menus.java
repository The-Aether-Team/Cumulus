package com.aetherteam.cumulus.api;

import com.aetherteam.cumulus.platform.Services;
import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Menus {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceLocation MINECRAFT_ICON = ResourceLocation.withDefaultNamespace("textures/block/grass_block_side.png");
    public static final Component MINECRAFT_NAME = Component.translatable("cumulus_menus.menu_title.minecraft");
    private static Map<ResourceLocation, Menu> MENUS;
    public static Menu MINECRAFT;

    @ApiStatus.Internal
    public static void init() {
        var menus = new HashMap<ResourceLocation, Menu>();

        MINECRAFT = registerVanillaScreen(menus);

        for (var menuInitializer : Services.PLATFORM.getMenuInitializers()) {
            menuInitializer.registerMenus((location, menu) -> {
                if (menus.containsKey(location)) {
                    LOGGER.error("A Cumulus Menu [{}] was already registered meaning such will be not be registered!", location);

                    return;
                }

                menus.put(location, menu);
            });
        }

        MENUS = ImmutableMap.copyOf(menus);
    }

    private static Menu registerVanillaScreen(Map<ResourceLocation, Menu> menus) {
        var vanilla = new Menu(MINECRAFT_ICON, MINECRAFT_NAME, new TitleScreen(true));
        menus.put(ResourceLocation.withDefaultNamespace("minecraft"), vanilla);
        return vanilla;
    }

    @Nullable
    public static ResourceLocation getKey(Menu menu) {
        for (var entry : MENUS.entrySet()) {
            if (entry.getValue().equals(menu)) return entry.getKey();
        }
        return null;
    }

    public static Menu get(ResourceLocation type) {
        return MENUS.getOrDefault(type, MINECRAFT);
    }

    /**
     * @return A {@link List} of all registered {@link Menu}s.
     */
    public static List<Menu> getMenus() {
        return List.copyOf(MENUS.values());
    }

    /**
     * @return A {@link List} of all {@link Menu}s' {@link Screen}s.
     */
    public static List<Screen> getMenuScreens() {
        return getMenus().stream().map(Menu::screen).collect(Collectors.toList());
    }
}
