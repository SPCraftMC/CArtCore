package moe.muska.ami.spcraft.cartcore.packs.warp

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class OnCommand: moe.muska.ami.spcraft.cartcore.utils.Command() {

    class OnCommandInject(cmd: String): Command(cmd) {
        override fun execute(sender: CommandSender, p1: String, args: Array<out String>?): Boolean {
            OnCommand().execute(sender, args)
            return true
        }

    }

    override fun execute(sender: CommandSender, args: Array<out String>?) {
        //sender.sendMessage("OK")
        when (args?.get(0)) {
            "set" -> setWarp(sender, args.drop(0).toTypedArray())
            "del" -> deleteWarp(sender, args.drop(0).toTypedArray())
            "delete" -> deleteWarp(sender, args.drop(0).toTypedArray())
            "tp" -> teleportWarp(sender, args.drop(0).toTypedArray())
            "teleport" -> teleportWarp(sender, args.drop(0).toTypedArray())
            else -> teleportWarp(sender, args)
        }
    }

    private fun setWarp(sender: CommandSender, args: Array<out String>?) {

    }
    private fun deleteWarp(sender: CommandSender, args: Array<out String>?) {

    }
    private fun teleportWarp(sender: CommandSender, args: Array<out String>?) {

    }

}