package com.aetherteam.cumulus.mixin.extensions;

import java.util.UUID;

public interface EntityRenderStateExtension {
    UUID cumulus$getUUID();

    void cumulus$setUUID(UUID uuid);
}
