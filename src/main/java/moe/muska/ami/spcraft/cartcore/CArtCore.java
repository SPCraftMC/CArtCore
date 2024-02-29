package moe.muska.ami.spcraft.cartcore;

import moe.muska.ami.spcraft.cartcore.packs.ListenerRegister;
import moe.muska.ami.spcraft.cartcore.utils.Configuration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class CArtCore extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        Configuration.load();
        new ListenerRegister().register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
