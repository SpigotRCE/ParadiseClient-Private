package io.github.spigotrce.paradiseclientprivate.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.command.Command;
import io.github.spigotrce.paradiseclientprivate.packets.AuthMeVelocityPayloadPacket;
import io.github.spigotrce.paradiseclientprivate.packets.ParadiseBridgePayloadPacket;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;

public class ParadiseBridgeCommand extends Command {
    public ParadiseBridgeCommand(MinecraftClient minecraftClient) {
        super("bridge", "Paradise ExploitsVelocity bridge", minecraftClient);
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> build() {
        return literal(getName())
                .executes(context -> {
                    Helper.printChatMessage("Unimplemented!");
                    return Command.SINGLE_SUCCESS;
                });
    }
}
