package io.github.spigotrce.paradiseclientprivate.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.command.Command;
import io.github.spigotrce.paradiseclientprivate.client.ChannelSender;
import io.github.spigotrce.paradiseclientprivate.packets.CloudSyncPayloadPacket;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;

public class CloudSyncCommand extends Command {
    public CloudSyncCommand(MinecraftClient minecraftClient) {
        super("cloudsync", "Executes CloudSync Commands", minecraftClient);
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> build() {
        return literal(getName())
                .executes(context -> {
                    Helper.printChatMessage("Incomplete command!");
                    return SINGLE_SUCCESS;
                })
                .then(ClientCommandManager.argument("player", StringArgumentType.word())
                        .executes(context -> {
                            Helper.printChatMessage("Incomplete command!");
                            return SINGLE_SUCCESS;
                        })
                        .then(ClientCommandManager.argument("command", StringArgumentType.greedyString())
                                .executes(context -> {
                                    String playerName = context.getArgument("player", String.class);
                                    String command = context.getArgument("command", String.class);

                                    Helper.sendPacket(
                                            new CustomPayloadC2SPacket(
                                                    new CloudSyncPayloadPacket(playerName, command)
                                            )
                                    );
                                    Helper.printChatMessage("CloudSync payload sent!");
                                    return SINGLE_SUCCESS;
                                })
                        )
                );
    }
}
