package moe.muska.ami.spcraft.cartcore.packs

import moe.muska.ami.spcraft.cartcore.CArtCore
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager

class CommandRegister {

    private val instance: Plugin = CArtCore.plugin
    private val pm: PluginManager = instance.server.pluginManager

    /**
     * 注册监听器
     */
    fun register() {
    }

}