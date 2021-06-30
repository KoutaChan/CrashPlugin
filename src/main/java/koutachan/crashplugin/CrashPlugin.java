package koutachan.crashplugin;

import koutachan.crashplugin.command.CrashCommand;
import koutachan.crashplugin.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class CrashPlugin extends JavaPlugin {

    public static HashMap<UUID,Boolean> hashMap = new HashMap<>();

    @Override
    public void onEnable() {
        for(Player player : Bukkit.getOnlinePlayers()){
            hashMap.put(player.getUniqueId(),false);
        }
        getServer().getPluginManager().registerEvents(new Event(),this);
        getCommand("crash").setExecutor(new CrashCommand(this));
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
