package org.nyanneko0113.craft_battle;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.nyanneko0113.craft_battle.commands.GameStartCommand;
import org.nyanneko0113.craft_battle.listener.PlayerCraftListener;
import org.nyanneko0113.craft_battle.listener.PlayerJoinListener;
import org.nyanneko0113.craft_battle.manager.GameManager;
import org.nyanneko0113.craft_battle.manager.ScoreboardManager;

import java.util.HashSet;

public class CraftBattle extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager plm = getServer().getPluginManager();

        getCommand("craftbattle_start").setExecutor(new GameStartCommand());

        plm.registerEvents(new PlayerJoinListener(), this);
        plm.registerEvents(new PlayerCraftListener(), this);
        ScoreboardManager.setScoreboard(0);
        ScoreboardManager.addOnlinePlayer();
        for (Player player : Bukkit.getOnlinePlayers()) {
            GameManager.getCraftMap().put(player, new HashSet<>());
        }

    }

    @Override
    public void onDisable() {

    }

    public static CraftBattle getInstance() {
        return getPlugin(CraftBattle.class);
    }
}
