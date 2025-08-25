package com.aphonix.cryptoMarket.Commands;

import com.aphonix.cryptoMarket.Commands.CryptoAdminCommand.*;
import com.aphonix.cryptoMarket.Commands.CryptoCommand.CCryptoBuy;
import com.aphonix.cryptoMarket.Commands.CryptoCommand.CCryptoGet;
import com.aphonix.cryptoMarket.Commands.CryptoCommand.CCryptoSell;
import com.aphonix.cryptoMarket.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

public class CryptoCommandCore implements CommandExecutor {
    private final CCryptoBuy cryptoBuy;
    private final CCryptoHelp cryptoHelp;
    private final CCryptoSell cryptoSell;
    private final CCryptoGet cryptoGet;
    private final CCryptoAdminAdd cryptoAdminAdd;
    private final CCryptoAdminGive cryptoAdminGive;
    private final CCryptoAdminSubtract cryptoAdminSubtract;
    private final CCryptoAdminTake cryptoAdminTake;
    private final CCryptoAdminReload cryptoAdminReload;
    private final Main plugin;

    public CryptoCommandCore(Main plugin) {
        this.cryptoHelp = new CCryptoHelp(plugin);
        this.cryptoBuy = new CCryptoBuy(plugin);
        this.cryptoSell = new CCryptoSell(plugin);
        this.cryptoGet = new CCryptoGet(plugin);
        this.cryptoAdminAdd = new CCryptoAdminAdd(plugin);
        this.cryptoAdminGive = new CCryptoAdminGive(plugin);
        this.cryptoAdminSubtract = new CCryptoAdminSubtract(plugin);
        this.cryptoAdminTake = new CCryptoAdminTake(plugin);
        this.cryptoAdminReload = new CCryptoAdminReload(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlConfiguration messages = this.plugin.getMessagesFile();

        if (args.length == 0) {
            String adminUsage = messages.getString("CommandAdminUsage");
            String usage = messages.getString("CommandUsage");

            if (usage != null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
            }
            if (adminUsage != null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', adminUsage));
            }
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "help":
                return this.cryptoHelp.execute(sender, args);

            case "buy":
                return this.cryptoBuy.execute(sender, args);

            case "sell":
                return this.cryptoSell.execute(sender, args);

            case "get":
                return this.cryptoGet.execute(sender, args);

            case "admin":
                if (args.length < 2) {
                    String adminUsage = messages.getString("CommandAdminUsage");
                    if (adminUsage != null) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', adminUsage));
                    }
                    return true;
                }
                switch (args[1].toLowerCase()) {
                    case "add":
                        return this.cryptoAdminAdd.execute(sender, args);
                    case "give":
                        return this.cryptoAdminGive.execute(sender, args);
                    case "subtract":
                        return this.cryptoAdminSubtract.execute(sender, args);
                    case "take":
                        return this.cryptoAdminTake.execute(sender, args);
                    case "reload":
                        return this.cryptoAdminReload.execute(sender, args);
                }
                return true;

            default:
                String usage = messages.getString("CommandUsage");
                if (usage != null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
                }
                return true;
        }
    }
}
