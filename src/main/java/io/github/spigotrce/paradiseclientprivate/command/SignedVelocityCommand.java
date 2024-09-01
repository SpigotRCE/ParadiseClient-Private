package io.github.spigotrce.paradiseclientprivate.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.command.Command;
import io.github.spigotrce.paradiseclientprivate.packets.SignedVelocityPayloadPacket;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;

public class SignedVelocityCommand extends Command {
    public SignedVelocityCommand(MinecraftClient minecraftClient) {
        super("paradisesignedvelocity", "Spoofs player sent commands", minecraftClient);
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> build() {
        return literal(getName())
                .executes(context -> {
                    Helper.printChatMessage("Incomplete command!");
                    return SINGLE_SUCCESS;
                })
                .then(ClientCommandManager.argument("user", StringArgumentType.word())
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
                                    .filter(player -> player.getName().toLowerCase().startsWith(finalPartialName.toLowerCase()))
                                    .forEach(profile -> builder.suggest(profile.getName()));

                            return builder.buildFuture();
                        })
                        .executes(context -> {
                            Helper.printChatMessage("Incomplete command!");
                            return SINGLE_SUCCESS;
                        })
                        .then(ClientCommandManager.argument("command", StringArgumentType.greedyString())
                                .executes(context -> {
                                    String user = context.getArgument("user", String.class);
                                    for (PlayerListEntry p : getMinecraftClient().getNetworkHandler().getPlayerList()) {
                                        if (p.getProfile().getName().equalsIgnoreCase(user)) {
                                            getMinecraftClient().getNetworkHandler().sendPacket(new CustomPayloadC2SPacket(new SignedVelocityPayloadPacket(
                                                    p.getProfile().getId().toString(), context.getArgument("command", String.class)
                                            )));
                                            return SINGLE_SUCCESS;
                                        }
                                    }

                                    Helper.printChatMessage("Player not found!");
                                    return SINGLE_SUCCESS;
                                })
                        )
                );
    }
}
