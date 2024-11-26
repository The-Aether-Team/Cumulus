package com.aetherteam.cumulus;

import com.aetherteam.cumulus.api.Menus;
import com.aetherteam.cumulus.client.event.listeners.MenuListener;
import com.mojang.logging.LogUtils;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

public class CumulusFabric implements ClientModInitializer, ModInitializer {
    public static final String MODID = "cumulus_menus";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitializeClient() {
        Menus.init();

        MenuListener.initEvents();
    }

    @Override
    public void onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.register(Cumulus.MODID, ModConfig.Type.CLIENT, CumulusConfig.CLIENT_SPEC);
    }
}
