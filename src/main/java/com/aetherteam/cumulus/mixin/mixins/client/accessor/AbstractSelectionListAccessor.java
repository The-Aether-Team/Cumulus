package com.aetherteam.cumulus.mixin.mixins.client.accessor;

import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractSelectionList.class)
public interface AbstractSelectionListAccessor<E extends AbstractSelectionList.Entry<E>>  {
    @Accessor("renderHeader")
    boolean isRenderHeader();

    @Accessor("renderHeader")
    void setRenderHeader(boolean renderHeader);

    @Accessor("renderBackground")
    boolean isRenderBackground();

    @Accessor("renderBackground")
    void setRenderBackground(boolean renderBackground);

    @Accessor("hovered")
    E getHovered();

    @Accessor("hovered")
    void setHovered(E hovered);
}
