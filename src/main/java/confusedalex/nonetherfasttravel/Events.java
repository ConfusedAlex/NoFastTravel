package confusedalex.nonetherfasttravel;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import java.util.HashMap;
import java.util.UUID;

public class Events implements Listener {

    HashMap<UUID, Location> locationHashMap = new HashMap<>();

    @EventHandler
    public void OnPortalEvent (PlayerPortalEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Location location = event.getFrom();

        if (location.getWorld().getEnvironment().equals(World.Environment.NORMAL)){
            locationHashMap.put(uuid, location);
        } else if (location.getWorld().getEnvironment().equals(World.Environment.NETHER) && locationHashMap.containsKey(uuid)){
            event.setCancelled(true);
            player.teleport(locationHashMap.get(uuid));
            locationHashMap.remove(uuid);
        }
    }
}
