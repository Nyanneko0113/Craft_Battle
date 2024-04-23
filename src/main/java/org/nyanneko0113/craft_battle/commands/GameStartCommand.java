package org.nyanneko0113.craft_battle.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.nyanneko0113.craft_battle.manager.GameManager;
import org.nyanneko0113.craft_battle.util.TextUtil;

public class GameStartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("craftbattle_start")) {
            send.sendMessage(TextUtil.TEXT_INFO + "ゲームを開始しています..");
            GameManager.startGame();
        }
        return false;
    }

}
