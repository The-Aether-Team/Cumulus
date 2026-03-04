package com.aetherteam.cumulus.api;

import com.aetherteam.cumulus.client.gui.component.MenuSelectionList;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.GameRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;

/**
 * Acts as a holder object for various custom paramters that can be adjusted for a custom menu
 *
 * @param icon      The Icon used within the {@link MenuSelectionList}
 * @param name      The name used within the {@link MenuSelectionList}
 * @param screen    The instance of the custom title screen used to replace minecrafts default
 * @param apply     The Callback when the given menu is to be applied
 * @param music     The Custom {@link Music} to be played while the screen is active
 * @param panorama  The panorama {@link CubeMap} used to replace minecarfts default
 */
public record Menu(Identifier icon, Component name, TitleScreen screen, Runnable apply, Music music, CubeMap panorama) {

    public Menu(Identifier icon, Component name, TitleScreen screen) {
        this(icon, name, screen, new Properties());
    }

    public Menu(Identifier icon, Component name, TitleScreen screen, Properties properties) {
        this(icon, name, screen, properties.apply, properties.music, properties.panorama);
    }

    /**
     * @return The {@link Identifier} of the {@link Menu}'s full registry ID.
     */
    public Identifier getId() {
        return Menus.getKey(this);
    }

    /**
     * @return The {@link String} of the {@link Menu}'s full registry ID, converted from a {@link Identifier} from {@link Menu#getId()}.
     */
    @Override
    public String toString() {
        return this.getId().toString();
    }

    public static class Properties {
        private Runnable apply = () -> {};
        private Music music = Musics.MENU;
        private CubeMap panorama = new CubeMap(Identifier.withDefaultNamespace("textures/gui/title/background/panorama"));

        /**
         * @see Menu#apply()
         */
        public Properties apply(Runnable apply) {
            this.apply = apply;
            return this;
        }

        /**
         * @see Menu#music()
         */
        public Properties music(Music music) {
            this.music = music;
            return this;
        }

        /**
         * @see Menu#panorama()
         */
        public Properties panorama(CubeMap panorama) {
            this.panorama = panorama;
            return this;
        }

        public static Properties propertiesFromType(Menu menu) {
            Properties props = new Properties();
            props.apply = menu.apply;
            props.music = menu.music;
            props.panorama = menu.panorama;
            return props;
        }
    }
}
