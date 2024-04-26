package org.nyanneko0113.craft_battle.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.nyanneko0113.craft_battle.CraftBattle;
import org.nyanneko0113.craft_battle.util.BukkitUtil;
import org.nyanneko0113.craft_battle.util.TextUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameManager {

    private static int game_time = 60*15;
    private static int count_time = 10;

    private static final Map<Player, Set<ItemStack>> craft_map = new HashMap<>();

    public static int startGame() {
        GameStatus status = statusGame();
        if (status.equals(GameStatus.WAITING)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (count_time != 0) {
                        Bukkit.broadcastMessage(TextUtil.TEXT_INFO + "ゲーム開始まであと" + count_time + "秒");
                        BukkitUtil.playSound(Sound.UI_BUTTON_CLICK);
                        count_time--;
                    }
                    else {
                        ScoreboardManager.setScoreboard(1);
                        Bukkit.broadcastMessage(TextUtil.TEXT_INFO + "ゲーム開始!!");
                        this.cancel();
                    }
                }
            }.runTaskTimer(CraftBattle.getInstance(), 0L, 20L);

            new BukkitRunnable() {
                String time = null;
                String point = null;
                @Override
                public void run() {
                    if (count_time == 0) {

                        //スコアボード
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            Objective game_obj = ScoreboardManager.getObjective(player);
                            Scoreboard game_board = ScoreboardManager.getScoreboard(player);

                            //残り時間
                            if (time != null) {
                                game_board.resetScores(time);
                            }
                            time = ("残り時間： " + GameManager.stringTime());
                            game_obj.getScore(time).setScore(28);

                            //ポイント
                            if (point != null) {
                                game_board.resetScores(point);
                            }
                            point = (ChatColor.RED + "ポイント： " + ChatColor.YELLOW + craft_map.get(player).size());
                            game_obj.getScore(point).setScore(26);

                            player.setScoreboard(ScoreboardManager.getScoreboard(player));
                        }

                        //時間通知
                        if (game_time == 60 || game_time == 120) {
                            Bukkit.broadcastMessage(TextUtil.TEXT_INFO + "残りあと" + game_time + "秒");
                        }

                        //ゲーム終了
                        if (game_time == 0) {
                            ScoreboardManager.setScoreboard(2);
                            Bukkit.broadcastMessage(TextUtil.TEXT_INFO + "ゲーム終了!!");
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                Set<ItemStack> craft = GameManager.getCraftMap().get(player);
                                player.sendMessage(TextUtil.TEXT_INFO + "あなたのクラフト数は" + craft.size() + "でした。");
                            }
                            this.cancel();
                        }

                        game_time--;
                    }
                }
            }.runTaskTimer(CraftBattle.getInstance(), 0L, 20L);

            //成功
            return 0;
        }
        else if (status.equals(GameStatus.RUNNING)) {
            //実行中のため
            return 1;
        }
        else if (status.equals(GameStatus.UNKNOWN)) {
            //不明なエラー
            return 2;
        }
        return 2;
    }

    public static void resetGame() {
        Bukkit.getScheduler().cancelTasks(CraftBattle.getInstance());
        count_time=0;
        game_time=0;
        ScoreboardManager.setScoreboard(2);
        Bukkit.broadcastMessage(TextUtil.TEXT_INFO + "ゲームは強制終了されました。");
        for (Player player : Bukkit.getOnlinePlayers()) {
            Set<ItemStack> craft = GameManager.getCraftMap().get(player);
            player.sendMessage(TextUtil.TEXT_INFO + "あなたのクラフト数は" + craft.size() + "でした。");
        }
    }

    public static Map<Player, Set<ItemStack>> getCraftMap() {
        return craft_map;
    }

    public static GameStatus statusGame() {
        if (count_time == 10) {
            return GameStatus.WAITING;
        }
        else if (count_time == 0 && game_time > 0) {
            return GameStatus.RUNNING;
        }
        else if (count_time == 0 && game_time == 0) {
            return GameStatus.ENDING;
        }
        return GameStatus.UNKNOWN;
    }

    private static String stringTime() {
        int hour = game_time / 3600;
        int min = game_time / 60;
        int sec = game_time % 60;

        return min + "分" + sec + "秒";
    }

    public enum GameStatus {
        RUNNING,
        WAITING,
        ENDING,
        UNKNOWN
    }
}
