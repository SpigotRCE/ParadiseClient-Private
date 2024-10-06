package io.github.spigotrce.paradiseclientprivate;

import io.github.spigotrce.paradiseclientfabric.Constants;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.ParadiseClient_Fabric;
import io.github.spigotrce.paradiseclientfabric.exploit.Exploit;
import io.github.spigotrce.paradiseclientprivate.command.*;
import io.github.spigotrce.paradiseclientprivate.exploit.ViaVersionExploit;
import io.github.spigotrce.paradiseclientprivate.listener.PacketListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.impl.networking.RegistrationPayload;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class Main implements ModInitializer {

    public static final ArrayList<String> channelsToRegisterToBridge = new ArrayList<>();
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
            registerExploits();

            ParadiseClient_Fabric.getEventManager().registerListener(new PacketListener());
        }).start();
    }

    private void registerCommands() {
        ParadiseClient_Fabric.getCommandManager().register(new AuthMeBypassCommand(MinecraftClient.getInstance()));
        ParadiseClient_Fabric.getCommandManager().register(new SignedVelocityCommand(MinecraftClient.getInstance()));
        ParadiseClient_Fabric.getCommandManager().register(new ChatSentryCommand(MinecraftClient.getInstance()));
        ParadiseClient_Fabric.getCommandManager().register(new DumpCommand(MinecraftClient.getInstance()));
        ParadiseClient_Fabric.getCommandManager().register(new CloudSyncCommand(MinecraftClient.getInstance()));
        ParadiseClient_Fabric.getCommandManager().register(new LuckPermsCommand(MinecraftClient.getInstance()));
        ParadiseClient_Fabric.getCommandManager().register(new ECBCommand(MinecraftClient.getInstance()));
    }

    private void registerExploits() {
        ParadiseClient_Fabric.getExploitManager().register(new ViaVersionExploit(MinecraftClient.getInstance()));
    }
}
