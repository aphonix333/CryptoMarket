package com.aphonix.cryptoMarket.Events;

import com.aphonix.cryptoMarket.Data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerJoinListener implements Listener {
    private final JavaPlugin plugin;

    public PlayerJoinListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        new PlayerData(this.plugin, e.getPlayer().getUniqueId());
    }
}
