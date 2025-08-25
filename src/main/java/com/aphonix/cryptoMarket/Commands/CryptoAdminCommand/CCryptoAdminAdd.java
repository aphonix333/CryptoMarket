package com.aphonix.cryptoMarket.Commands.CryptoAdminCommand;

import com.aphonix.cryptoMarket.Main;
import com.aphonix.cryptoMarket.CryptoCore.Crypto;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CCryptoAdminAdd {
    private final Main plugin;
    private final Crypto crypto;
    private double TokenPriceAfterBuy;
    private double TokensGiveAfterBuy;

    public CCryptoAdminAdd(Main plugin) {
        this.plugin = plugin;
        this.crypto = new Crypto(plugin);
    }

    public boolean execute(CommandSender sender, String[] args) {
        YamlConfiguration messages = this.plugin.getMessagesFile();
        if (!sender.hasPermission(plugin.getConfig().getString("BypassAllAdminCommand")) && !sender.hasPermission(plugin.getConfig().getString("CommandCryptoAdminAdd"))) {
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
                    double CapitalizationAfterAdd = Capitalization + amount;
                    double TokenPriceAfterAdd = CapitalizationAfterAdd / AllToknes;
                    String msg = messages.getString("CommandAdminAddCapitalization");
                    if (msg != null) {
                        msg = msg.replaceAll("%AMOUNT%", amountStr);
                        msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                        msg = msg.replaceAll("%CAPITALIZATIONBEFORE%", String.valueOf(Capitalization));
                        msg = msg.replaceAll("%CAPITALIZATIONAFTER%", String.valueOf(CapitalizationAfterAdd));
                        msg = msg.replaceAll("%TOKENPRICEBEFORE%", String.valueOf(TokenPrice));
                        msg = msg.replaceAll("%TOKENPRICEAFTER%", String.valueOf(TokenPriceAfterAdd));
                        msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                        msg = ChatColor.translateAlternateColorCodes('&', msg);
                    }

                    sender.sendMessage(msg);
                    this.plugin.getConfig().set("Token Capitalization", CapitalizationAfterAdd);
                    this.plugin.getConfig().set("Token price", TokenPriceAfterAdd);
                    this.plugin.saveConfig();
                    return true;
                } else if (currency.equalsIgnoreCase(Token) && type.equalsIgnoreCase("tokens")) {
                    double AllToknesAfterAdd = AllToknes + amount;
                    double TokensLeftAfterAdd = TokensLeft + amount;
                    double TokenPriceAfterAdd = Capitalization / AllToknesAfterAdd;
                    String msg = messages.getString("CommandAdminSubtractCapitalization");
                    if (msg != null) {
                        msg = msg.replaceAll("%AMOUNT%", amountStr);
                        msg = msg.replaceAll("%TOKENSBEFORE%", String.valueOf(TokensLeft));
                        msg = msg.replaceAll("%TOKENSAFTER%", String.valueOf(TokensLeftAfterAdd));
                        msg = msg.replaceAll("%ALLTOKENSBEFORE%", String.valueOf(AllToknes));
                        msg = msg.replaceAll("%ALLTOKENSAFTER%", String.valueOf(AllToknesAfterAdd));
                        msg = msg.replaceAll("%TOKENPRICEBEFORE%", String.valueOf(TokenPrice));
                        msg = msg.replaceAll("%TOKENPRICEAFTER%", String.valueOf(TokenPriceAfterAdd));
                        msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                        msg = ChatColor.translateAlternateColorCodes('&', msg);
                    }
                    player.sendMessage(msg);
                    this.plugin.getConfig().set("Token all tokens", AllToknesAfterAdd);
                    this.plugin.getConfig().set("Tokens left", TokensLeftAfterAdd);
                    this.plugin.getConfig().set("Token price", TokenPriceAfterAdd);
                    this.plugin.saveConfig();
                    return true;
                }
                    String msg = messages.getString("CommandAdminAddCorrectUsage");
                    if (msg != null) {
                        msg = msg.replaceAll("%AMOUNT%", amountStr).replaceAll("%TOKEN%", Token);
                        msg = ChatColor.translateAlternateColorCodes('&', msg);
                    }

                    player.sendMessage(msg);
                    return true;
                }
            }
        return true;
    }
    }

