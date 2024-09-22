package io.github.spigotrce.paradiseclientprivate.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.command.Command;
import io.github.spigotrce.paradiseclientprivate.client.ChannelSender;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;

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
                .then(ClientCommandManager.argument("type", StringArgumentType.word())
                        .suggests((ctx, builder) -> builder
                                .suggest("bungee")
                                .suggest("velocity")
                                .buildFuture()).then(ClientCommandManager.argument("user", StringArgumentType.word())
                                .suggests((ctx, builder) -> {
                                    String partialName;

                                    try {
                                        partialName = ctx.getArgument("user", String.class).toLowerCase();
                                    } catch (IllegalArgumentException ignored) {
                                        partialName = "";
                                    }

                                    if (partialName.isEmpty()) {
                                        getMinecraftClient().getNetworkHandler().getPlayerList().forEach(playerListEntry -> builder.suggest(playerListEntry.getProfile().getName()));
                                        return builder.buildFuture();
                                    }

                                    String finalPartialName = partialName;

                                    getMinecraftClient().getNetworkHandler().getPlayerList().stream().map(PlayerListEntry::getProfile)
                                            .filter(player -> player.getName().toLowerCase().startsWith(finalPartialName))
                                            .forEach(profile -> builder.suggest(profile.getName()));

                                    return builder.buildFuture();
                                })
                                .executes(context -> {
                                    Helper.printChatMessage("Incomplete command!");
                                    return SINGLE_SUCCESS;
                                })
                                .then(ClientCommandManager.argument("command", StringArgumentType.greedyString())
                                        .executes(context -> {
                                            String type = context.getArgument("type", String.class);
                                            String user = context.getArgument("user", String.class); // This retrieves the player name, not UUID
                                            String command = context.getArgument("command", String.class);

                                            if (type.equalsIgnoreCase("bungee") || type.equalsIgnoreCase("velocity")) {
                                                ChannelSender.sendPluginMessage("plugin:cloudsync", user, command); // `user` is the player name
                                                Helper.printChatMessage("Command sent to " + type + "!");
                                            } else {
                                                Helper.printChatMessage("Invalid type specified!");
                                            }

                                            return SINGLE_SUCCESS;
                                        })
                                )
                        )
                );
    }
}
