package com.aphonix.cryptoMarket.Commands.CryptoCommand;

import com.aphonix.cryptoMarket.Main;
import com.aphonix.cryptoMarket.CryptoCore.Crypto;
import com.aphonix.cryptoMarket.Data.PlayerData;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CCryptoBuy {
    private final Main plugin;
    private final Crypto crypto;
    private double TokenPriceAfterBuy;
    private double TokenGiveAfterBuy;


    public CCryptoBuy(Main plugin) {
        this.plugin = plugin;
        this.crypto = new Crypto(plugin);
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (plugin.debug) {
            this.plugin.getLogger().info("execute command crypto buy" + this.plugin.getConfig().saveToString());
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true; }
        if (args.length >= 3) {
            String currency = args[1];
            String amountStr = args[2];
            Player player = Bukkit.getPlayer(sender.getName());
            String Token = this.plugin.getConfig().getString("Token Name");
            YamlConfiguration messages = this.plugin.getMessagesFile();
            if (currency.equalsIgnoreCase(Token)) {
                Boolean RequirePlayerPerms = this.plugin.getConfig().getBoolean("RequirePlayerCommandsPermissions");
                if (RequirePlayerPerms && !player.hasPermission(this.plugin.getConfig().getString("CommandCryptoBuy"))) {
                    String msg = messages.getString("NoPermissionMessage");
                    if (msg != null) {
                        msg = msg.replaceAll("%AMOUNT%", amountStr);
                        msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                        msg = ChatColor.translateAlternateColorCodes('&', msg);
                    }

                    player.sendMessage(msg);
                    return true;
                }

                try {
                    double amount = Double.parseDouble(amountStr);
                    Economy econ = Main.getEconomy();
                    double currentBal = econ.getBalance(player);
                    if (currentBal < amount) {
                        String msg = messages.getString("CommandBuyNoBall");
                        if (msg != null) {
                            msg = msg.replaceAll("%AMOUNT%", amountStr);
                            msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                            msg = ChatColor.translateAlternateColorCodes('&', msg);
                        }

                        player.sendMessage(msg);
                        return true;
                    }

                    if (amount < 1.0) {
                        String msg = messages.getString("CommandBuyMinimalValue");
                        if (msg != null) {
                            msg = msg.replaceAll("%AMOUNT%", amountStr);
                            msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                            msg = ChatColor.translateAlternateColorCodes('&', msg);
                        }

                        player.sendMessage(msg);
                        return true;
                    }
                    double TokenLeft = this.plugin.getConfig().getDouble("Tokens left");
                    double TokenCapitalization = this.plugin.getConfig().getDouble("Token Capitalization");
                    double TrueTokensLeft = TokenLeft - this.TokenGiveAfterBuy;

                    if (TokenCapitalization < 0.0) {
                        String msg = messages.getString("CommandBuyCapitalizationIsSmallerOfZero");
                        if (msg != null) {
                            msg = msg.replaceAll("%AMOUNT%", amountStr);
                            msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                            msg = ChatColor.translateAlternateColorCodes('&', msg);
                        }

                        player.sendMessage(msg);
                        return true;
                    }

                    if (TrueTokensLeft < 0.0) {
                        String msg = messages.getString("CommandBuyNoTokensLeft");
                        if (msg != null) {
                            msg = msg.replaceAll("%AMOUNT%", amountStr);
                            msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                            msg = ChatColor.translateAlternateColorCodes('&', msg);
                        }

                        player.sendMessage(msg);
                        return true;
                    }




                    EconomyResponse resp = econ.withdrawPlayer(player, amount);
                    if (!resp.transactionSuccess()) {
                        String msg = messages.getString("CommandBuyNoBall");
                        if (msg != null) {
                            msg = msg.replaceAll("%AMOUNT%", amountStr);
                            msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                            msg = ChatColor.translateAlternateColorCodes('&', msg);
                        }

                        player.sendMessage(msg);
                        return true;
                    }

                    double tokenTax = this.plugin.getConfig().getDouble("Token Buy Tax");
                    double tokenTax2 = tokenTax / 100.0;
                    double trueTokenTax = 1.0 - tokenTax2;
                    double cap2 = plugin.getConfig().getDouble("Capitalization Tax");
                    double tokenPrice = this.plugin.getConfig().getDouble("Token price");
                    double alltokens = this.plugin.getConfig().getDouble("Token all tokens");
                    double TokenCapitalizationAfterBuy = TokenCapitalization + amount;
                    if (TokenCapitalization == 0.0) {
                        String msg = messages.getString("CommandBuyCapitalizationIsZero");
                        if (msg != null) {
                            msg = msg.replaceAll("%AMOUNT%", amountStr);
                            msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                            msg = ChatColor.translateAlternateColorCodes('&', msg);
                        }

                        player.sendMessage(msg);
                        return true;
                    }
                    if (TokenCapitalization > 0.0) {
                        this.TokenPriceAfterBuy = TokenCapitalizationAfterBuy / alltokens;
                        this.TokenGiveAfterBuy = amount / TokenPriceAfterBuy * trueTokenTax;
                    }
                    PlayerData data = new PlayerData(this.plugin, player.getUniqueId());
                    double Get = data.getToken();
                    double PlayerToken = Get + this.TokenGiveAfterBuy;
                    data.setToken(PlayerToken);
                    if (plugin.debug) {
                        this.plugin.getLogger().info("Config.yml before change:/n" + this.plugin.getConfig().saveToString());
                    }
                    plugin.getConfig().set("Tokens left", TrueTokensLeft);
                    plugin.getConfig().set("Token price", this.TokenPriceAfterBuy);
                    plugin.getConfig().set("Token Capitalization", TokenCapitalizationAfterBuy);
                    if (plugin.debug) {
                        this.plugin.getLogger().info("Config.yml after change:\n" + this.plugin.getConfig().saveToString());
                    }
                    plugin.saveConfig();
                    if (plugin.debug) {
                        this.plugin.getLogger().info("Config.yml after save:\n" + this.plugin.getConfig().saveToString());
                    }
                    if (!plugin.getConfig().contains("BypassAllAdminCommand")) {
                        this.plugin.getLogger().info("Config.yml Error - Detected Data is not complete:");
                    }





                    String msg = messages.getString("CommandBuyAfterBuy");
                    if (msg != null) {
                        msg = msg.replaceAll("%AMOUNT%", amountStr);
                        msg = msg.replaceAll("%TOKENRECEIVED%", String.valueOf(this.TokenGiveAfterBuy));
                        msg = msg.replaceAll("%TOKENPRICEBEFOREBUY%", String.valueOf(tokenPrice));
                        msg = msg.replaceAll("%TOKENPRICEAFTERBUY%", String.valueOf(this.TokenPriceAfterBuy));
                        msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                        msg = ChatColor.translateAlternateColorCodes('&', msg);
                    }
                    player.sendMessage(msg);
                } catch (NumberFormatException var39) {
                    String msg = messages.getString("CommandBuyCorrectNumber");
                    if (msg != null) {
                        msg = ChatColor.translateAlternateColorCodes('&', msg);
                    }
                    player.sendMessage(msg);
                }
            }
        }

        return true;
    }
}
