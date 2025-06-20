package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.mixin.extensions.EntityRenderStateExtension;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.UUID;

@Mixin(EntityRenderState.class)
public class EntityRenderStateMixin implements EntityRenderStateExtension {
    @Unique
    private UUID uuid = UUID.randomUUID();

    @Override
    public UUID cumulus$getUUID() {
        return this.uuid;
    }

    @Override
    public void cumulus$setUUID(UUID uuid) {
        this.uuid = uuid;
    }
}
