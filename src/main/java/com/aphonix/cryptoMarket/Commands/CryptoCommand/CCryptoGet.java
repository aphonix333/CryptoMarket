
package com.aphonix.cryptoMarket.Commands.CryptoCommand;

import com.aphonix.cryptoMarket.Data.PlayerData;
import com.aphonix.cryptoMarket.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class CCryptoGet {
    private final Main plugin;

    public CCryptoGet(Main plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, String[] args) {
        YamlConfiguration messages = this.plugin.getMessagesFile();
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true; }
            Boolean RequirePlayerPerms = this.plugin.getConfig().getBoolean("RequirePlayerCommandsPermissions");
            if (RequirePlayerPerms && !sender.hasPermission(this.plugin.getConfig().getString("CommandCryptoGet"))) {
                String msg = messages.getString("NoPermissionMessage");
                if (msg != null) {
                    msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                    msg = ChatColor.translateAlternateColorCodes('&', msg);
                }

                sender.sendMessage(msg);
                return true;
            }
         else {
            PlayerData playerData = new PlayerData(this.plugin, player.getUniqueId());

            if (args.length >= 2) {
                String currency = args[1];
                if (currency.equalsIgnoreCase(this.plugin.getConfig().getString("Token Name"))) {
                    double gettoken = playerData.getToken();
                    String msg = messages.getString("CommandGetUsage");
                    if (msg != null) {
                        msg = msg.replaceAll("%PLAYERCRYPTOBAL%", String.valueOf(gettoken));
                        msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                        msg = ChatColor.translateAlternateColorCodes('&', msg);
                        TextComponent message = new TextComponent(msg);
                        message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.valueOf(gettoken)));
                        player.spigot().sendMessage(message);
                    }
                } else {
                    String msg = messages.getString("CommandGetUnknownCurrency");
                    if (msg != null) {
                        msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                        msg = ChatColor.translateAlternateColorCodes('&', msg);
                    }

                    player.sendMessage(msg);
                }
            } else {
                String msg = messages.getString("CommandGetCorrectUsage");
                if (msg != null) {
                    msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                    msg = ChatColor.translateAlternateColorCodes('&', msg);
                }

                player.sendMessage(msg);
            }

            return true;
        }
    }
}
