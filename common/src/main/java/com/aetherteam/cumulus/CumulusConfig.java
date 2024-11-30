package com.aetherteam.cumulus;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CumulusConfig {
    public static class Client {
        public final ModConfigSpec.ConfigValue<Boolean> enable_menu_api;
        public final ModConfigSpec.ConfigValue<String> active_menu;
        public final ModConfigSpec.ConfigValue<Boolean> enable_menu_list_button;

        public final ModConfigSpec.ConfigValue<Boolean> enable_world_preview;
        public final ModConfigSpec.ConfigValue<Boolean> enable_world_preview_button;
        public final ModConfigSpec.ConfigValue<Boolean> enable_quick_load_button;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("Menu");
            enable_menu_api = builder
                    .comment("Determines whether the Menu API is enabled or not")
                    .translation("config.cumulus_menus.client.menu.enable_menu_api")
                    .define("Enable Menu API", true);
            active_menu = builder
                    .comment("Sets the current active menu title screen")
                    .translation("config.cumulus_menus.client.menu.active_menu")
                    .define("Active Menu", "cumulus_menus:minecraft");
            enable_menu_list_button = builder
                    .comment("Adds a button to the top right of the main menu screen to open a menu selection screen")
                    .translation("config.cumulus_menus.client.menu.enable_menu_list_button")
                    .define("Enables menu selection button", true);
            builder.pop();

            builder.push("World Preview");
            enable_world_preview = builder
                    .comment("Changes the background panorama into a preview of the latest played world")
                    .translation("config.cumulus_menus.client.world_preview.enable_world_preview")
                    .define("Enables world preview", false);
            enable_world_preview_button = builder
                    .comment("Adds a button to the top right of the main menu screen to toggle between the panorama and world preview")
                    .translation("config.cumulus_menus.client.world_preview.enable_world_preview_button")
                    .define("Enables toggle world button", true);
            enable_quick_load_button = builder
                    .comment("Adds a button to the top right of the main menu screen to allow quick loading into a world if the world preview is enabled")
                    .translation("config.cumulus_menus.client.world_preview.enable_quick_load_button")
                    .define("Enables quick load button", true);
            builder.pop();
        }
    }

    public static final ModConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        final Pair<Client, ModConfigSpec> clientSpecPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }
}
