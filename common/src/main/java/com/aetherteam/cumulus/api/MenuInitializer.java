package com.aetherteam.cumulus.api;

/**
 * Serves as the main interface to register {@link Menu}'s to be listed with
 * the selection screen.
 *
 *  <br><br>
 *  Example Entry Point within fabric.mod.json:
 *  <pre>
 *  {@code
 *  "entrypoints": {
 *      "condensed_creative": [
 *        "the.class.path.here"
 *      ]
 *  }}
 *  </pre>
 *  <br>
 *  It is recommended that all <strong>Fabric Mods</strong> add the {@link CumulusEntrypoint}
 *  for use when on Neoforge and Connector is involved.
 */
public interface MenuInitializer {
    /**
     * Method used to register {@link Menu}'s to be listed within the selection screen
     * using the given
     */
    void registerMenus(MenuRegisterCallback registerCallback);
}
