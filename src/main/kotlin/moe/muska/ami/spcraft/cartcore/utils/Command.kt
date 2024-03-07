package moe.muska.ami.spcraft.cartcore.utils

import org.bukkit.command.CommandSender

abstract class Command {

    /**
     * 命令执行
     */
    abstract fun execute(sender: CommandSender, args: Array<out String>?)

}