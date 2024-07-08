package moe.muska.ami.spcraft.cartcore.packs.chain_collection

import moe.muska.ami.spcraft.cartcore.configuration.Configuration
import org.bukkit.GameMode
import org.bukkit.block.Block
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

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
                        if (target.contains(relative.type.name)) {
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
                                    breakNaturally(relative, player, hand, target)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}