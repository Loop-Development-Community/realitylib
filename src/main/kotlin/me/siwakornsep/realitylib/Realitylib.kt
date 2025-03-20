package me.siwakornsep.realitylib

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import org.bukkit.plugin.java.JavaPlugin


class Realitylib : JavaPlugin() {

    private lateinit var databaseManager: Databases
    private lateinit var playerDatabases: PlayerDatabases
    private lateinit var protocolManager: ProtocolManager

    override fun onEnable() {
        databaseManager = Databases("localhost:3306", "realityschema", "root", "4563")
        playerDatabases = PlayerDatabases(databaseManager)
        protocolManager = ProtocolLibrary.getProtocolManager()
        logger.info("RealityLIB Started Correctly")
    }

    override fun onDisable() {
        databaseManager.closeConnection()
        logger.info("RealityLIB Closing Correctly")
    }
}

