package io.github.spigotrce.paradiseclientprivate.packets;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record VelocityReportPayloadPacket(String reporter, String reported) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, VelocityReportPayloadPacket> CODEC = CustomPayload.codecOf(VelocityReportPayloadPacket::write, VelocityReportPayloadPacket::new);
    public static final Id<VelocityReportPayloadPacket> ID = new Id<>(Identifier.of("velocityreport", "main"));

    private VelocityReportPayloadPacket(PacketByteBuf buf) {
        this(buf.readString(), buf.readString());
    }

    public VelocityReportPayloadPacket(String reporter, String reported) {
        this.reporter = reporter;
        this.reported = reported;
    }

    private void write(PacketByteBuf buf) {
        buf.writeByte(0);
        buf.writeString("{\"reporter\": \"{reporter}\", \"reported\":\"{reported}\", \"server\":\"srv\", \"reason\":\"r\", \"type\":\"NewReport\"}"
                .replace("{reporter}", reporter).
                replace("{reported}", reported));
    }

    public Id<VelocityReportPayloadPacket> getId() {
        return ID;
    }
}