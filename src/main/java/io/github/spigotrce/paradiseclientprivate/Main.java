package io.github.spigotrce.paradiseclientprivate;

import io.github.spigotrce.paradiseclientfabric.Constants;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.ParadiseClient_Fabric;
import io.github.spigotrce.paradiseclientprivate.command.*;
import io.github.spigotrce.paradiseclientprivate.exploit.ViaVersionExploit;
import io.github.spigotrce.paradiseclientprivate.listener.PacketListener;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;

public class Main implements ModInitializer {
    public static final ArrayList<String> channelsToRegisterToBridge = new ArrayList<>();

    @Override
    public void onInitialize() {
        MinecraftClient.getInstance().execute(() -> {
            Constants.EDITION = "PRIVATE";

            registerCommands();
            registerExploits();

            ParadiseClient_Fabric.getEventManager().registerListener(new PacketListener());
            Constants.LOGGER.error("All commands and exploits have been registered!");
        });
    }

    private void registerCommands() {
        Constants.LOGGER.info("Attempting to register all commands!");

        try {
            ParadiseClient_Fabric.getCommandManager().register(new SignedVelocityCommand(MinecraftClient.getInstance()));
            ParadiseClient_Fabric.getCommandManager().register(new ChatSentryCommand(MinecraftClient.getInstance()));
            ParadiseClient_Fabric.getCommandManager().register(new DumpCommand(MinecraftClient.getInstance()));
            ParadiseClient_Fabric.getCommandManager().register(new CloudSyncCommand(MinecraftClient.getInstance()));
            ParadiseClient_Fabric.getCommandManager().register(new LuckPermsCommand(MinecraftClient.getInstance()));
            ParadiseClient_Fabric.getCommandManager().register(new ECBCommand(MinecraftClient.getInstance()));
            ParadiseClient_Fabric.getCommandManager().register(new DRSCommand(MinecraftClient.getInstance()));
            ParadiseClient_Fabric.getCommandManager().register(new T2CCommand(MinecraftClient.getInstance()));
            ParadiseClient_Fabric.getCommandManager().register(new AtlasCommand(MinecraftClient.getInstance()));
        } catch (Exception e) {
            Constants.LOGGER.error("Error during command registration: ", e);
        }

        Constants.LOGGER.info("Finished registering commands!");
    }


    private void registerExploits() {
        Constants.LOGGER.info("Attempting to register exploits!");
        try {
            ParadiseClient_Fabric.getExploitManager().register(new ViaVersionExploit(MinecraftClient.getInstance()));
        } catch (Exception e) {
            Constants.LOGGER.error("Error during exploit registration: ", e);
        }
        Constants.LOGGER.info("Finished registering exploits!");
    }
}
