package io.github.spigotrce.paradiseclientprivate;

import io.github.spigotrce.paradiseclientfabric.Constants;
import io.github.spigotrce.paradiseclientfabric.ParadiseClient_Fabric;
import io.github.spigotrce.paradiseclientprivate.command.*;
import io.github.spigotrce.paradiseclientprivate.listener.PacketListener;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

public class Main implements ModInitializer {

    @Override
    public void onInitialize() {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                Constants.LOGGER.error("Failed to initialize!");
            }
            Constants.EDITION = "PRIVATE";
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            registerCommands();

            ParadiseClient_Fabric.getEventManager().registerListener(new PacketListener());
        }).start();
    }

    private void registerCommands() {
        ParadiseClient_Fabric.getCommandManager().register(new AuthMeBypassCommand(MinecraftClient.getInstance()));
        ParadiseClient_Fabric.getCommandManager().register(new SignedVelocityCommand(MinecraftClient.getInstance()));
        ParadiseClient_Fabric.getCommandManager().register(new ChatSentryCommand(MinecraftClient.getInstance()));
        ParadiseClient_Fabric.getCommandManager().register(new VelocityReportCommand(MinecraftClient.getInstance()));
        ParadiseClient_Fabric.getCommandManager().register(new DumpCommand(MinecraftClient.getInstance()));
    }
}
