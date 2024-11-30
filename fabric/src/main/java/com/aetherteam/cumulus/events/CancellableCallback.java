package com.aetherteam.cumulus.events;

public interface CancellableCallback {

    boolean isCanceled();

    void setCanceled(boolean value);
}
