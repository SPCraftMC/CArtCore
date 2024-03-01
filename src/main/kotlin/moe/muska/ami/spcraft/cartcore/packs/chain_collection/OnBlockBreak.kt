package moe.muska.ami.spcraft.cartcore.packs.chain_collection

import moe.muska.ami.spcraft.cartcore.CArtCore
import moe.muska.ami.spcraft.cartcore.utils.Configuration
import org.bukkit.GameMode
import org.bukkit.block.Block
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.Plugin

class OnBlockBreak : Listener {

    private val instance: Plugin = CArtCore.plugin

    private val config: FileConfiguration? = Configuration.PacksConfiguration.CHAIN_COLLECTION
    private val target: MutableList<String> = ArrayList()

    @EventHandler
    fun onTreeBreak(e: BlockBreakEvent) {
        target.clear()
        if (config?.getBoolean("tree.enable") != true) return

        val logs = config.getStringList("tree.map.logs")
        val leaves = config.getStringList("tree.map.leaves")
        val tools = config.getStringList("tree.map.tools")

        target.addAll(logs)

        if (config.getBoolean("tree.leave")) {
            target.addAll(leaves)
        }

        val block: Block = e.block
        val player: Player = e.player

        // 触发条件
        if (!config.getBoolean("tree.creative-mode-support") && player.gameMode == GameMode.CREATIVE) return
        if (!logs.contains(block.type.name)) return
        val item = player.inventory.getItem(player.inventory.heldItemSlot) ?: return
        if (!tools.contains(item.type.name)) return
        if (!e.player.isSneaking) return
        Tool.breakNaturally(block, player, item, target)
    }

    @EventHandler
    fun onOreBreak(e: BlockBreakEvent) {
        target.clear()
        if (config?.getBoolean("ore.enable") != true) return

        val ores = config.getStringList("ore.map.ores")
        val tools = config.getStringList("ore.map.tools")

        target.addAll(ores)

        val block: Block = e.block
        val player: Player = e.player

        // 触发条件
        if (!config.getBoolean("ore.creative-mode-support") && player.gameMode == GameMode.CREATIVE) return
        if (!ores.contains(block.type.name)) return
        val item = player.inventory.getItem(player.inventory.heldItemSlot) ?: return
        if (!tools.contains(item.type.name)) return
        if (!e.player.isSneaking) return
        Tool.breakNaturally(block, player, item, target)
    }

}