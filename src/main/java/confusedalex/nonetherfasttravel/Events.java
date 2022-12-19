package confusedalex.nonetherfasttravel;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class Events implements Listener {
    NoNetherFastTravel noNetherFastTravel;
    HashMap<UUID, Location> locationHashMap = new HashMap<>();

    public Events(NoNetherFastTravel noNetherFastTravel) {
        this.noNetherFastTravel = noNetherFastTravel;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        FileConfiguration config = noNetherFastTravel.getConfig();

        if (config.contains(uuid.toString())) {
            locationHashMap.put(uuid, (Location) config.get(uuid.toString()));
            config.set(uuid.toString(), null);
        }
    }

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

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        FileConfiguration config = noNetherFastTravel.getConfig();

        if (locationHashMap.containsKey(uuid)) {
            config.set(uuid.toString(), locationHashMap.get(uuid));
            locationHashMap.remove(uuid);
        }
    }
}
