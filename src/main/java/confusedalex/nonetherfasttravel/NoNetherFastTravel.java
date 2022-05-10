package confusedalex.nonetherfasttravel;

import org.bukkit.plugin.java.JavaPlugin;

public final class NoNetherFastTravel extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    @Override
    public void onDisable() {}
}
