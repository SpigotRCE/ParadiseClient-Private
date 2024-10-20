package io.github.spigotrce.paradiseclientprivate.packets;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.github.spigotrce.paradiseclientfabric.Helper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

public record AtlasPayloadPacket(String command) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, AtlasPayloadPacket> CODEC = CustomPayload.codecOf(AtlasPayloadPacket::write, AtlasPayloadPacket::new);
    public static final Id<AtlasPayloadPacket> ID = new Id<>(Identifier.of("atlas", "out"));

    private AtlasPayloadPacket(PacketByteBuf buf) {
        this(buf.readString());
    }

    public void write(PacketByteBuf buf) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutputStream oStream = new ObjectOutputStream(stream);
            oStream.writeUTF("commandBungee");
            oStream.writeObject(command);
            buf.writeBytes(stream.toByteArray());
        } catch (IOException e) {
            Helper.printChatMessage(e.getMessage());
        }
    }

    public Id<AtlasPayloadPacket> getId() {
        return ID;
    }
}
