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
                const val CONFIG = "config.yml"
                const val WELCOME_MESSAGE = "configs/welcome_message.yml"
            }
        }
        companion object {
            @JvmStatic
            var CONFIG: FileConfiguration? = null
            @JvmStatic
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

            cs.CONFIG = YamlConfiguration.loadConfiguration(File("${instance.dataFolder}/${fs.CONFIG}"))
            cs.WELCOME_MESSAGE = YamlConfiguration.loadConfiguration(File("${instance.dataFolder}/${fs.WELCOME_MESSAGE}"))
        }
        private fun extract() {
            extractSingle(fs.CONFIG)
            extractSingle(fs.WELCOME_MESSAGE)
        }
        private fun extractSingle(path: String) {
            if (!File("${instance.dataFolder}/$path").exists())
                instance.saveResource(path, false)
        }
    }
}