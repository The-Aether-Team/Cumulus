package com.aetherteam.cumulus.platform.services;

import com.aetherteam.cumulus.api.MenuInitializer;
import com.aetherteam.cumulus.network.api.PayloadSender;

import java.util.List;

public interface IPlatformHelper extends PayloadSender {
    List<MenuInitializer> getMenuInitializers();
}