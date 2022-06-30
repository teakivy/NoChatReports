package me.teakivy.nochatreports.listeners;

import me.teakivy.nochatreports.util.Config;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(org.bukkit.event.player.AsyncPlayerChatEvent e) {
        e.setCancelled(true);

        Player player = e.getPlayer();
        String message = e.getMessage();

        String[] format = Config.getMessageFormat().split("%player%");
        String first = format[0];
        String second = format[1];

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.spigot().sendMessage(new TextComponent(first), getClickableName(player), new TextComponent(second.replaceAll("%message%", message)));
        }
    }

    public TextComponent getClickableName(Player player) {
        TextComponent nameComponent = new TextComponent(player.getPlayerListName());
        nameComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + player.getName() + " "));
        nameComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(player.getName() + "\nType: Player\n" + player.getUniqueId())));
        return nameComponent;
    }

}
