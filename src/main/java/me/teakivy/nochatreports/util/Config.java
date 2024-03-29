package me.teakivy.nochatreports.util;

import me.teakivy.nochatreports.NoChatReports;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private static final FileConfiguration config = NoChatReports.getInstance().getConfig();

    public static boolean isLogColors() {
        return config.getBoolean("log-colors", true);
    }

    public static boolean isPaperWorkaround() {
        return config.getBoolean("paper-workaround", false);
    }
}
