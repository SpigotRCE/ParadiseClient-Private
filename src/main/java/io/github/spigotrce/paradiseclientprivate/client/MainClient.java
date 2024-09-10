package io.github.spigotrce.paradiseclientprivate.client;

import io.github.spigotrce.paradiseclientprivate.packets.AuthMeVelocityPayloadPacket;
import io.github.spigotrce.paradiseclientprivate.packets.ChatSentryPayloadPacket;
import io.github.spigotrce.paradiseclientprivate.packets.SignedVelocityPayloadPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class MainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PayloadTypeRegistry.playC2S().register(AuthMeVelocityPayloadPacket.ID, AuthMeVelocityPayloadPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(SignedVelocityPayloadPacket.ID, SignedVelocityPayloadPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(ChatSentryPayloadPacket.ID, ChatSentryPayloadPacket.CODEC);
    }
}
