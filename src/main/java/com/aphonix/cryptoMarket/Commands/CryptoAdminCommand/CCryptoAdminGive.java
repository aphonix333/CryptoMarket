package com.aphonix.cryptoMarket.Commands.CryptoAdminCommand;

import com.aphonix.cryptoMarket.CryptoCore.Crypto;
import com.aphonix.cryptoMarket.Data.PlayerData;
import com.aphonix.cryptoMarket.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.UUID;

public class CCryptoAdminGive {
    private final Main plugin;
    private final Crypto crypto;

    public CCryptoAdminGive(Main plugin) {
        this.plugin = plugin;
        this.crypto = new Crypto(plugin);
    }

    public boolean execute(CommandSender sender, String[] args) {
        YamlConfiguration messages = this.plugin.getMessagesFile();
        if (!sender.hasPermission(plugin.getConfig().getString("BypassAllAdminCommand")) && !sender.hasPermission(plugin.getConfig().getString("CommandCryptoAdminGive"))) {
            String msg0 = messages.getString("NoPermissionMessage");
            if (msg0 != null) {
                msg0 = ChatColor.translateAlternateColorCodes('&', msg0);
            }

            sender.sendMessage(msg0);
            return true;
        } else {
            String Token = this.plugin.getConfig().getString("Token Name");
            if (args.length >= 3) {
                String currency = args[2];
                String playerName = args[3];
                String amountStr = args[4];
                OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);
                UUID uuid = target.getUniqueId();
                double amount = Double.parseDouble(amountStr);
                PlayerData data = new PlayerData(this.plugin, uuid);
                double currentTokens = data.getToken();
                data.setToken(currentTokens + amount);
                double TokenLeft = this.plugin.getConfig().getDouble("Tokens left");
                double AllTokens = this.plugin.getConfig().getDouble("Token all tokens");
                double TrueTokenLeft = TokenLeft - amount;
                double TrueTokenAllToknes = AllTokens + amount;
                this.plugin.getConfig().set("Tokens left", TrueTokenLeft);
                this.plugin.getConfig().set("Token all tokens", TrueTokenAllToknes);
                String msg = messages.getString("CommandAdminGive");
                if (msg != null) {
                    msg = msg.replaceAll("%AMOUNT%", amountStr).replaceAll("%TOKEN%", Token);
                    msg = msg.replaceAll("%PLAYERNAME", playerName);
                    msg = ChatColor.translateAlternateColorCodes('&', msg);
                }

                sender.sendMessage(msg);
                String msg1 = messages.getString("CommandAdminGive2");
                if (msg1 != null) {
                    msg1 = msg1.replaceAll("%TOKENLEFT%", String.valueOf(TrueTokenLeft)).replaceAll("%TOKENALLTOKNES", String.valueOf(TrueTokenAllToknes));
                    msg1 = ChatColor.translateAlternateColorCodes('&', msg1);
                }

                sender.sendMessage(msg1);
                return true;
            } else {
                String msg = messages.getString("CommandAdminGiveCorrectUsage");
                if (msg != null) {
                    msg = ChatColor.translateAlternateColorCodes('&', msg);
                    return true;
                } else {
                    sender.sendMessage("§cError!");
                    return true;
                }
            }
        }
    }
}
