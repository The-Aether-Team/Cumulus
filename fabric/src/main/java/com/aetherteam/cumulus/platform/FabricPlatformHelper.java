package com.aetherteam.cumulus.platform;

import com.aetherteam.cumulus.api.MenuInitializer;
import com.aetherteam.cumulus.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public List<MenuInitializer> getMenuInitializers() {
        return FabricLoader.getInstance().getEntrypoints("cumulus:menu_initializers", MenuInitializer.class);
    }
}
