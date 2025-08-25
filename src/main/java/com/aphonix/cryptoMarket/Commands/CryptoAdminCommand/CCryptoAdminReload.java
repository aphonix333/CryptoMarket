package com.aphonix.cryptoMarket.Commands.CryptoAdminCommand;

import com.aphonix.cryptoMarket.Main;
import com.aphonix.cryptoMarket.CryptoCore.Crypto;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CCryptoAdminReload {
    private final Main plugin;
    private final Crypto crypto;

    public CCryptoAdminReload(Main plugin) {
        this.plugin = plugin;
        this.crypto = new Crypto(plugin);
    }

    public boolean execute(CommandSender sender, String[] args) {
        YamlConfiguration messages = this.plugin.getMessagesFile();
        if (!sender.hasPermission(plugin.getConfig().getString("BypassAllAdminCommand")) && !sender.hasPermission(plugin.getConfig().getString("CommandCryptoAdminReload"))) {
            String msg0 = messages.getString("NoPermissionMessage");
            if (msg0 != null) {
                msg0 = ChatColor.translateAlternateColorCodes('&', msg0);
            }

            sender.sendMessage(msg0);
            return true;
        } else {
            Player player = (Player) sender;
            String msg = messages.getString("CommandAdminReload");
            if (msg != null) {
                msg = ChatColor.translateAlternateColorCodes('&', msg);
            }

            player.sendMessage(msg);
            this.plugin.reloadConfig();
            String msg1 = messages.getString("CommandAdminReload2");
            if (msg1 != null) {
                msg1 = ChatColor.translateAlternateColorCodes('&', msg1);
            }

            player.sendMessage(msg1);
            this.plugin.saveConfig();
            String msg2 = messages.getString("CommandAdminReloadSuccessful");
            if (msg2 != null) {
                msg2 = ChatColor.translateAlternateColorCodes('&', msg2);
            }

            player.sendMessage(msg2);
            return true;
        }
    }
}
