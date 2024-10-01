package io.github.spigotrce.paradiseclientprivate.packets;

import io.github.spigotrce.paradiseclientfabric.Helper;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

public record ParadiseBridgePayloadPacket(String id, ArrayList<String> data) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, ParadiseBridgePayloadPacket> CODEC = CustomPayload.codecOf(ParadiseBridgePayloadPacket::write, ParadiseBridgePayloadPacket::new);
    public static final Id<ParadiseBridgePayloadPacket> ID = new Id<>(Identifier.of("paradise-client", "bridge"));

    private ParadiseBridgePayloadPacket(PacketByteBuf buf) {
        this(buf.readString(), new ArrayList<>());
    }

    private void write(PacketByteBuf buf) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF(id);
            if (data.isEmpty()) {
                out.writeInt(0);
                return;
            }
            out.writeInt(data.size());
            for (String s : data) out.writeUTF(s);
        } catch (Exception e) {
            Helper.printChatMessage(e.getMessage());
        }
        buf.writeBytes(b.toByteArray());
    }

    public Id<ParadiseBridgePayloadPacket> getId() {
        return ID;
    }
}
