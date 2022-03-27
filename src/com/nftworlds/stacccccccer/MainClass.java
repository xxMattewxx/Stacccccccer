package com.nftworlds.stacccccccer;

import com.nftworlds.stacccccccer.loot.LootCalculator;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.plugin.java.JavaPlugin;
import com.nftworlds.stacccccccer.utils.StackingConfig;
import com.nftworlds.stacccccccer.workers.ExistingMobStacker;

public class MainClass extends JavaPlugin {
    private BukkitTask stackerRunnableTask;

    private void setupConfig() {
        FileConfiguration config = getConfig();
        config.addDefault("maxStackCount", 20000);
        config.addDefault("schedulerTiming", 40);
        config.addDefault("stackDistance", 3);

        config.addDefault("spawnerXDistance", 8);
        config.addDefault("spawnerYDistance", 4);
        config.addDefault("spawnerZDistance", 8);
    }

    @Override
    public void onEnable() {
        setupConfig();

        LootCalculator lootCalculator = new LootCalculator();
        StackingConfig stackingConfig = new StackingConfig(getConfig());
        StackingManager stackingManager = new StackingManager(stackingConfig);

        Bukkit.getLogger().info(String.format("[%s] Registering events...", this.getName()));
        MainListener eventListener = new MainListener(lootCalculator, stackingConfig, stackingManager);
        getServer().getPluginManager().registerEvents(eventListener, this);

        Bukkit.getLogger().info(String.format("[%s] Registering scheduled tasks", this.getName()));
        ExistingMobStacker stackerRunnable = new ExistingMobStacker(stackingConfig, stackingManager);
        stackerRunnableTask = stackerRunnable.runTaskTimer(this, 0, stackingConfig.getSchedulerTiming());

        Bukkit.getLogger().info(String.format("[%s] Plugin loaded successfully", this.getName()));

        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(String.format("[%s] Cancelling scheduled tasks", this.getName()));
        stackerRunnableTask.cancel();

        Bukkit.getLogger().info(String.format("[%s] Plugin unloaded", this.getName()));
    }
}
