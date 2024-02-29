package moe.muska.ami.spcraft.cartcore.utils

import moe.muska.ami.spcraft.cartcore.CArtCore
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

interface Configuration {
    class PacksConfiguration {
        class Files {
            companion object {
                const val WELCOME_MESSAGE = "configs/welcome_message.yml"
            }
        }
        companion object {
            var WELCOME_MESSAGE: FileConfiguration? = null
        }
    }

    companion object {
        private val instance: Plugin = CArtCore.plugin
        private val cs = PacksConfiguration
        private val fs = PacksConfiguration.Files
        @JvmStatic
        fun load() {
            extract()

            cs.WELCOME_MESSAGE = YamlConfiguration.loadConfiguration(File(fs.WELCOME_MESSAGE))
        }
        private fun extract() {
            extractSingle(fs.WELCOME_MESSAGE)
        }
        private fun extractSingle(path: String) {
            if (!File("${CArtCore.plugin.dataFolder}/$path").exists())
                instance.saveResource(path, false)
        }
    }
}