package com.aetherteam.cumulus.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

public class OpeningScreenEvents {

    public static final Event<Post> POST = EventFactory.createArrayBacked(Post.class, invokers -> (oldScreen, newScreen) -> {
        for (var invoker : invokers) {
            var replacementScreen = invoker.onOpening(oldScreen, newScreen);

            if (replacementScreen != null) newScreen = replacementScreen;
        }

        return newScreen;
    });

    public interface Post {
        @Nullable Screen onOpening(Screen oldScreen, Screen newScreen);
    }
}

