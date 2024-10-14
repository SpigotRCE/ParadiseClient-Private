package io.github.spigotrce.paradiseclientprivate.packets;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record DRSPayloadPacket(String command) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, DRSPayloadPacket> CODEC = CustomPayload.codecOf(DRSPayloadPacket::write, DRSPayloadPacket::new);
    public static final Id<DRSPayloadPacket> ID = new Id<>(Identifier.of("discordranksync", "data"));

    public DRSPayloadPacket(PacketByteBuf buf) {
        this(buf.readString());
    }

    public DRSPayloadPacket(String command) {
        this.command = command;
    }

    public void write(PacketByteBuf buf) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(command);
        buf.writeBytes(out.toByteArray());
    }

    public Id<DRSPayloadPacket> getId() {
        return ID;
    }
}