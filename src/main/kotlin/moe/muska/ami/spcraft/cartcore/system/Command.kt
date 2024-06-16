package moe.muska.ami.spcraft.cartcore.system

import moe.muska.ami.spcraft.cartcore.CArtCore
import moe.muska.ami.spcraft.cartcore.utils.Configuration
import moe.muska.ami.spcraft.cartcore.utils.Environment
import moe.muska.ami.spcraft.cartcore.utils.Hook
import moe.muska.ami.spcraft.cartcore.utils.Logger
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

open class Command(cmd: String) : Command(cmd) {

    private val instance: Plugin = CArtCore.plugin
    override fun execute(sender: CommandSender, p1: String, args: Array<out String>?): Boolean {
        try {
            if (args?.isNotEmpty() == true) {
                when (args[0]) {
                    "version" -> run {
                        sender.sendMessage(Hook.translateMiniMessage("<blue>CArtCore</blue> version <yellow>${instance.description.version}</yellow>, made with <red>❤</red>"))
                        sender.sendMessage(Hook.translateMiniMessage("PlaceholderAPI: ${if (Environment.PlaceholderAPI) "<green>已安装</green>" else "<red>未安装</red>"}"))
                    }

                    "reload" -> run {
                        if (sender.hasPermission("cartcore.command.reload")) {
                            sender.sendMessage(Hook.translateMiniMessage("<yellow>正在重载配置文件...</yellow>"))
                            try {
                                // 重载配置
                                Configuration.reload()
                                sender.sendMessage(Hook.translateMiniMessage("<green>重载完毕</green>"))
                            } catch (e: Exception) {
                                instance.logger.severe(e.stackTrace.toString())
                                sender.sendMessage(Hook.translateMiniMessage("<red>重载失败，请查看控制台</red>"))
                            }
                        } else sender.sendMessage(Hook.translateMiniMessage("<red>你没有权限执行此命令</red>"))
                    }

                    "teleport" -> run {
                        if (sender.hasPermission("cartcore.command.teleport")) {
                            moe.muska.ami.spcraft.cartcore.packs.teleport.OnCommand().execute(sender, args.drop(1).toTypedArray())
                        } else sender.sendMessage(Hook.translateMiniMessage("<red>你没有权限执行此命令</red>"))
                    }

                    else -> run {
                        sender.sendMessage(Hook.translateMiniMessage("<red>命令不存在</red>"))
                    }
                }
            } else {
                sender.sendMessage(Hook.translateMiniMessage("<blue>CArtCore</blue> version <yellow>${instance.description.version}</yellow>, made with <red>❤</red>"))
            }
        } catch (e: Exception) {
            sender.sendMessage(Hook.translateMiniMessage("<red>执行命令发生错误</red>"))
            Logger.exception(e)
        }
        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>?): MutableList<String> {
        val sublist: MutableList<String> = ArrayList()
        when (args?.size) {
            1 -> run {
                sublist.clear()
                sublist.add("version")
                if (sender.hasPermission("cartcore.command.reload")) sublist.add("reload")
                if (sender.hasPermission("cartcore.command.warp")) sublist.add("warp")
                if (sender.hasPermission("cartcore.command.teleport")) sublist.add("teleport")
            }
        }
        return sublist
    }

}