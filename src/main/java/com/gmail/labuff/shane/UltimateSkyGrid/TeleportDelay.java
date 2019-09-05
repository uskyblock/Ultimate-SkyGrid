package com.gmail.labuff.shane.UltimateSkyGrid;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportDelay extends BukkitRunnable {
    private final Player player;
    private final Location location;
    private final String worldName;
    
    TeleportDelay(final Player play, final Location loc, final String worldName) {
        this.player = play;
        this.location = loc;
        this.worldName = worldName;
    }
    
    public void run() {
        this.player.sendMessage(ChatColor.GREEN + "Teleporting you to your home in " + this.worldName);
        this.player.teleport(this.location);
    }
}
