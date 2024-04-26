package org.nyanneko0113.craft_battle.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.nyanneko0113.craft_battle.manager.GameManager;
import org.nyanneko0113.craft_battle.util.TextUtil;

import java.util.Map;
import java.util.Set;

public class PlayerCraftListener implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getRecipe().getResult();
        Set<ItemStack> craft = GameManager.getCraftMap().get(player);

        if (GameManager.statusGame().equals(GameManager.GameStatus.RUNNING)) {
            if (!craft.contains(item)) {
                craft.add(item);
                player.sendMessage(TextUtil.TEXT_INFO + "新しいアイテムをクラフトしました。" +
                        "(現在: " + ChatColor.YELLOW + craft.size() + ChatColor.RESET + "ポイント)");
            }
        }
    }

}
