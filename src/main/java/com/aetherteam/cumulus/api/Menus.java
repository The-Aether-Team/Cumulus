package com.aetherteam.cumulus.api;

import com.aetherteam.cumulus.Cumulus;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

public class Menus {
    public static final LazyRegistrar<Menu> MENUS = LazyRegistrar.create(Cumulus.MENU_REGISTRY_KEY, Cumulus.MODID);

    public static final ResourceLocation MINECRAFT_ICON = new ResourceLocation("textures/block/grass_block_side.png");
    public static final Component MINECRAFT_NAME = Component.translatable("cumulus_menus.menu_title.minecraft");
    public static final BooleanSupplier MINECRAFT_CONDITION = () -> true;

    public static final RegistryObject<Menu> MINECRAFT = MENUS.register("minecraft", () -> new Menu(MINECRAFT_ICON, MINECRAFT_NAME, new TitleScreen(true), MINECRAFT_CONDITION));

    @Nullable
    public static Menu get(String id) {
        return Cumulus.MENU_REGISTRY.get(new ResourceLocation(id));
    }

    /**
     * @return A {@link List} of all registered {@link Menu}s.
     */
    public static List<Menu> getMenus() {
        return Cumulus.MENU_REGISTRY.stream().toList();
    }

    /**
     * @return A {@link List} of all {@link Menu}s' {@link Screen}s.
     */
    public static List<Screen> getMenuScreens() {
        return getMenus().stream().map(Menu::getScreen).collect(Collectors.toList());
    }
}
