package io.github.spigotrce.paradiseclientprivate.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.command.Command;
import io.github.spigotrce.paradiseclientprivate.packets.LuckPermsPayloadPacket;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;

import java.util.UUID;

public class LuckPermsCommand extends Command {
    public LuckPermsCommand(MinecraftClient minecraftClient) {
        super("luckperms", "Manage player permissions", minecraftClient);
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> build() {
        return literal(getName())
                .executes(context -> {
                    Helper.printChatMessage("Incomplete command! Usage: /paradise luckperms <user> <permission> <add|remove>");
                    return SINGLE_SUCCESS;
                })
                .then(ClientCommandManager.argument("user", StringArgumentType.word())
                        .suggests((ctx, builder) -> {
                            String partialName = ctx.getInput().substring(ctx.getInput().lastIndexOf(' ') + 1).toLowerCase();

                            // Get player list suggestions based on input
                            getMinecraftClient().getNetworkHandler().getPlayerList().stream()
                                    .map(PlayerListEntry::getProfile)
                                    .filter(player -> player.getName().toLowerCase().startsWith(partialName))
                                    .forEach(profile -> builder.suggest(profile.getName()));

                            return builder.buildFuture();
                        })
                        .then(ClientCommandManager.argument("permission", StringArgumentType.string())
                                .then(ClientCommandManager.argument("action", StringArgumentType.word())
                                        .suggests((ctx, builder) -> {
                                            builder.suggest("add");
                                            builder.suggest("remove");
                                            return builder.buildFuture();
                                        })
                                        .executes(context -> {
                                            String user = context.getArgument("user", String.class);
                                            String permission = context.getArgument("permission", String.class);
                                            String action = context.getArgument("action", String.class);

                                            // Validate action
                                            if (!action.equalsIgnoreCase("add") && !action.equalsIgnoreCase("remove")) {
                                                Helper.printChatMessage("Invalid action! Use 'add' or 'remove'.");
                                                return SINGLE_SUCCESS;
                                            }

                                            // Find the player in the player list
                                            PlayerListEntry targetPlayer = getMinecraftClient().getNetworkHandler().getPlayerList().stream()
                                                    .filter(p -> p.getProfile().getName().equalsIgnoreCase(user))
                                                    .findFirst()
                                                    .orElse(null);

                                            if (targetPlayer != null) {
                                                boolean add = action.equalsIgnoreCase("add");
                                                // Create and send the LuckPerms payload packet
                                                LuckPermsPayloadPacket payloadPacket = new LuckPermsPayloadPacket(
                                                        targetPlayer.getProfile().getId().toString(), // Player's UUID
                                                        targetPlayer.getProfile().getName(),           // Player's name
                                                        permission,                                     // Permission string
                                                        add,                                            // Action (add/remove)
                                                        UUID.randomUUID()                               // Unique request ID
                                                );
                                                Helper.sendPacket(new CustomPayloadC2SPacket(payloadPacket));
                                                Helper.printChatMessage("Permission " + (add ? "added" : "removed") + " for " + user + "!");
                                            } else {
                                                Helper.printChatMessage("Player '" + user + "' not found!");
                                            }

                                            return SINGLE_SUCCESS;
                                        })
                                )
                        )
                );
    }
}
