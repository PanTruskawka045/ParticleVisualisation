package me.pan_truskawka045.particlevisualiser.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {

    @EventHandler
    private void onAchievement(PlayerAdvancementDoneEvent event) {
        event.message(null);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.CREATIVE);
        player.teleport(new Location(Bukkit.getWorlds().iterator().next(), 0, 64, 0));
    }

}
