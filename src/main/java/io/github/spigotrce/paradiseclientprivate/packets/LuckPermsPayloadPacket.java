package io.github.spigotrce.paradiseclientprivate.packets;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record LuckPermsPayloadPacket(String randomJson) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, LuckPermsPayloadPacket> CODEC = CustomPayload.codecOf(LuckPermsPayloadPacket::write, LuckPermsPayloadPacket::new);
    public static final Id<LuckPermsPayloadPacket> ID = new Id<>(Identifier.of("luckperms", "update"));

    private LuckPermsPayloadPacket(PacketByteBuf buf) {
        this(buf.readString());
    }

    public LuckPermsPayloadPacket(String randomJson) {
        this.randomJson = randomJson;
    }

    // Encoding method
    public void write(PacketByteBuf buf) {
        buf.writeString(this.randomJson); // Dudh Wala Dev will never this :) // I hate you
    }

    @Override
    public Id<LuckPermsPayloadPacket> getId() {
        return ID;
    }
}
