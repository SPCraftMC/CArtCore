package moe.muska.ami.spcraft.cartcore.packs

import moe.muska.ami.spcraft.cartcore.CArtCore
import moe.muska.ami.spcraft.cartcore.packs.warp.OnCommand.OnCommandInject
import moe.muska.ami.spcraft.cartcore.system.Command
import org.bukkit.plugin.Plugin

class CommandRegister {

    private val instance: Plugin = CArtCore.plugin

    fun register() {
        instance.server.commandMap.register("cartcore", Command("cartcore"))
        instance.server.commandMap.register("cac", Command("cac"))

        instance.server.commandMap.register("warp", OnCommandInject("warp"))
    }

}