package io.github.spigotrce.paradiseclientprivate.packets;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record CloudSyncPayloadPacket(String playerName, String command) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, CloudSyncPayloadPacket> CODEC = CustomPayload.codecOf(CloudSyncPayloadPacket::write, CloudSyncPayloadPacket::new);
    public static final Id<CloudSyncPayloadPacket> ID = new Id<>(Identifier.of("plugin", "cloudsync"));

    public CloudSyncPayloadPacket(PacketByteBuf buf) {
        this(buf.readString(), buf.readString());
    }

    public CloudSyncPayloadPacket(String playerName, String command) {
        this.playerName = playerName;
        this.command = command;
    }

    public void write(PacketByteBuf buf) {
        buf.writeString(playerName);
        buf.writeString(command);
    }

    public Id<CloudSyncPayloadPacket> getId() {
        return ID;
    }
}
