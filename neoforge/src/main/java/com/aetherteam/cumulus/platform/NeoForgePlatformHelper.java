package com.aetherteam.cumulus.platform;

import com.aetherteam.cumulus.api.CumulusEntrypoint;
import com.aetherteam.cumulus.api.MenuInitializer;
import com.aetherteam.cumulus.platform.services.IPlatformHelper;
import com.mojang.logging.LogUtils;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Type;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class NeoForgePlatformHelper implements IPlatformHelper {

    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public List<MenuInitializer> getMenuInitializers() {
        var menuInitializers = new ArrayList<MenuInitializer>();

        for(var data : ModList.get().getAllScanData()) {
            for (var annotationData : data.getAnnotations()) {
                if(!annotationData.annotationType().equals(Type.getType(CumulusEntrypoint.class))){
                    continue;
                }

                try {
                    Class<?> clazz = Class.forName(annotationData.memberName());

                    if(MenuInitializer.class.isAssignableFrom(clazz)){
                        try {
                            menuInitializers.add((MenuInitializer) clazz.getDeclaredConstructor().newInstance());
                        } catch (Throwable e){
                            LOGGER.error("Failed to load MenuInitializer: {}", annotationData.memberName(), e);
                        }
                    }
                } catch (Throwable e) {
                    LOGGER.error("No class from such annotation: {}", annotationData.memberName(), e);
                }

                break;
            }
        }

        return menuInitializers;
    }

    //--


    @Override
    public void sendToServer(CustomPacketPayload payload, CustomPacketPayload... payloads) {
        PacketDistributor.sendToServer(payload, payloads);
    }

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload, CustomPacketPayload... payloads) {
        PacketDistributor.sendToPlayer(player, payload, payloads);
    }

    @Override
    public void sendToPlayersInDimension(ServerLevel level, CustomPacketPayload payload, CustomPacketPayload... payloads) {
        PacketDistributor.sendToPlayersInDimension(level, payload, payloads);
    }

    @Override
    public void sendToPlayersNear(ServerLevel level, @Nullable ServerPlayer excluded, double x, double y, double z, double radius, CustomPacketPayload payload, CustomPacketPayload... payloads) {
        PacketDistributor.sendToPlayersNear(level, excluded, x, y, z, radius, payload, payloads);
    }

    @Override
    public void sendToAllPlayers(CustomPacketPayload payload, CustomPacketPayload... payloads) {
        PacketDistributor.sendToAllPlayers(payload, payloads);
    }

    @Override
    public void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload payload, CustomPacketPayload... payloads) {
        PacketDistributor.sendToPlayersTrackingEntity(entity, payload, payloads);
    }

    @Override
    public void sendToPlayersTrackingEntityAndSelf(Entity entity, CustomPacketPayload payload, CustomPacketPayload... payloads) {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, payload, payloads);
    }

    @Override
    public void sendToPlayersTrackingChunk(ServerLevel level, ChunkPos chunkPos, CustomPacketPayload payload, CustomPacketPayload... payloads) {
        PacketDistributor.sendToPlayersTrackingChunk(level, chunkPos, payload, payloads);
    }
}