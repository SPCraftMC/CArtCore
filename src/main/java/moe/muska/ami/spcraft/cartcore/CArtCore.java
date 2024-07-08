package moe.muska.ami.spcraft.cartcore;

import moe.muska.ami.spcraft.cartcore.configuration.Configuration;
import moe.muska.ami.spcraft.cartcore.packs.CommandRegister;
import moe.muska.ami.spcraft.cartcore.packs.ListenerRegister;
import moe.muska.ami.spcraft.cartcore.utils.Environment;
import moe.muska.ami.spcraft.cartcore.utils.Hook;
import moe.muska.ami.spcraft.cartcore.utils.Logger;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class CArtCore extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        // 加载配置
        Configuration.load();
//        // 加载数据库连接
//        Database.init();
        // 注册监听器
        new ListenerRegister().register();
        new CommandRegister().register();

        if (!Environment.Companion.getVault()) {
            Logger.Companion.warn("没有安装 Vault ，金钱相关功能将不可用！");
        } else {
            setupEconomy();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        Hook.Companion.setEcon(rsp.getProvider());
    }
}
