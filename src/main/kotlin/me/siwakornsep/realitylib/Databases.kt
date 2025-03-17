package me.siwakornsep.realitylib

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.UUID

class Databases(private val host: String, private val database: String, private val username: String, private val password: String) {

    private var connection: Connection? = null

    fun getConnection(): Connection? {
        try {
            if (connection == null || connection?.isClosed == true) {
                Class.forName("com.mysql.cj.jdbc.Driver") // Load the MySQL driver
                val url = "jdbc:mysql://$host/$database"
                connection = DriverManager.getConnection(url, username, password)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return connection
    }

    fun closeConnection() {
        try {
            connection?.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}

class playerDatabases(private val databaseManager: Databases) {
    fun getPlayerDataFromUuid(uuid : UUID, column: String, table: String): String? {
        var data: String? = null
        try {
            databaseManager.getConnection()?.use { connection: Connection -> // Explicitly specify the Connection type
                connection.prepareStatement("SELECT $column FROM $table WHERE uuid = ?").use { statement -> // Replace ... with your actual SQL query
                    statement.setString(1, uuid.toString())
                    statement.executeQuery().use { resultSet ->
                        if (resultSet.next()) {
                            data = resultSet.getString(column)
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return data
    }
}