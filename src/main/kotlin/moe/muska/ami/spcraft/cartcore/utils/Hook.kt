package moe.muska.ami.spcraft.cartcore.utils

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.milkbowl.vault.economy.Economy
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Hook {

    companion object {

        var econ: Economy? = null

        /**
         * 转换占位符
         * @param text 原始文本
         * @param player 玩家
         * @return 处理后的文本
         */
        fun translatePlaceholders(text: String, player: Player): String {
            return if (Environment.PlaceholderAPI) PlaceholderAPI.setPlaceholders(player, text) else text
        }

        /**
         * 转换占位符
         * @param text 原始文本
         * @param player 玩家
         * @return 处理后的文本
         */
        fun translatePlaceholders(text: String, player: CommandSender): String {
            return translatePlaceholders(text, player as Player)
        }

        /**
         * 转换MiniMessage
         * @param text 原始文本
         * @return 处理后的文本
         */
        fun translateMiniMessage(text: String): Component {
            return MiniMessage.miniMessage().deserialize(text)
        }
    }

}