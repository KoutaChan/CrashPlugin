package koutachan.crashplugin.event;

import koutachan.crashplugin.CrashPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Event implements Listener {
    @EventHandler
    public void JoinEvent(PlayerJoinEvent e){
        CrashPlugin.hashMap.put(e.getPlayer().getUniqueId(), false);
    }
}
