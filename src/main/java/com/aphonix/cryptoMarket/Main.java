package com.aphonix.cryptoMarket;

import com.aphonix.cryptoMarket.Commands.CryptoTab;
import com.aphonix.cryptoMarket.Commands.CryptoCommandCore;
import com.aphonix.cryptoMarket.CryptoCore.Crypto;
import com.aphonix.cryptoMarket.Events.PlayerJoinListener;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;
import java.io.File;

public class Main extends JavaPlugin {
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    private YamlConfiguration messagesFile;
    public boolean debug = this.getConfig().getBoolean("DebugMode");

    public Main() {
    }

    public static Economy getEconomy() {
        return econ;
    }

    public void onEnable() {
        if (!this.setupEconomy()) {
            this.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", this.getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
        } else {
            this.saveDefaultConfig();
            this.reloadConfig();
            this.setupPermissions();
            this.setupChat();
            this.getCommand("crypto").setExecutor(new CryptoCommandCore(this));
            this.getCommand("crypto").setTabCompleter(new CryptoTab(this));
            Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
            new Crypto(this);
            File file = new File(this.getDataFolder(), "messages.yml");
            if (!file.exists()) {
                this.saveResource("messages.yml", false);
            }
            this.messagesFile = YamlConfiguration.loadConfiguration(file);
            Metrics metrics = new Metrics(this, 27043);
        }
    }

    public YamlConfiguration getMessagesFile() {
        return this.messagesFile;
    }

    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                return false;
            } else {
                econ = (Economy)rsp.getProvider();
                return econ != null;
            }
        }
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = this.getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp == null) {
            return false;
        } else {
            chat = (Chat)rsp.getProvider();
            return chat != null;
        }
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = this.getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        } else {
            perms = (Permission)rsp.getProvider();
            return perms != null;
        }
    }
}
