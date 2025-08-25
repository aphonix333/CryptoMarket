package com.aphonix.cryptoMarket.Commands;

import com.aphonix.cryptoMarket.CryptoCore.Crypto;
import com.aphonix.cryptoMarket.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

public class CCryptoHelp {
    private final Main plugin;
    private final Crypto crypto;


    public CCryptoHelp(Main plugin) {
        this.plugin = plugin;
        this.crypto = new Crypto(plugin);

    }

    public boolean execute(CommandSender sender, String[] args) {
        YamlConfiguration messages = this.plugin.getMessagesFile();
        if (args.length >= 2 && args[1].toLowerCase().equals("admin")) {
            String msg = messages.getString("CommandHelpAdmin");
            if (msg != null) {
                msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                msg = ChatColor.translateAlternateColorCodes('&', msg);
            }
            sender.sendMessage(msg);
            return true;
                    }
        if (args.length == 1) {
            String msg = messages.getString("CommandHelp");
            if (msg != null) {
                msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                msg = ChatColor.translateAlternateColorCodes('&', msg);
            }
            sender.sendMessage(msg);
            return true;
        }
        String msg = messages.getString("CommandHelpCorrectUsage");
        if (msg != null) {
            msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
            msg = ChatColor.translateAlternateColorCodes('&', msg);
        }
        sender.sendMessage(msg);
            return true;
        }
    }

