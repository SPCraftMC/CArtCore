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
        val sql = "INSERT INTO $table (player_uuid, name, location_x, location_y, location_z, location_pitch, location_world, create_at, visit_times) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        val pstmt = dbPool.prepareStatement(sql)
        pstmt.setString(1, playerUUID.toString())
        pstmt.setString(2, name)
        pstmt.setDouble(3, location.x)
        pstmt.setDouble(4, location.y)
        pstmt.setDouble(5, location.z)
        pstmt.setFloat(6, location.pitch)
        pstmt.setString(7, location.world.name)
        pstmt.setString(8, time)
        pstmt.setInt(9, 0)
        pstmt.executeUpdate()
    }

    fun delete(name: String) {
        val sql = "DELETE FROM $table WHERE name = ?"
        val pstmt = dbPool.prepareStatement(sql)
        pstmt.setString(1, name)
        pstmt.executeUpdate()
    }

    fun get(name: String): Location? {
        val sql = "SELECT * FROM $table WHERE name = ?"
        val pstmt = dbPool.prepareStatement(sql)
        pstmt.setString(1, name)
        val rs = pstmt.executeQuery()
        if (rs.next()) {
            val x = rs.getDouble("location_x")
            val y = rs.getDouble("location_y")
            val z = rs.getDouble("location_z")
            val pitch = rs.getFloat("location_pitch")
            val world = rs.getString("location_world")
            val map: Map<String, Any?> = mapOf(
                "x" to x,
                "y" to y,
                "z" to z,
                "pitch" to pitch,
                "world" to world
            )
            Logger.debug(map.toString())
            rs.close()
            return Location.deserialize(map)
        }
        return null
    }

}