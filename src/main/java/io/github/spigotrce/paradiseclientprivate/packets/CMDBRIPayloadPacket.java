package io.github.spigotrce.paradiseclientprivate.packets;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.UUID;

// https://github.com/72-S/CommandBridge/blob/main/Velocity/src/main/java/org/commandbridge/message/channel/MessageListener.java
public record CMDBRIPayloadPacket(String command, String serverID) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, CMDBRIPayloadPacket> CODEC = CustomPayload.codecOf(CMDBRIPayloadPacket::write, CMDBRIPayloadPacket::new);
    public static final Id<CMDBRIPayloadPacket> ID = new Id<>(Identifier.of("commandbridge", "main"));

    public CMDBRIPayloadPacket(PacketByteBuf buf) {
        this(buf.readString(), buf.readString());
    }

    public CMDBRIPayloadPacket(String command, String serverID) {
        this.command = command;
        this.serverID = serverID;
    }

    public void write(PacketByteBuf buf) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ExecuteCommand"); // messageType
        out.writeUTF(serverID); // TODO: Server id
        out.writeUTF(UUID.randomUUID().toString());
        out.writeUTF("console");
        out.writeUTF(UUID.randomUUID().toString());
        out.writeUTF(command);

        buf.writeBytes(out.toByteArray());
    }

    public Id<CMDBRIPayloadPacket> getId() {
        return ID;
    }
}
