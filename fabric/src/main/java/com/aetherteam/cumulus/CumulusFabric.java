package com.aetherteam.cumulus;

import com.aetherteam.cumulus.api.Menus;
import com.aetherteam.cumulus.client.event.listeners.MenuListener;
import com.aetherteam.cumulus.client.event.listeners.WorldPreviewListener;
import com.mojang.logging.LogUtils;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

public class CumulusFabric implements ClientModInitializer, ModInitializer {
    public static final String MODID = "cumulus_menus";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitializeClient() {
        Menus.init();

        MenuListener.initEvents();
        WorldPreviewListener.initEvents();

        ServerLifecycleEvents.SERVER_STARTED.register(server -> Cumulus.SERVER_INSTANCE = server);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> Cumulus.SERVER_INSTANCE = null);
    }

    @Override
    public void onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.register(Cumulus.MODID, ModConfig.Type.CLIENT, CumulusConfig.CLIENT_SPEC);
    }
}
