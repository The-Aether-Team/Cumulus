package com.aetherteam.cumulus.platform;

import com.aetherteam.cumulus.Cumulus;
import com.aetherteam.cumulus.api.MenuInitializer;
import com.aetherteam.cumulus.platform.services.IPlatformHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public List<MenuInitializer> getMenuInitializers() {
        return FabricLoader.getInstance().getEntrypoints("cumulus:menu_initializers", MenuInitializer.class);
    }

    //--

    @Override
    @Environment(EnvType.CLIENT)
    public void sendToServer(CustomPacketPayload payload, CustomPacketPayload... payloads) {
        ClientPlayNetworking.send(payload);

        for (var otherPayload : payloads) {
            ClientPlayNetworking.send(otherPayload);
        }
    }

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload, CustomPacketPayload... payloads) {
        player.connection.send(makeClientboundPacket(payload, payloads));
    }

    @Override
    public void sendToPlayersInDimension(ServerLevel level, CustomPacketPayload payload, CustomPacketPayload... payloads) {
        var packet = makeClientboundPacket(payload, payloads);

        for (var serverPlayer : PlayerLookup.world(level)) {
            serverPlayer.connection.send(packet);
        }
    }

    @Override
    public void sendToPlayersNear(ServerLevel level, @Nullable ServerPlayer excluded, double x, double y, double z, double radius, CustomPacketPayload payload, CustomPacketPayload... payloads) {
        var packet = makeClientboundPacket(payload, payloads);

        for (var serverPlayer : PlayerLookup.around(level, new Vec3(x, y, z), radius)) {
            if (serverPlayer == excluded) continue;

            serverPlayer.connection.send(packet);
        }
    }

    @Override
    public void sendToAllPlayers(CustomPacketPayload payload, CustomPacketPayload... payloads) {
        var packet = makeClientboundPacket(payload, payloads);

        for (var serverPlayer : PlayerLookup.all(Cumulus.SERVER_INSTANCE)) {
            serverPlayer.connection.send(packet);
        }
    }

    @Override
    public void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload payload, CustomPacketPayload... payloads) {
        var packet = makeClientboundPacket(payload, payloads);

        for (var serverPlayer : PlayerLookup.tracking(entity)) {
            if (serverPlayer == entity) continue; // Just in case such is picked up

            serverPlayer.connection.send(packet);
        }
    }

    @Override
    public void sendToPlayersTrackingEntityAndSelf(Entity entity, CustomPacketPayload payload, CustomPacketPayload... payloads) {
        var packet = makeClientboundPacket(payload, payloads);

        var targets = new LinkedHashSet<>(PlayerLookup.tracking(entity));

        if (entity instanceof ServerPlayer serverPlayer) targets.add(serverPlayer);

        for (var serverPlayer : targets) {
            if (serverPlayer == entity) continue;

            serverPlayer.connection.send(packet);
        }
    }

    @Override
    public void sendToPlayersTrackingChunk(ServerLevel level, ChunkPos chunkPos, CustomPacketPayload payload, CustomPacketPayload... payloads) {
        var packet = makeClientboundPacket(payload, payloads);

        for (var serverPlayer : PlayerLookup.tracking(level, chunkPos)) {
            serverPlayer.connection.send(packet);
        }
    }

    //--

    private static Packet<?> makeClientboundPacket(CustomPacketPayload payload, CustomPacketPayload... payloads) {
        if (payloads.length > 0) {
            var packets = new ArrayList<Packet<? super ClientGamePacketListener>>();

            packets.add(new ClientboundCustomPayloadPacket(payload));

            for (var otherPayload : payloads) {
                packets.add(new ClientboundCustomPayloadPacket(otherPayload));
            }

            return new ClientboundBundlePacket(packets);
        }

        return new ClientboundCustomPayloadPacket(payload);
    }
}
