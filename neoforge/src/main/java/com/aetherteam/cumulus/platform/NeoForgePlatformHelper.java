package com.aetherteam.cumulus.platform;

import com.aetherteam.cumulus.api.CumulusEntrypoint;
import com.aetherteam.cumulus.api.MenuInitializer;
import com.aetherteam.cumulus.platform.services.IPlatformHelper;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.ModList;
import org.objectweb.asm.Type;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class NeoForgePlatformHelper implements IPlatformHelper {

    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public List<MenuInitializer> getMenuInitializers() {
        var menuInitializers = new ArrayList<MenuInitializer>();

        for(var data : ModList.get().getAllScanData()) {
            for (var annotationData : data.getAnnotations()) {
                if(!annotationData.annotationType().equals(Type.getType(CumulusEntrypoint.class))){
                    continue;
                }

                try {
                    Class<?> clazz = Class.forName(annotationData.memberName());

                    if(MenuInitializer.class.isAssignableFrom(clazz)){
                        try {
                            menuInitializers.add((MenuInitializer) clazz.getDeclaredConstructor().newInstance());
                        } catch (Throwable e){
                            LOGGER.error("Failed to load MenuInitializer: {}", annotationData.memberName(), e);
                        }
                    }
                } catch (Throwable e) {
                    LOGGER.error("No class from such annotation: {}", annotationData.memberName(), e);
                }

                break;
            }
        }

        return menuInitializers;
    }
}