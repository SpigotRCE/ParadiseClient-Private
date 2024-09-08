package io.github.spigotrce.paradiseclientprivate.mixin.inject;

import io.github.spigotrce.paradiseclientfabric.Helper;
import net.fabricmc.fabric.impl.networking.RegistrationPayload;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(RegistrationPayload.class)
public abstract class RegistrationPayloadMixin {
    @Inject(method = "addId", at = @At(value = "HEAD"), remap = false)
    private static void addId(List<Identifier> ids, StringBuilder sb, CallbackInfo ci) {
        String channel = sb.toString();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!channel.isEmpty()) Helper.printChatMessage("§9§lChannel: §a§l" + channel);

            switch (channel) {
                case "signedvelocity:main", "chatsentry:datasync", "authmevelocity:main" ->
                        sendVulnerabilityMessage(channel);
            }
        }).start();
    }

    @Unique
    private static void sendVulnerabilityMessage(String channel) {
        Helper.printChatMessage("§4§lKnown vulnerability channel found: §e§l" + channel);
    }

    @Shadow
    public abstract List<Identifier> channels();
}