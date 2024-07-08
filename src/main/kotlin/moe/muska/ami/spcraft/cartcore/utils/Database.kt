package moe.muska.ami.spcraft.cartcore.utils

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import moe.muska.ami.spcraft.cartcore.CArtCore
import moe.muska.ami.spcraft.cartcore.configuration.Configuration
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.Plugin
import java.sql.Connection

@Deprecated("")
class Database {

//    private val instance: Plugin = CArtCore.plugin

    fun initTable() {
        val mySQLTablePrefix = config?.getString("database.mysql.table_prefix")

        val tables = ArrayList<String>()

        if (config?.getString("database.type") == "mysql" && mySQLTablePrefix != "") {
            tables.add("${mySQLTablePrefix}_warps")
        } else {
            tables.add("warps")
        }
        val stmt = hds.connection.createStatement()
        for (table in tables) {
            val sql = "CREATE TABLE IF NOT EXISTS $table (" +
                    "id             INTEGER   PRIMARY KEY AUTOINCREMENT," +
                    "player_uuid    CHAR(255)         NOT NULL," +
                    "name           CHAR(255)         NOT NULL UNIQUE," +
                    "location_x     DOUBLE            NOT NULL," +
                    "location_y     DOUBLE            NOT NULL," +
                    "location_z     DOUBLE            NOT NULL," +
                    "location_pitch FLOAT             NOT NULL," +
                    "location_world CHAR(255)         NOT NULL," +
                    "create_at      CHAR(255)         NOT NULL," +
                    "visit_times    INT               NOT NULL" +
                    ");"
            stmt.execute(sql)
        }
    }

    fun getConnection(): Connection {
        return hds.connection
    }

    companion object {
        private val instance: Plugin = CArtCore.plugin
        private val config: FileConfiguration? = Configuration.PacksConfiguration.CONFIG

        @JvmStatic
        private lateinit var hds: HikariDataSource

        @JvmStatic
        fun init() {
            val hc = HikariConfig()
            when (config?.getString("database.type")) {
                "mysql" -> run {
                    // MySQL 模式
                    hc.driverClassName = config?.getString("database.mysql.driver")
                    hc.jdbcUrl =
                        "jdbc:mysql://${config?.getString("database.mysql.host")}:${config?.getString("database.mysql.port")}/${
                            config?.getString("database.mysql.database")
                        }"
                    hc.username = config?.getString("database.mysql.username")
                    hc.password = config?.getString("database.mysql.host")
                    if (config?.getBoolean("database.mysql.keepalive.enable") == true) hc.keepaliveTime =
                        config.getLong("database.mysql.keepalive.time")
                }

                "sqlite" -> run {
                    // SQLite 模式
                    hc.driverClassName = config?.getString("database.sqlite.driver")
                    hc.jdbcUrl = "jdbc:sqlite:${instance.dataFolder}/${config?.getString("database.sqlite.path")}"
                }

                else ->
                    // 不支持的数据库类型
                    Logger.fatal("Invalid database type provided!")
            }
            hds = HikariDataSource(hc)
            try {
                Database().initTable()
            } catch (e: Exception) {
                Logger.fatal("Failed init database tables!", e)
            }
        }
    }
}