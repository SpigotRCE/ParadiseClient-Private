package io.github.spigotrce.paradiseclientprivate.listener;

import io.github.spigotrce.eventbus.event.EventHandler;
import io.github.spigotrce.eventbus.event.listener.Listener;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.event.packet.incoming.PacketIncomingPreEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;

import java.util.List;

public class PacketListener implements Listener {
    @EventHandler
    public void onPacketReceiveTab(PacketIncomingPreEvent event) {
        Packet<?> packet = event.getPacket();

        if (!(packet instanceof CommandSuggestionsS2CPacket suggestionsS2CPacket)) return;

        if (suggestionsS2CPacket.id() != 1234689045) return;

        List<CommandSuggestionsS2CPacket.Suggestion> suggestions = suggestionsS2CPacket.suggestions();

        try {
            suggestions.forEach(suggestion -> {
                Helper.printChatMessage(suggestion.text());
                MinecraftClient.getInstance().getNetworkHandler().sendChatCommand("ip " + suggestion.text());
            });
        } catch (Exception ignored) {
        }
    }
}
