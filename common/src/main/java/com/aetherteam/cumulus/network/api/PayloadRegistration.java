package com.aetherteam.cumulus.network.api;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public interface PayloadRegistration {

    default <T extends CustomPacketPayload> PayloadRegistration playToClient(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader, PayloadHandler<T> handler) {
        return this;
    }

    <T extends CustomPacketPayload> PayloadRegistration playToServer(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader, PayloadHandler<T> handler);

    <T extends CustomPacketPayload> PayloadRegistration playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader, PayloadHandler<T> handler);

    interface PayloadHandler<T extends CustomPacketPayload> {
        void handle(T packet, Player player);
    }
}
