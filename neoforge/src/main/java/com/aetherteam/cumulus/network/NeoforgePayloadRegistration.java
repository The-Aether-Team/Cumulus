package com.aetherteam.cumulus.network;

import com.aetherteam.cumulus.network.api.PayloadRegistration;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public record NeoforgePayloadRegistration(PayloadRegistrar registrar) implements PayloadRegistration {
    @Override
    public <T extends CustomPacketPayload> PayloadRegistration playToClient(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader, PayloadHandler<T> handler) {
        registrar.playToClient(type, reader, convert(handler));

        return this;
    }

    @Override
    public <T extends CustomPacketPayload> PayloadRegistration playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader, PayloadHandler<T> handler) {
        registrar.playBidirectional(type, reader, convert(handler));

        return this;
    }

    @Override
    public <T extends CustomPacketPayload> PayloadRegistration playToServer(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader, PayloadHandler<T> handler) {
        registrar.playToServer(type, reader, convert(handler));

        return this;
    }

    private static <T extends CustomPacketPayload> IPayloadHandler<T> convert(PayloadHandler<T> handler) {
        return (payload, context) -> {
            handler.handle(payload, context.player());
        };
    }
}
