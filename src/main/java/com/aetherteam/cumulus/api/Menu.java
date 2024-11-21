package com.aetherteam.cumulus.api;

import com.aetherteam.cumulus.mixin.mixins.client.accessor.ScreenAccessor;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;

import java.util.function.BooleanSupplier;

public record Menu(ResourceLocation icon, Component name, TitleScreen screen, BooleanSupplier condition, Runnable apply, Music music, CubeMap panorama) {

    public Menu(ResourceLocation icon, Component name, TitleScreen screen, BooleanSupplier condition) {
        this(icon, name, screen, condition, new Properties());
    }

    public Menu(ResourceLocation icon, Component name, TitleScreen screen, BooleanSupplier condition, Properties properties) {
        this(icon, name, screen, condition, properties.apply, properties.music, properties.panorama);
    }

    /**
     * @return The {@link ResourceLocation} of the {@link Menu}'s full registry ID.
     */
    public ResourceLocation getId() {
        return Menus.getKey(this);
    }

    /**
     * @return The {@link String} of the {@link Menu}'s full registry ID, converted from a {@link ResourceLocation} from {@link Menu#getId()}.
     */
    @Override
    public String toString() {
        return this.getId().toString();
    }

    public static class Properties {
        private Runnable apply = () -> {};
        private Music music = Musics.MENU;
        private CubeMap panorama = ScreenAccessor.cumulus$getCubeMap();

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
