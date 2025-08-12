package com.aetherteam.cumulus.network;

import com.aetherteam.cumulus.network.api.PayloadRegistration;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class FabricPayloadRegistration implements PayloadRegistration {

    public static final FabricPayloadRegistration INSTANCE = new FabricPayloadRegistration();

    FabricPayloadRegistration(){}
    @Override
    public <T extends CustomPacketPayload> PayloadRegistration playToClient(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader, PayloadHandler<T> handler) {
        PayloadTypeRegistry.playS2C().register(type, reader);

        if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
            registerClientPayloadHandler(type, handler);
        }

        return this;
    }

    @Environment(EnvType.CLIENT)
    private static <T extends CustomPacketPayload> void registerClientPayloadHandler(CustomPacketPayload.Type<T> type, PayloadHandler<T> handler) {
        ClientPlayNetworking.registerGlobalReceiver(type, (payload, context) -> handler.handle(payload, context.player()));
    }

    @Override
    public <T extends CustomPacketPayload> PayloadRegistration playToServer(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader, PayloadHandler<T> handler) {
        PayloadTypeRegistry.playC2S().register(type, reader);

        ServerPlayNetworking.registerGlobalReceiver(type, (payload, context) -> handler.handle(payload, context.player()));

        return this;
    }

    @Override
    public <T extends CustomPacketPayload> PayloadRegistration playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader, PayloadHandler<T> handler) {
        playToClient(type, reader, handler);
        playToServer(type, reader, handler);

        return this;
    }
}
