package io.github.spigotrce.paradiseclientprivate.listener;

import io.github.spigotrce.eventbus.event.EventHandler;
import io.github.spigotrce.eventbus.event.listener.Listener;
import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.event.channel.ChannelRegisterEvent;
import io.github.spigotrce.paradiseclientfabric.event.packet.incoming.PacketIncomingPreEvent;
import io.github.spigotrce.paradiseclientprivate.packets.ParadiseBridgePayloadPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;

import java.util.ArrayList;
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

    @EventHandler
    public void onChannelRegister(ChannelRegisterEvent event) {
        String channel = event.getChannel();
        if (channel.equals("plugin:cloudsync")) {
            event.setMessage(event.getMessage() + " §4might be exploitable!");
        }

        ArrayList<String> channelList = new ArrayList<>();
        channelList.add(channel);

        Helper.sendPacket(
                new CustomPayloadC2SPacket(
                        new ParadiseBridgePayloadPacket(
                            "channel-register",
                                channelList
                        )
                )
        );
    }
}
