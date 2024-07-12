package moe.muska.ami.spcraft.cartcore.packs.chain_collection

import moe.muska.ami.spcraft.cartcore.configuration.Configuration
import moe.muska.ami.spcraft.cartcore.utils.Logger
import org.bukkit.GameMode
import org.bukkit.block.Block
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class Tool {
    companion object {

        private val config: FileConfiguration? = Configuration.PacksConfiguration.CHAIN_COLLECTION

        fun breakNaturally(block: Block, player: Player, hand: ItemStack, target: MutableList<String>) {
            //x -1 0 1
            for (x in -1..1) {
                //y -1 0 1
                for (y in -1..1) {
                    //z -1 0 1
                    for (z in -1..1) {
                        val relative: Block = block.getRelative(x, y, z)
                        val blockName = relative.type.name
                        if (target.contains(blockName)) {
                            val meta = hand.itemMeta
                            if (meta != null) {
                                val damage = (hand.durability + 1).toShort()

                                if (config?.getBoolean("damage-protection.enable") == true) {
                                    val damageProtectValue = damage + config.getInt("damage-protection.value")
                                    if (damageProtectValue > hand.type.maxDurability) return
                                }
                                if (damage <= hand.type.maxDurability) {
                                    if (player.gameMode != GameMode.CREATIVE) {
                                        hand.durability = damage
                                    }

                                    if (config?.getBoolean("ignore-enchant") == true) relative.breakNaturally()
                                    else relative.breakNaturally(hand)
                                    player.giveExp(expCount(blockName))
                                    breakNaturally(relative, player, hand, target)
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * 计算随机掉落经验
         * @param target 目标方块名
         */
        private fun expCount(target: String): Int {
            Logger.debug("Target: $target")
            val experienceSection: ConfigurationSection? = config?.getConfigurationSection("experience")

            var experienceGet = 0

            Logger.debug(config?.getConfigurationSection("experience").toString())
            if (experienceSection != null) {
                for (key in experienceSection.getKeys(false)) {
                    val itemSection = experienceSection.getConfigurationSection(key)
                    val items = itemSection?.getStringList("items")
                    val min = itemSection?.getInt("min") ?: 0
                    val max = itemSection?.getInt("max") ?: 0
                    if (items != null) {
                        Logger.debug("items not null")
                        Logger.debug(items)
                        Logger.debug("Experience range: $min ~ $max")
                        for (item in items) {
                            if (item == target) {
                                // 以当前时间戳为随机种子
                                val random = Random(seed = System.currentTimeMillis())
                                // 在最大和最小经验之间取值并赋值给 experienceGet
                                // 由于 target 不可能是多个方块，所以不需要 +=
                                experienceGet = random.nextInt(max - min + 1) + min
                                Logger.debug("Target $target get $experienceGet experience")
                            }
                        }
                    } else Logger.warn("Invalid item input in ChainCollection experience config, expected StringList, but got null.")
                }
            }
            return experienceGet
        }
    }
}