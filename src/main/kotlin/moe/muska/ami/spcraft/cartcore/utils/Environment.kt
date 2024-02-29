package moe.muska.ami.spcraft.cartcore.utils

import moe.muska.ami.spcraft.cartcore.CArtCore
import org.bukkit.plugin.Plugin

class Environment {
    companion object {
        private val instance: Plugin = CArtCore.plugin

        var PlaceholderAPI: Boolean = instance.server.pluginManager.getPlugin("PlaceholderAPI") != null

        /**
         * 重载环境
         */
        fun reload() {
            PlaceholderAPI = instance.server.pluginManager.getPlugin("PlaceholderAPI") != null
        }
    }
}