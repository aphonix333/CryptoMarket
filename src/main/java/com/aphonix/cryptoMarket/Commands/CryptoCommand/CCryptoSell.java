package com.aphonix.cryptoMarket.Commands.CryptoCommand;

import com.aphonix.cryptoMarket.Main;
import com.aphonix.cryptoMarket.CryptoCore.Crypto;
import com.aphonix.cryptoMarket.Data.PlayerData;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CCryptoSell {
    private final Main plugin;
    private final Crypto crypto;
    private double TokenPriceAfterSell;
    private double TokenTakeAfterSell;
    private double MoneyAfterSell;

    public CCryptoSell(Main plugin) {
        this.plugin = plugin;
        this.crypto = new Crypto(plugin);
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true; }
        if (args.length >= 3) {
            String currency = args[1];
            String amountStr = args[2];
            Player player = Bukkit.getPlayer(sender.getName());
            new PlayerData(this.plugin, player.getUniqueId());
            YamlConfiguration messages = this.plugin.getMessagesFile();
            String Token = this.plugin.getConfig().getString("Token Name");
            if (currency.equalsIgnoreCase(Token)) {
                if (this.plugin.getConfig().getBoolean("RequirePlayerCommandsPermissions") && !player.hasPermission(this.plugin.getConfig().getString("CommandCryptoBuy"))) {
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
                    PlayerData data = new PlayerData(this.plugin, player.getUniqueId());
                    double Get = data.getToken();
                    double TokenName = Get - amount;
                    if (amount > Get) {
                        String msg = messages.getString("CommandSellNoTokenBall");
                        if (msg != null) {
                            msg = msg.replaceAll("%AMOUNT%", amountStr);
                            msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                            msg = ChatColor.translateAlternateColorCodes('&', msg);
                        }

                        player.sendMessage(msg);
                        return true;
                    }

                    if (amount <= 1.0E-14) {
                        String msg = messages.getString("CommandSellMinimalValue");
                        if (msg != null) {
                            msg = msg.replaceAll("%AMOUNT%", amountStr);
                            msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                            msg = ChatColor.translateAlternateColorCodes('&', msg);
                        }

                        player.sendMessage(msg);
                        return true;
                    }

                    double TokenTax = this.plugin.getConfig().getDouble("Token Sell Tax");
                    double TokenTax2 = TokenTax / 100.0;
                    double TrueTokenTax = 1.0 - TokenTax2;
                    double TokenCapitalizationSellTax = this.plugin.getConfig().getDouble("Capitalization Tax");
                    double TokenCapitalizationSellTax2 = TokenCapitalizationSellTax /100.0;
                    double TrueTokenCapitalizationSellTax = 1.0 - TokenCapitalizationSellTax2;
                    double TokenPrice = this.plugin.getConfig().getDouble("Token price");
                    double alltokens = this.plugin.getConfig().getDouble("Token all tokens");
                    double TokenCapitalization = this.plugin.getConfig().getDouble("Token Capitalization");
                    double TokensLeft = this.plugin.getConfig().getDouble("Tokens left");
                    double TokenCapitalizationAfterSell = TokenCapitalization - amount * TokenPrice;
                    if (TokenCapitalization == 0.0) {
                        String msg = messages.getString("CommandSellNoCapitalization");
                        if (msg != null) {
                            msg = msg.replaceAll("%AMOUNT%", amountStr);
                            msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                            msg = ChatColor.translateAlternateColorCodes('&', msg);
                        }

                        player.sendMessage(msg);
                        return true;
                    }

                    if (TokenCapitalization < 0.0) {
                        String msg = messages.getString("CommandSellNoCapitalization");
                        if (msg != null) {
                            msg = msg.replaceAll("%AMOUNT%", amountStr);
                            msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                            msg = ChatColor.translateAlternateColorCodes('&', msg);
                        }

                        player.sendMessage(msg);
                        return true;
                    }

                    if (TokenCapitalization > 0.0) {
                        this.TokenPriceAfterSell = (TokenCapitalizationAfterSell / alltokens) * TrueTokenCapitalizationSellTax;
                        this.MoneyAfterSell = amount * TokenPrice * TrueTokenTax;
                        econ.depositPlayer(player, this.MoneyAfterSell);
                    }

                    double TrueTokenLeft = TokensLeft + amount;
                    data.setToken(TokenName);
                    this.plugin.getConfig().set("Tokens left", TrueTokenLeft);
                    this.plugin.getConfig().set("Token price", this.TokenPriceAfterSell);
                    this.plugin.getConfig().set("Token Capitalization", TokenCapitalizationAfterSell);
                    this.plugin.saveConfig();
                    String msg = messages.getString("CommandSellAfterSell");
                    if (msg != null) {
                        msg = msg.replaceAll("%AMOUNT%", amountStr);
                        msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                        msg = msg.replaceAll("%MONEYAFTERSELL", String.valueOf(this.MoneyAfterSell));
                        msg = msg.replaceAll("%COURSEBEFORESELL%", String.valueOf(TokenPrice));
                        msg = msg.replaceAll("%COURSEAFTERSELL%", String.valueOf(this.TokenPriceAfterSell));
                        msg = ChatColor.translateAlternateColorCodes('&', msg);
                    }

                    player.sendMessage(msg);
                } catch (NumberFormatException var36) {
                    String msg = messages.getString("CommandSellCorrectUsage");
                    if (msg != null) {
                        msg = msg.replaceAll("%AMOUNT%", amountStr);
                        msg = msg.replaceAll("%TOKEN%", this.plugin.getConfig().getString("Token Name"));
                        msg = ChatColor.translateAlternateColorCodes('&', msg);
                    }

                    player.sendMessage(msg);
                }
            }
        }


        return true;
    }
}
