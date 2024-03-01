package moe.muska.ami.spcraft.cartcore.packs

import moe.muska.ami.spcraft.cartcore.CArtCore
import moe.muska.ami.spcraft.cartcore.packs.chain_collection.OnBlockBreak
import moe.muska.ami.spcraft.cartcore.packs.welcome_message.OnPlayerJoin
import moe.muska.ami.spcraft.cartcore.packs.welcome_message.OnPlayerQuit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager

class ListenerRegister {

    private val instance: Plugin = CArtCore.plugin
    private val pm: PluginManager = instance.server.pluginManager

    /**
     * 注册监听器
     */
    fun register() {
        // welcome_message
        pm.registerEvents(OnPlayerJoin(), instance)
        pm.registerEvents(OnPlayerQuit(), instance)
        // chain_collection
        pm.registerEvents(OnBlockBreak(), instance)
    }

}