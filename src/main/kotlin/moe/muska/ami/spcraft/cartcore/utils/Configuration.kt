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
                const val CHAIN_COLLECTION = "configs/chain_collection.yml"
                const val TELEPORT = "configs/teleport.yml"
            }
        }

        companion object {
            @JvmStatic
            var CONFIG: FileConfiguration? = null

            @JvmStatic
            var WELCOME_MESSAGE: FileConfiguration? = null

            @JvmStatic
            var CHAIN_COLLECTION: FileConfiguration? = null

            @JvmStatic
            var TELEPORT: FileConfiguration? = null
        }
    }

    companion object {
        private val instance: Plugin = CArtCore.plugin
        private val cs = PacksConfiguration
        private val fs = PacksConfiguration.Files

        /**
         * 加载配置文件
         */
        @JvmStatic
        fun load() {
            try {
                extract()

                cs.CONFIG = YamlConfiguration.loadConfiguration(File("${instance.dataFolder}/${fs.CONFIG}"))
                cs.WELCOME_MESSAGE =
                    YamlConfiguration.loadConfiguration(File("${instance.dataFolder}/${fs.WELCOME_MESSAGE}"))
                cs.CHAIN_COLLECTION =
                    YamlConfiguration.loadConfiguration(File("${instance.dataFolder}/${fs.CHAIN_COLLECTION}"))
                cs.TELEPORT = YamlConfiguration.loadConfiguration(File("${instance.dataFolder}/${fs.TELEPORT}"))
            } catch (e: Exception) {
                Logger.fatal("Load configuration failed!", e)
            }
        }

        /**
         * 重载配置文件
         */
        @JvmStatic
        fun reload() {
            try {
                cs.CONFIG?.load(File("${instance.dataFolder}/${fs.CONFIG}"))
                cs.WELCOME_MESSAGE?.load(File("${instance.dataFolder}/${fs.WELCOME_MESSAGE}"))
                cs.CHAIN_COLLECTION?.load(File("${instance.dataFolder}/${fs.CHAIN_COLLECTION}"))
                cs.TELEPORT?.load(File("${instance.dataFolder}/${fs.TELEPORT}"))
            } catch (e: Exception) {
                Logger.exception(e)
            }
        }

        private fun extract() {
            extractSingle(fs.CONFIG)
            extractSingle(fs.WELCOME_MESSAGE)
            extractSingle(fs.CHAIN_COLLECTION)
            extractSingle(fs.TELEPORT)
        }

        private fun extractSingle(path: String) {
            if (!File("${instance.dataFolder}/$path").exists())
                instance.saveResource(path, false)
        }
    }
}