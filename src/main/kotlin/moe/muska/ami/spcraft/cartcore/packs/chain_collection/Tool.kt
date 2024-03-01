package moe.muska.ami.spcraft.cartcore.packs.chain_collection

import org.bukkit.GameMode
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Tool {
    companion object {
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

                                if (damage <= hand.type.maxDurability) {
                                    if (player.gameMode != GameMode.CREATIVE) {
                                        hand.durability = damage
                                    }

                                    relative.breakNaturally()
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