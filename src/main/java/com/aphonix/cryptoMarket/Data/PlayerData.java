package com.aphonix.cryptoMarket.Data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerData {
    private final File file;
    private final FileConfiguration data;
    private final JavaPlugin plugin;

    public PlayerData(JavaPlugin plugin, UUID uuid) {
        this.plugin = plugin;
        File dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File playersFolder = new File(dataFolder, "players");
        if (!playersFolder.exists()) {
            playersFolder.mkdirs();
        }

        this.file = new File(playersFolder, uuid.toString() + ".yml");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.data = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save() {
        try {
            this.data.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setToken(double PlayerToken) {
        this.data.set(this.plugin.getConfig().getString("Token Name"), PlayerToken);
        this.save();
    }

    public double getToken() {
        return this.data.getDouble(this.plugin.getConfig().getString("Token Name"), 0.0);
    }
}
