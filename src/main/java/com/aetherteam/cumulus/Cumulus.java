package com.aetherteam.cumulus;

import com.aetherteam.cumulus.api.Menu;
import com.aetherteam.cumulus.api.Menus;
import com.aetherteam.cumulus.client.event.listeners.MenuListener;
import com.mojang.logging.LogUtils;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

public class Cumulus implements ClientModInitializer {
    public static final String MODID = "cumulus_menus";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceKey<Registry<Menu>> MENU_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(Cumulus.MODID, "menu"));
    public static final LazyRegistrar<Menu> DEFERRED_MENUS = LazyRegistrar.create(Cumulus.MENU_REGISTRY_KEY, Cumulus.MENU_REGISTRY_KEY.location().getNamespace());
    public static final Registry<Menu> MENU_REGISTRY = FabricRegistryBuilder.createSimple(Cumulus.MENU_REGISTRY_KEY).buildAndRegister();

    @Override
    public void onInitializeClient() {
        ForgeConfigRegistry.INSTANCE.register(Cumulus.MODID, ModConfig.Type.CLIENT, CumulusConfig.CLIENT_SPEC);

        Cumulus.DEFERRED_MENUS.register();
        Menus.MENUS.register();

        Cumulus.initEvents();
    }

    public static void initEvents() {
        MenuListener.init();
    }
}
