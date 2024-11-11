package io.github.spigotrce.paradiseclientprivate.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.command.Command;
import io.github.spigotrce.paradiseclientprivate.exploit.ViaVersionExploit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;

public class ViaVersionCommand extends Command {
    public ViaVersionCommand(MinecraftClient minecraftClient) {
        super("viaversion", "ViaVersion crasher", minecraftClient);
    }

    @Override
    public LiteralArgumentBuilder<CommandSource> build() {
        return literal(getName())
                .executes(context -> {
                    Helper.printChatMessage("Executing ViaVersion exploit!");
                    new ViaVersionExploit(getMinecraftClient()).execute();
                    return SINGLE_SUCCESS;
                });
    }
}
