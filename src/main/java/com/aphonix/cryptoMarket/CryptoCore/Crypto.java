package com.aphonix.cryptoMarket.CryptoCore;

import org.bukkit.plugin.java.JavaPlugin;



// MAYBE IN THE FUTURE THIS WILL BE USED


public class Crypto {
    private final JavaPlugin plugin;

    public Crypto(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public double getTokenPrice() {
        return this.plugin.getConfig().getDouble("Token price");
    }

    public double getTokenLeft() {
        return this.plugin.getConfig().getDouble("Tokens left");
    }

    public double getTokenName() {
        return this.plugin.getConfig().getDouble("Token Name");
    }

    public double getTokenAllTokens() {
        return this.plugin.getConfig().getDouble("Token all tokens");
    }

    public double getTokenCapitalization() {
        return this.plugin.getConfig().getDouble("Token Capitalization");
    }

    public double getTokenTax() {
        return this.plugin.getConfig().getDouble("BTC tax");
    }

    public double getTrueTokenTax() {
        double btcTax = this.getTokenTax() / (double)100.0F;
        return (double)1.0F - btcTax;
    }

    public void setTokenPrice(double price) {
        this.plugin.getConfig().set("Token price", price);
    }

    public void setTokenLeft(double left) {
        this.plugin.getConfig().set("Tokens left", left);
    }

    public void setTokenAllTokens(double allTokens) {
        this.plugin.getConfig().set("Token all tokens", allTokens);
    }

    public void setTokenCapitalization(double capitalization) {
        this.plugin.getConfig().set("Token Capitalization", capitalization);
    }

    public void setTokenName(String name) {
        this.plugin.getConfig().set("Token Name", name);
    }

    public void setTokenTax(double tax) {
        this.plugin.getConfig().set("BTC tax", tax);
    }

    public void save() {
        this.plugin.saveConfig();
    }
}
