package com.aetherteam.cumulus.api;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.event.IModBusEvent;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

/**
 * Allows users to register custom {@link Menu menus} for the title screen.
 *
 * <p>These will show up under the "Menu List" button Cumulus adds, allowing for easy swapping between custom main menus.
 *
 * <p>This event is not {@linkplain ICancellableEvent cancellable}, and does not {@linkplain HasResult have a result}.
 *
 * <p>This event is fired on the mod-specific event bus, only on the {@linkplain LogicalSide#CLIENT logical client}.</p>
 */
public class RegisterMenuEvent extends Event implements IModBusEvent {

    private final Map<ResourceLocation, Menu> menus;

    @ApiStatus.Internal
    public RegisterMenuEvent(Map<ResourceLocation, Menu> menus) {
        this.menus = menus;
    }

    /**
     * Registers a new {@link Menu}.
     */
    public void register(ResourceLocation menuName, Menu menu) {
        this.menus.put(menuName, menu);
    }
}
