package com.aetherteam.cumulus.platform.services;

import com.aetherteam.cumulus.api.MenuInitializer;

import java.util.List;

public interface IPlatformHelper {
    List<MenuInitializer> getMenuInitializers();
}