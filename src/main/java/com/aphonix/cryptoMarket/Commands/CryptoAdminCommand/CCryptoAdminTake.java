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

public class CCryptoAdminTake {
    private final Main plugin;
    private final Crypto crypto;

    public CCryptoAdminTake(Main plugin) {
        this.plugin = plugin;
        this.crypto = new Crypto(plugin);
    }

    public boolean execute(CommandSender sender, String[] args) {
        YamlConfiguration messages = this.plugin.getMessagesFile();
        if (!sender.hasPermission(plugin.getConfig().getString("BypassAllAdminCommand")) && !sender.hasPermission(plugin.getConfig().getString("CommandCryptoAdminSubtract"))) {
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
                if (currentTokens >= amount) {
                    data.setToken(currentTokens - amount);
                    double TokenLeft = this.plugin.getConfig().getDouble("Tokens left");
                    double AllTokens = this.plugin.getConfig().getDouble("Token all tokens");
                    double TrueTokenLeft = TokenLeft + amount;
                    this.plugin.getConfig().set("Tokens left", TrueTokenLeft);
                    String msg = messages.getString("CommandAdminTake");
                    if (msg != null) {
                        msg = ChatColor.translateAlternateColorCodes('&', msg);
                        msg = msg.replaceAll("%AMOUNT%", amountStr).replaceAll("%TOKEN%", Token);
                        msg = msg.replaceAll("%PLAYERNAME", playerName);
                    }

                    sender.sendMessage(msg);
                    return true;
                }

                if (currentTokens < amount) {
                    String msg5 = messages.getString("CommandAdminTakeWrongAmount");
                    if (msg5 != null) {
                        msg5 = ChatColor.translateAlternateColorCodes('&', msg5);
                    }

                    sender.sendMessage(msg5);
                    return true;
                }
            }

            String msg = messages.getString("CommandAdminTakeCorrectUsage");
            if (msg != null) {
                msg = ChatColor.translateAlternateColorCodes('&', msg);
            }

            sender.sendMessage(msg);
            return true;
        }
    }
}
