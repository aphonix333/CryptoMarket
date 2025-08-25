package com.aphonix.cryptoMarket.Commands.CryptoAdminCommand;

import com.aphonix.cryptoMarket.Main;
import com.aphonix.cryptoMarket.CryptoCore.Crypto;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CCryptoAdminSubtract {
    private final Main plugin;
    private final Crypto crypto;

    public CCryptoAdminSubtract(Main plugin) {
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
            if (args.length >= 5) {
                String currency = args[2];
                String type = args[3];
                String amountStr = args[4];
                Player player = Bukkit.getPlayer(sender.getName());
                double amount = Double.parseDouble(amountStr);
                double TokensLeft = this.plugin.getConfig().getDouble("Tokens left");
                double Capitalization = this.plugin.getConfig().getDouble("Token Capitalization");
                double AllToknes = this.plugin.getConfig().getDouble("Token all tokens");
                double TokenPrice = this.plugin.getConfig().getDouble("Token price");
                if (currency.equalsIgnoreCase(Token) && type.equalsIgnoreCase("capitalization")) {
                    double CapitalizationAfterSubtract = Capitalization - amount;
                    double TokenPriceAfterSubtract = CapitalizationAfterSubtract / AllToknes;
                    String msg = messages.getString("CommandAdminSubtractCapitalization");
                    if (msg != null) {
                        msg = msg.replaceAll("%AMOUNT%", amountStr);
                        msg = msg.replaceAll("%CAPITALIZATIONBEFORE%", String.valueOf(Capitalization));
                        msg = msg.replaceAll("%CAPITALIZATIONAFTER%", String.valueOf(CapitalizationAfterSubtract));
                        msg = msg.replaceAll("%TOKENPRICEBEFORE%", String.valueOf(TokenPrice));
                        msg = msg.replaceAll("%TOKENPRICEAFTER%", String.valueOf(TokenPriceAfterSubtract));
                        msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                        msg = ChatColor.translateAlternateColorCodes('&', msg);
                    }

                    player.sendMessage(msg);
                    this.plugin.getConfig().set("Token Capitalization", CapitalizationAfterSubtract);
                    this.plugin.getConfig().set("Token price", TokenPriceAfterSubtract);
                    this.plugin.saveConfig();
                }

                if (currency.equalsIgnoreCase(Token) && type.equalsIgnoreCase("tokens")) {
                    double AllToknesAfterSubtract = AllToknes - amount;
                    double TokensLeftAfterSubtract = TokensLeft - amount;
                    double TokenPriceAfterSubtract = Capitalization / AllToknesAfterSubtract;
                    player.sendMessage("Sucefully added " + AllToknes + " to " + Token + "tokens.");
                    String msg = messages.getString("CommandAdminSubtractCapitalization");
                    if (msg != null) {
                        msg = msg.replaceAll("%AMOUNT%", amountStr);
                        msg = msg.replaceAll("%TOKENSBEFORE%", String.valueOf(TokensLeft));
                        msg = msg.replaceAll("%TOKENSAFTER%", String.valueOf(TokensLeftAfterSubtract));
                        msg = msg.replaceAll("%ALLTOKENSBEFORE%", String.valueOf(AllToknes));
                        msg = msg.replaceAll("%ALLTOKENSAFTER%", String.valueOf(AllToknesAfterSubtract));
                        msg = msg.replaceAll("%TOKENPRICEBEFORE%", String.valueOf(TokenPrice));
                        msg = msg.replaceAll("%TOKENPRICEAFTER%", String.valueOf(TokenPriceAfterSubtract));
                        msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                        msg = ChatColor.translateAlternateColorCodes('&', msg);
                    }

                    player.sendMessage(msg);
                    this.plugin.getConfig().set("Token all tokens", AllToknesAfterSubtract);
                    this.plugin.getConfig().set("Tokens left", TokensLeftAfterSubtract);
                    this.plugin.getConfig().set("Token price", TokenPriceAfterSubtract);
                    this.plugin.saveConfig();
                }

                String msg = messages.getString("CommandAdminSubtractCapitalization");
                if (msg != null) {
                    msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                    msg = ChatColor.translateAlternateColorCodes('&', msg);
                }

                player.sendMessage(msg);
                return true;
            } else {
                return true;
            }
        }
    }
}
