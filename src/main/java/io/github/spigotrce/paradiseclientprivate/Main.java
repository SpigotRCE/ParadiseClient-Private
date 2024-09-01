package io.github.spigotrce.paradiseclientprivate;

import io.github.spigotrce.paradiseclientfabric.Constants;
import io.github.spigotrce.paradiseclientfabric.ParadiseClient_Fabric;
import io.github.spigotrce.paradiseclientprivate.command.AuthMeBypassCommand;
import io.github.spigotrce.paradiseclientprivate.command.SignedVelocityCommand;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

public class Main implements ModInitializer {

    @Override
    public void onInitialize() {
        Constants.EDITION = "PRIVATE";
        registerCommands();
    }

    private void registerCommands() {
        ParadiseClient_Fabric.getCommandManager().register(new AuthMeBypassCommand(MinecraftClient.getInstance()));
        ParadiseClient_Fabric.getCommandManager().register(new SignedVelocityCommand(MinecraftClient.getInstance()));
    }
}
