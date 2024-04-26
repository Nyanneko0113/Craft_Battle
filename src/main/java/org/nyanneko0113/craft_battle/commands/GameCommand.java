package org.nyanneko0113.craft_battle.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.nyanneko0113.craft_battle.manager.GameManager;
import org.nyanneko0113.craft_battle.util.TextUtil;

public class GameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("craftbattle_start")) {
            send.sendMessage(TextUtil.TEXT_INFO + "ゲームを開始しています..");
            int game = GameManager.startGame();
            if (game == 1) {
                send.sendMessage(TextUtil.TEXT_ERROR + "ゲームが実行中のため開始できませんでした。");
            }
            else if (game == 2) {
                send.sendMessage(TextUtil.TEXT_ERROR + "不明なエラーが発生しました。");
            }
        }
        else if (cmd.getName().equalsIgnoreCase("craftbattle_reset")) {
            send.sendMessage(TextUtil.TEXT_INFO + "ゲームを強制的にしています...");
            GameManager.resetGame();
        }
        return false;
    }

}
