package com.aetherteam.cumulus.client;

import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public interface LanguageProviderBase {

    default void addTranslations() {
        this.addMenuText("button.world_preview", "W");
        this.addMenuText("button.quick_load", "Q");

        this.addMenuText("preview", "Toggle World");
        this.addMenuText("load", "Quick Load");

        this.addGuiText("button.menu_list", "Menu List");
        this.addGuiText("button.menu_launch", "Launch Menu");
        this.addGuiText("title.menu_selection", "Choose a Main Menu");

        this.addMenuTitle("minecraft", "Minecraft");


        this.addConfig("title", "Cumulus Configuration");
        this.addConfig("section.cumulus.menus.client.toml", "Client Settings");
        this.addConfig("section.cumulus.menus.client.toml.title", "Cumulus Client Configuration");

        this.addConfig("Menu", "Menu");
        this.addConfig("Menu.tooltip", "Config options for menu settings");
        this.addConfig("Menu.button", "Options");

        this.addClientConfig("menu", "enable_menu_api", "Determines whether the Menu API is enabled or not");
        this.addClientConfig("menu", "active_menu", "Sets the current active menu title screen");
        this.addClientConfig("menu", "enable_menu_list_button", "Adds a button to the top right of the main menu screen to open a menu selection screen");

        this.addPackDescription("mod", "Cumulus Resources");

        this.addToast("world_preview", "Server still saving", "Please wait before toggling World preview");
    }

    String id();

    //--

    void add(String key, String value);

    void addBlock(Supplier<? extends Block> key, String name);

    void add(Block key, String name);

    void addItem(Supplier<? extends Item> key, String name);

    void add(Item key, String name);

    void addItemStack(Supplier<ItemStack> key, String name);

    void add(ItemStack key, String name);

    void addEffect(Supplier<? extends MobEffect> key, String name);

    void add(MobEffect key, String name);

    void addEntityType(Supplier<? extends EntityType<?>> key, String name);

    void add(EntityType<?> key, String name);

    void addTag(Supplier<? extends TagKey<?>> key, String name);

    void add(TagKey<?> tagKey, String name);

    //--

    default void addToast(String key, String title, String description) {
        this.add(this.id() + "." + key + ".toast.title", title);
        this.add(this.id() + "." + key + ".toast.description", description);
    }

    default void addGuiText(String key, String name) {
        this.add("gui." + this.id() + "." + key, name);
    }

    default void addMenuText(String key, String name) {
        this.addGuiText("menu." + key, name);
    }

    default void addMenuTitle(String key, String name) {
        this.add(this.id() + ".menu_title." + key, name);
    }

    default void addConfig(String prefix, String name) {
        this.add(this.id() + ".configuration." + prefix, name);
    }

    default void addClientConfig(String prefix, String key, String name) {
        this.add("config." + this.id() + ".client." + prefix + "." + key, name);
        this.add("config." + this.id() + ".client." + prefix + "." + key + ".tooltip", name);
    }

    default void addPackDescription(String packName, String description) {
        this.add("pack." + this.id() + "." + packName + ".description", description);
    }
}
