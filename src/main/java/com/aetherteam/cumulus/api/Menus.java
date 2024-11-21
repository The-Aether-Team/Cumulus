package com.aetherteam.cumulus.api;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModLoader;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

public class Menus {

    public static final ResourceLocation MINECRAFT_ICON = ResourceLocation.withDefaultNamespace("textures/block/grass_block_side.png");
    public static final Component MINECRAFT_NAME = Component.translatable("cumulus_menus.menu_title.minecraft");
    public static final BooleanSupplier MINECRAFT_CONDITION = () -> true;
    private static Map<ResourceLocation, Menu> MENUS;
    public static Menu MINECRAFT;

    @ApiStatus.Internal
    public static void init() {
        var menus = new HashMap<ResourceLocation, Menu>();
        MINECRAFT = registerVanillaScreen(menus);
        var event = new RegisterMenuEvent(menus);
        ModLoader.postEventWrapContainerInModOrder(event);
        MENUS = ImmutableMap.copyOf(menus);
    }

    private static Menu registerVanillaScreen(Map<ResourceLocation, Menu> effects) {
        var vanilla = new Menu(MINECRAFT_ICON, MINECRAFT_NAME, new TitleScreen(true), MINECRAFT_CONDITION);
        effects.put(ResourceLocation.withDefaultNamespace("minecraft"), vanilla);
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
