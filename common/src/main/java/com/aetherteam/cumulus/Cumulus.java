package com.aetherteam.cumulus;

import com.mojang.logging.LogUtils;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class Cumulus {
    public static final String MODID = "cumulus_menus";
    public static final Logger LOGGER = LogUtils.getLogger();
    @Nullable
    public static MinecraftServer SERVER_INSTANCE = null;
}