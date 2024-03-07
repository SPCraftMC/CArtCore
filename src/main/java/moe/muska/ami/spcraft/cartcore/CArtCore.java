package moe.muska.ami.spcraft.cartcore;

import moe.muska.ami.spcraft.cartcore.packs.CommandRegister;
import moe.muska.ami.spcraft.cartcore.packs.ListenerRegister;
import moe.muska.ami.spcraft.cartcore.utils.Configuration;
import moe.muska.ami.spcraft.cartcore.utils.Database;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class CArtCore extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        // 加载配置
        Configuration.load();
        // 加载数据库连接
        Database.init();
        // 注册监听器
        new ListenerRegister().register();
        new CommandRegister().register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
