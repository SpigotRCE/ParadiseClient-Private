package io.github.spigotrce.paradiseclientprivate.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.command.Command;
import io.github.spigotrce.paradiseclientprivate.packets.AuthMeVelocityPayloadPacket;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket;

public class DumpCommand extends Command {
    public DumpCommand(MinecraftClient minecraftClient) {
        super("paradisedump", "IP dumping methods", minecraftClient);
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> build() {
        return literal(getName())
                .executes(context -> {
                    Helper.sendPacket(new RequestCommandCompletionsC2SPacket(1234689045, "/ip "));
                    Helper.printChatMessage("Attempting to dump IPs via bungee /ip method!");
                    return Command.SINGLE_SUCCESS;
                });
    }
}
