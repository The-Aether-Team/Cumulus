package com.aetherteam.cumulus.network.serverbound;

import com.aetherteam.cumulus.Cumulus;
import com.aetherteam.cumulus.client.WorldDisplayHelper;
import com.aetherteam.cumulus.mixin.mixins.common.accessor.IntegratedServerAccessor;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetupLevelDisplayPacket() implements CustomPacketPayload {
    public static final Type<SetupLevelDisplayPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Cumulus.MODID, "setup_level_display"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetupLevelDisplayPacket> STREAM_CODEC = CustomPacketPayload.codec(
            SetupLevelDisplayPacket::write,
            SetupLevelDisplayPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) { }

    public static SetupLevelDisplayPacket decode(RegistryFriendlyByteBuf buf) {
        return new SetupLevelDisplayPacket();
    }

    @Override
    public Type<SetupLevelDisplayPacket> type() {
        return TYPE;
    }

    public static void execute(SetupLevelDisplayPacket payload, IPayloadContext context) {
        Player player = context.player();
        if (player.getServer() != null) {
            MinecraftServer server = player.getServer();
            if (server instanceof IntegratedServer integratedServer) {
                IntegratedServerAccessor accessor = (IntegratedServerAccessor) integratedServer;
                server.getConnection().stop();
                if (accessor.cumulus$getLanPinger() != null) {
                    accessor.cumulus$getLanPinger().interrupt();
                    accessor.cumulus$setLanPinger(null);
                }
                accessor.cumulus$setPublishedPort(-1);
                server.getPlayerList().saveAll();
                for (int i = 0; i < server.getPlayerList().getPlayers().size(); ++i) { //todo whats the proper way i should loop this
                    ServerPlayer serverPlayer = server.getPlayerList().getPlayers().get(i);
                    if (!serverPlayer.getUUID().equals(accessor.cumulus$getUUID())) {
                        serverPlayer.connection.disconnect(Component.translatable("multiplayer.disconnect.server_shutdown"));
                    }
                }
                Minecraft.getInstance().options.hideGui = true;
                Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                WorldDisplayHelper.setMenu();
            }
        }
    }
}
