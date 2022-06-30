package me.teakivy.nochatreports.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {
    public static void info(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        message = "[NCR] " + message;
        if (!Config.isLogColors()) {
            message = ChatColor.stripColor(message);
        }

        Bukkit.getConsoleSender().sendMessage(message);
    }
}
