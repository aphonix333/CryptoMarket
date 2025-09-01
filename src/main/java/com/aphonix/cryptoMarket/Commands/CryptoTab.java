package com.aphonix.cryptoMarket.Commands;

import com.aphonix.cryptoMarket.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;




public class CryptoTab implements TabCompleter {
    private final Main plugin;

    public CryptoTab(Main plugin) {
        this.plugin = plugin;
    }



    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> results = new ArrayList<>();

        if (args.length == 1) {
            results.add("buy");
            results.add("get");
            results.add("sell");
            results.add("help");
            results.add("admin");
        }
// Buy
        else if (args.length == 2 && args[0].equalsIgnoreCase("buy")) {
            results.add(plugin.getConfig().getString("Token Name"));
        }
        else if (args.length == 3 && args[0].equalsIgnoreCase("buy")) {
            results.add(plugin.getConfig().getString("amountin$"));
        }
// Sell
        else if (args.length == 2 && args[0].equalsIgnoreCase("sell")) {
            results.add(plugin.getConfig().getString("Token Name"));
        }
          else if (args.length == 3 && args[0].equalsIgnoreCase("sell")) {
            results.add(plugin.getConfig().getString("amountintoken"));
        }
// Help
        else if (args.length == 2 && args[0].equalsIgnoreCase("help")) {
            results.add("admin");
        }
// Admin
          else if (args.length == 2 && args[0].equalsIgnoreCase("admin")) {
              results.add("add");
              results.add("give");
              results.add("reload");
              results.add("subtract");
              results.add("take");
        }
// Add
        else if (args.length == 3 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("add")) {
            results.add(plugin.getConfig().getString("Token Name"));
        }
        else if (args.length == 4 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("add")) {
            results.add(plugin.getConfig().getString("tokens"));
            results.add(plugin.getConfig().getString("capitalization"));
        }
        else if (args.length == 5 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("add")) {
            results.add(plugin.getConfig().getString("amount"));
        }
// Give
        else if (args.length == 3 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("give")) {
            results.add(plugin.getConfig().getString("Token Name"));
        }
        else if (args.length == 4 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("give")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                results.add(player.getName());
            }
        }
        else if (args.length == 5 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("give")) {
            results.add(plugin.getConfig().getString("amount"));
        }
// Subtract
        else if (args.length == 3 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("subtract")) {
            results.add(plugin.getConfig().getString("Token Name"));
        }
        else if (args.length == 4 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("subtract")) {
            results.add(plugin.getConfig().getString("tokens"));
            results.add(plugin.getConfig().getString("capitalization"));
        }
        else if (args.length == 5 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("subtract")) {
            results.add(plugin.getConfig().getString("amount"));
        }
// Take
        else if (args.length == 3 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("take")) {
            results.add(plugin.getConfig().getString("Token Name"));
        }
        else if (args.length == 4 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("take")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                results.add(player.getName());
            }
        }
        else if (args.length == 5 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("take")) {
            results.add(plugin.getConfig().getString("amount"));
        }
        return results;
    }
    }

