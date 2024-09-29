package io.github.spigotrce.paradiseclientprivate.mixin.inject;

import io.github.spigotrce.paradiseclientfabric.Helper;
import io.github.spigotrce.paradiseclientfabric.ParadiseClient_Fabric;
import io.github.spigotrce.paradiseclientprivate.Main;
import io.github.spigotrce.paradiseclientprivate.packets.ParadiseBridgePayloadPacket;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Iterator;

@Mixin({ClientPlayNetworkHandler.class})
public abstract class ClientPlayNetworkHandlerMixin {
    public ClientPlayNetworkHandlerMixin() {
    }

    @Inject(
            method = {"onGameJoin"},
            at = {@At("TAIL")}
    )
    private void onGameJoin(GameJoinS2CPacket packet, CallbackInfo info) {
        for (String channel : Main.channelsToRegisterToBridge) {
            ArrayList<String> s = new ArrayList<>();
            s.add(channel);
            Helper.sendPacket(
                    new CustomPayloadC2SPacket(
                            new ParadiseBridgePayloadPacket(
                                    "channel-register",
                                    s
                            )
                    )
            );
        }
    }
}