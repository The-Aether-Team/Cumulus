package com.aetherteam.cumulus.network.packets;

import com.aetherteam.cumulus.network.api.PayloadRegistration;

public class CumulusPackets {
    public static void registerPackets(PayloadRegistration registration) {
        // SERVERBOUND
        registration.playToServer(SetupLevelDisplayPacket.TYPE, SetupLevelDisplayPacket.STREAM_CODEC, SetupLevelDisplayPacket::execute);
    }
}
