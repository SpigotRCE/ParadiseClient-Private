package io.github.spigotrce.paradiseclientprivate.client;

import io.github.spigotrce.paradiseclientprivate.packets.AuthMeVelocityPayloadPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class MainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PayloadTypeRegistry.playC2S().register(AuthMeVelocityPayloadPacket.ID, AuthMeVelocityPayloadPacket.CODEC);
    }
}
