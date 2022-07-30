package me.teakivy.nochatreports;

import me.teakivy.nochatreports.listeners.ChatListener;
import me.teakivy.nochatreports.util.Config;
import me.teakivy.nochatreports.util.ConfigUpdater;
import me.teakivy.nochatreports.util.Logger;
import me.teakivy.nochatreports.util.Metrics.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
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
