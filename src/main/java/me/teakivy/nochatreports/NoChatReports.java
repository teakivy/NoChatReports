package me.teakivy.nochatreports;

import me.teakivy.nochatreports.listeners.ChatListener;
import me.teakivy.nochatreports.util.Config;
import me.teakivy.nochatreports.util.ConfigUpdater;
import me.teakivy.nochatreports.util.Logger;
import me.teakivy.nochatreports.util.Metrics.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;

public final class NoChatReports extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);

        saveDefaultConfig();

        if (getConfig().getBoolean("config.dev-mode")) {
            Logger.info(ChatColor.GOLD + "NoChatReports is in developer mode!");
        }

        updateConfig();

        registerMetrics();

        Logger.info(ChatColor.GREEN + "NoChatReports v" + getDescription().getVersion() + " loaded!");


        if (Config.isPaperWorkaround()) return;

        String[] paperServerTypes = {"Paper", "Purpur", "PaperSpigot", "Airplane"};

        for (String serverType : paperServerTypes) {
            if (Bukkit.getServer().getName().toLowerCase().contains(serverType.toLowerCase())) {
                Logger.warning("This server is running on a known Paper forked server!");
                Logger.warning("This plugin may not work properly.");
                Logger.warning("Enable the 'paper-workaround' option in the config to fix this.");
                break;
            }
        }
    }

    private void updateConfig() {
        if (!this.getConfig().getBoolean("config.dev-mode")) {
            if (this.getConfig().getInt("config.version") < Objects.requireNonNull(this.getConfig().getDefaults()).getInt("config.version")) {
                attemptConfigUpdate();
            }
        } else {
            attemptConfigUpdate();
        }
    }

    private void attemptConfigUpdate() {
        try {
            ConfigUpdater.update(this, "config.yml", new File(this.getDataFolder(), "config.yml"), Collections.emptyList(), true);
            this.reloadConfig();
            this.getLogger().info("Config updated to version " + this.getConfig().getInt("config.version"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.info("Updated Config");
    }

    private void registerMetrics() {
        int pluginId = 15631;
        Metrics metrics = new Metrics(this, pluginId);

        // Add custom charts
        metrics.addCustomChart(new Metrics.SimplePie("log_colors", () -> Config.isLogColors() ? "True" : "False"));
        metrics.addCustomChart(new Metrics.SimplePie("first_used", () -> getConfig().getString("config.plugin-version")));
    }

    public static NoChatReports getInstance() {
        return NoChatReports.getPlugin(NoChatReports.class);
    }
}
