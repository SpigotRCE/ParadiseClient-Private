package io.github.spigotrce.paradiseclientprivate.client;

import io.github.spigotrce.paradiseclientprivate.packets.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class MainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PayloadTypeRegistry.playC2S().register(AuthMeVelocityPayloadPacket.ID, AuthMeVelocityPayloadPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(SignedVelocityPayloadPacket.ID, SignedVelocityPayloadPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(ChatSentryPayloadPacket.ID, ChatSentryPayloadPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(CloudSyncPayloadPacket.ID, CloudSyncPayloadPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(ParadiseBridgePayloadPacket.ID, ParadiseBridgePayloadPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(LuckPermsPayloadPacket.ID, LuckPermsPayloadPacket.CODEC);
    }
}
