package com.aetherteam.cumulus.network.api;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import org.jetbrains.annotations.Nullable;

public interface PayloadSender {

    default void sendToServer(CustomPacketPayload payload, CustomPacketPayload... payloads) {
        // NO-OP
    }

    void sendToPlayer(ServerPlayer player, CustomPacketPayload payload, CustomPacketPayload... payloads);

    void sendToPlayersInDimension(ServerLevel level, CustomPacketPayload payload, CustomPacketPayload... payloads);

    void sendToPlayersNear(ServerLevel level, @Nullable ServerPlayer excluded, double x, double y, double z, double radius, CustomPacketPayload payload, CustomPacketPayload... payloads);

    void sendToAllPlayers(CustomPacketPayload payload, CustomPacketPayload... payloads);

    void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload payload, CustomPacketPayload... payloads);

    void sendToPlayersTrackingEntityAndSelf(Entity entity, CustomPacketPayload payload, CustomPacketPayload... payloads);

    void sendToPlayersTrackingChunk(ServerLevel level, ChunkPos chunkPos, CustomPacketPayload payload, CustomPacketPayload... payloads);
}
