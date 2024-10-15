package io.github.spigotrce.paradiseclientprivate.packets;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record T2CPayloadPacket(String command) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, T2CPayloadPacket> CODEC = CustomPayload.codecOf(T2CPayloadPacket::write, T2CPayloadPacket::new);
    public static final Id<T2CPayloadPacket> ID = new Id<>(Identifier.of("t2c", "bonlp"));

    public T2CPayloadPacket(PacketByteBuf buf) {
        this(buf.readString());
    }

    public T2CPayloadPacket(String command) {
        this.command = command;
    }

    public void write(PacketByteBuf buf) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("T2Code-Console");
        out.writeUTF(command);
        buf.writeBytes(out.toByteArray());
    }

    public Id<T2CPayloadPacket> getId() {
        return ID;
    }
}