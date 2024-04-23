package org.nyanneko0113.craft_battle.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager {

    private static Objective game_obj = Bukkit.getScoreboardManager().getMainScoreboard().getObjective("Craft_Battle");
    private static Map<Player, Objective> obj_map = new HashMap<>();

    public static void addOnlinePlayer() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            obj_map.put(player, game_obj);
        }
    }

    public static void addPlayer(Player player) {
        obj_map.put(player, game_obj);
    }

    public static Player getOnlinePlayer() {
        return obj_map.keySet().stream().filter(OfflinePlayer::isOnline).findFirst().orElse(null);
    }

    private static void createScoreboard() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if (scoreboard.getObjective("Craft_Battle") == null) {
            Objective obj = scoreboard.registerNewObjective("Craft_Battle", "dummy");
            obj.setDisplayName(ChatColor.AQUA + "Craft_Battle");
        }
    }

    public static Objective getObjective(Player player) {
        return obj_map.get(player);
    }

    public static Scoreboard getScoreboard(Player player) {
        return obj_map.get(player).getScoreboard();
    }

    public static void setScoreboard(int type) {
        createScoreboard();
        resetScoreboard();
        if (type == 0) {
            //ゲーム開始前
            game_obj.getScore(" ").setScore(29);
            game_obj.getScore("ゲーム開始までお待ちください").setScore(28);
            game_obj.getScore("  ").setScore(27);
            game_obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        else if (type == 1) {
            //ゲーム中
            game_obj.getScore(" ").setScore(29);
            // anni_obj.getScore("残り時間： " + GameManager.game_time).setScore(28);
            game_obj.getScore("  ").setScore(27);
            // anni_obj.getScore(ChatColor.RED + "ポイント： " + ).setScore(26);
            game_obj.getScore("   ").setScore(24);
            game_obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        else if (type == 2) {
            //ゲーム終了後
            game_obj.getScore(" ").setScore(29);
            game_obj.getScore("ゲーム終了!!").setScore(28);
            game_obj.getScore("   ").setScore(27);
            game_obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }

    private static void resetScoreboard() {
        Scoreboard game_score = game_obj.getScoreboard();
        if (game_score != null) {
            game_score.getEntries().forEach(game_score::resetScores);
        }

        Collection<Objective> player_obj = obj_map.values();
        for (Objective obj : player_obj) {
            Scoreboard player_score = obj.getScoreboard();
            if (player_score != null) {
                player_score.getEntries().forEach(player_score::resetScores);
            }
        }
    }
}
