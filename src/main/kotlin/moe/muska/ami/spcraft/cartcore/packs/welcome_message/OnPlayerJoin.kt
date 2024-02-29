package moe.muska.ami.spcraft.cartcore.packs.welcome_message

import moe.muska.ami.spcraft.cartcore.utils.Configuration
import moe.muska.ami.spcraft.cartcore.utils.Hook
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class OnPlayerJoin: Listener {

    private val config: FileConfiguration? = Configuration.PacksConfiguration.WELCOME_MESSAGE

    @Override
    fun onPlayerJoin(e: PlayerJoinEvent) {
        if (config?.getBoolean("join.enable") == true) {
            val player = e.player
            e.joinMessage(Hook.translateMiniMessage(Hook.translatePlaceholders(config.getString("join.format") ?: "", player)))
        }
    }

}