package moe.muska.ami.spcraft.cartcore.packs.warp

import moe.muska.ami.spcraft.cartcore.utils.Configuration
import moe.muska.ami.spcraft.cartcore.utils.Database
import moe.muska.ami.spcraft.cartcore.utils.Logger
import org.bukkit.Location
import org.bukkit.configuration.file.FileConfiguration
import java.text.SimpleDateFormat
import java.util.*

class DB {
    private val config: FileConfiguration? = Configuration.PacksConfiguration.CONFIG
    private val mySQLTablePrefix = config?.getString("database.mysql.table_prefix")

    private val dbPool = Database().getConnection()
    private val table = if (config?.getString("database.type") == "mysql" && mySQLTablePrefix != "") "${mySQLTablePrefix}_warps" else "warps"

    fun add(playerUUID: UUID, location: Location, name: String) {
        val time = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(Date())
        val sql = "INSERT INTO $table (player_uuid, name, location, create_at, visit_times) VALUES (?, ?, ?, ?, ?)"
        val pstmt = dbPool.prepareStatement(sql)
        pstmt.setString(1, playerUUID.toString())
        pstmt.setString(2, name)
        pstmt.setString(3, location.serialize().toString())
        pstmt.setString(4, time)
        pstmt.setInt(5, 0)
        pstmt.executeUpdate()
    }

    fun delete(name: String) {
        val sql = "DELETE FROM $table WHERE name = ?"
        val pstmt = dbPool.prepareStatement(sql)
        pstmt.setString(1, name)
        pstmt.executeUpdate()
    }

    fun get(name: String): Location {
        val sql = "SELECT * FROM $table WHERE name = ?"
        val pstmt = dbPool.prepareStatement(sql)
        pstmt.setString(1, name)
        val rs = pstmt.executeQuery()
        val locationStr = rs.getString("location")
        val worldStartIndex = locationStr.indexOf("world=") + 6
        val worldEndIndex = locationStr.indexOf("}", startIndex = worldStartIndex)
        val worldStr = locationStr.substring(worldStartIndex, worldEndIndex)

        val keyValuePairs = locationStr.substring(locationStr.indexOf("{") + 1, locationStr.indexOf("}"))
            .split(",")
            .associate {
                val (key, value) = it.split("=")
                key.trim() to value
            }

        val map = mapOf("world" to worldStr) + keyValuePairs
        Logger.debug(map.toString())
        return Location.deserialize(map)
    }

}