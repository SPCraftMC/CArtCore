package moe.muska.ami.spcraft.cartcore.packs.welcome_message

import moe.muska.ami.spcraft.cartcore.utils.Configuration
import moe.muska.ami.spcraft.cartcore.utils.Hook
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class OnPlayerQuit: Listener {

    private val config: FileConfiguration? = Configuration.PacksConfiguration.WELCOME_MESSAGE

    @Override
    fun onPlayerQuit(e: PlayerQuitEvent) {
        if (config?.getBoolean("quit.enable") == true) {
            val player = e.player
            e.quitMessage(Hook.translateMiniMessage(Hook.translatePlaceholders(config.getString("quit.format") ?: "", player)))
        }
    }

}