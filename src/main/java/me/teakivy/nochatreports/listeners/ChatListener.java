package me.teakivy.nochatreports.listeners;

import me.teakivy.nochatreports.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (Config.isPaperWorkaround()) {
            e.setCancelled(true);

            for (Player toPlayer : Bukkit.getOnlinePlayers()) {
                toPlayer.sendMessage(String.format(e.getFormat(), e.getPlayer().getDisplayName(), e.getMessage()));
            }

            return;
        }
        e.setMessage(e.getMessage() + " ");
    }
}
