package me.siwakornsep.realitylib

import org.bukkit.plugin.java.JavaPlugin


class Realitylib : JavaPlugin() {

    private lateinit var databaseManager: Databases

    override fun onEnable() {
        databaseManager = Databases("localhost:3306", "realityschema", "root", "4563")
        logger.info("RealityLIB Started Correctly")
    }

    override fun onDisable() {
        databaseManager.closeConnection()
        logger.info("RealityLIB Closing Correctly")
    }
}

