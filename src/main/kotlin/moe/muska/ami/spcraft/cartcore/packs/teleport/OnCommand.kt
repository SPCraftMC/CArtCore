package moe.muska.ami.spcraft.cartcore.packs.teleport

import com.google.common.io.ByteStreams
import moe.muska.ami.spcraft.cartcore.CArtCore
import moe.muska.ami.spcraft.cartcore.utils.Hook
import moe.muska.ami.spcraft.cartcore.utils.Logger
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin


class OnCommand : moe.muska.ami.spcraft.cartcore.utils.Command() {

    private val instance: Plugin = CArtCore.plugin
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
        if (args != null) {
            if (args.size == 1) {
                val player = sender as Player
                val out = ByteStreams.newDataOutput()
                out.writeUTF("ConnectPlayer")
                out.writeUTF(player.name)
                out.writeUTF(args[0])
                player.sendPluginMessage(instance, "BungeeCord", out.toByteArray())
            }
        }
    }

}