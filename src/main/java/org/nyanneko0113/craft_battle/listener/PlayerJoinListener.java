package org.nyanneko0113.craft_battle.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.nyanneko0113.craft_battle.manager.GameManager;
import org.nyanneko0113.craft_battle.manager.ScoreboardManager;

import java.util.HashSet;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ScoreboardManager.addPlayer(player);
        GameManager.getCraftMap().put(player, new HashSet<>());
    }
}
