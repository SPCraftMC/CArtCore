package moe.muska.ami.spcraft.cartcore.packs.warp

import moe.muska.ami.spcraft.cartcore.utils.Hook
import moe.muska.ami.spcraft.cartcore.utils.Logger
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class OnCommand: moe.muska.ami.spcraft.cartcore.utils.Command() {

    val db: DB = DB()

    class OnCommandInject(cmd: String): Command(cmd) {
        override fun execute(sender: CommandSender, p1: String, args: Array<out String>?): Boolean {
            try {
                OnCommand().execute(sender, args)
            } catch (e: Exception) {
                sender.sendMessage(Hook.translateMiniMessage("<red>执行命令发生错误</red>"))
                Logger.exception(e)
            }
            return true
        }
    }

    override fun execute(sender: CommandSender, args: Array<out String>?) {
        //sender.sendMessage("OK")
        if (args.isNullOrEmpty()) {
            sender.sendMessage(Hook.translateMiniMessage("<red>命令语法不正确</red>"))
            return
        }
        when (args[0]) {
            "set" -> setWarp(sender, args.drop(1).toTypedArray())
            "del" -> deleteWarp(sender, args.drop(1).toTypedArray())
            "delete" -> deleteWarp(sender, args.drop(1).toTypedArray())
            "tp" -> teleportWarp(sender, args.drop(1).toTypedArray())
            "teleport" -> teleportWarp(sender, args.drop(1).toTypedArray())
            else -> teleportWarp(sender, args)
        }
    }

    private fun setWarp(sender: CommandSender, args: Array<out String>?) {
        //args?.toList()?.let { Logger.debug(it) }
        if (args?.size == 1) {
            val player = sender as Player
            db.add(player.uniqueId, player.location, args[0])
            sender.sendMessage(Hook.translateMiniMessage("<green>传送点设置成功！</green>"))
        } else {
            sender.sendMessage(Hook.translateMiniMessage("<red>命令语法不正确</red>"))
        }
    }
    private fun deleteWarp(sender: CommandSender, args: Array<out String>?) {
        if (args?.size == 1) {
            db.delete(args[0])
            sender.sendMessage(Hook.translateMiniMessage("<green>传送点已删除！</green>"))
        } else {
            sender.sendMessage(Hook.translateMiniMessage("<red>命令语法不正确</red>"))
        }
    }
    private fun teleportWarp(sender: CommandSender, args: Array<out String>?) {
        if (args?.size == 1) {
            val res = db.get(args[0])
            val player = sender as Player
            player.teleport(res)
            sender.sendMessage(Hook.translateMiniMessage("<yellow>已传送至 ${res}</yellow>"))
        } else {
            sender.sendMessage(Hook.translateMiniMessage("<red>命令语法不正确</red>"))
        }
    }

}