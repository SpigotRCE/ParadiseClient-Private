package io.github.spigotrce.paradiseclientprivate.client;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import io.github.spigotrce.paradiseclientprivate.packets.CloudSyncPayloadPacket;

public class ChannelSender {

    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void sendPluginMessage(String channel, String playerName, String command) {
        if (client == null || client.getNetworkHandler() == null) {
            return;
        }

        // Create the packet with the actual playerName and command
        CloudSyncPayloadPacket packet = new CloudSyncPayloadPacket(playerName, command);

        // Convert the packet to byte array
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(playerName);
        out.writeUTF(command);

        // Send the packet
        if (channel.equalsIgnoreCase("plugin:cloudsync")) {
            client.getNetworkHandler().sendPacket(new CustomPayloadC2SPacket(packet));
        }
        // Handle other channel types if needed
    }
}
