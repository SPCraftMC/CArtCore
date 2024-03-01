package moe.muska.ami.spcraft.cartcore.system

import moe.muska.ami.spcraft.cartcore.CArtCore
import moe.muska.ami.spcraft.cartcore.utils.Environment
import moe.muska.ami.spcraft.cartcore.utils.Hook
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class Command(cmd: String) : Command(cmd) {

    private val instance: Plugin = CArtCore.plugin
    override fun execute(sender: CommandSender, p1: String, args: Array<out String>?): Boolean {
        if (args?.isNotEmpty() == true) {
            when (args[0]) {
                "version" -> run {
                    sender.sendMessage(Hook.translateMiniMessage("<blue>CArtCore</blue> version <yellow>${instance.description.version}</yellow>, made with <red>❤</red>"))
                    sender.sendMessage(Hook.translateMiniMessage("PlaceholderAPI: ${if (Environment.PlaceholderAPI) "<green>已安装</green>" else "<red>未安装</red>"}"))
                }

                else -> run {
                    sender.sendMessage(Hook.translateMiniMessage("<red>命令不存在</red>"))
                }
            }
        } else {
            sender.sendMessage(Hook.translateMiniMessage("<blue>CArtCore</blue> version <yellow>${instance.description.version}</yellow>, made with <red>❤</red>"))
        }
        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>?): MutableList<String> {
        val sublist: MutableList<String> = ArrayList()
        when (args?.size) {
            1 -> run {
                sublist.clear()
                sublist.add("version")
            }
        }
        return sublist
    }

}