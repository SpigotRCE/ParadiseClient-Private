package io.github.spigotrce.paradiseclientprivate.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.command.Command;
import io.github.spigotrce.paradiseclientprivate.packets.AuthMeVelocityPayloadPacket;
import io.github.spigotrce.paradiseclientprivate.packets.ChatSentryPayloadPacket;
import io.github.spigotrce.paradiseclientprivate.packets.SignedVelocityPayloadPacket;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;

public class ChatSentryCommand extends Command {
    public ChatSentryCommand(MinecraftClient minecraftClient) {
        super("paradisechatsentry", "Executes bungee command through console!", minecraftClient);
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> build() {
        return literal(getName())
                .executes(context -> {
                    Helper.printChatMessage("Incomplete command!");
                    return SINGLE_SUCCESS;
                })
                .then(ClientCommandManager.argument("command", StringArgumentType.greedyString())
                        .executes(context -> {
                            Helper.sendPacket(new CustomPayloadC2SPacket(
                                    new ChatSentryPayloadPacket(context.getArgument("command", String.class))
                            ));
                            Helper.printChatMessage("Chat sentry payload packet sent!");
                            return SINGLE_SUCCESS;
                        })
                );
    }
}
