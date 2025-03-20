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

class PlayerDatabases(private val databaseManager: Databases) {
    fun getPlayerDataFromUuid(uuid : UUID, column: String, table: String): String? {
        val data = getPlayerData("uuid", uuid.toString(), column, table)
        return data
    }
    fun getPlayerDataFromRid(rid : Double, column: String, table: String): String? {
        val data = getPlayerData("RID", rid.toString(), column, table)
        return data
    }
    private fun getPlayerData (iden: String, id: String, column: String, table: String): String? {
        var data: String? = null
        try {
            databaseManager.getConnection()?.use { connection: Connection ->
                connection.prepareStatement("SELECT $column FROM $table WHERE $iden = ?").use { statement ->
                    if (iden == "uuid") {
                        statement.setString(1, id)
                    }
                    if (iden == "RID") {
                        statement.setDouble(1, id.toDouble())
                    }
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
    fun setPlayerDataStringFromUuid(uuid: UUID, column: String, table: String, data : String) {
        setPlayerData("uuid", uuid.toString(), column, table, data)
    }
    fun setPlayerDataStringFromRid(rid: Double, column: String, table: String, data : String) {
        setPlayerData("RID", rid.toString(), column, table, data)
    }

    private fun setPlayerData(iden: String, id: String, column: String, table: String, data: String) {
        try {
            databaseManager.getConnection()?.use { connection ->
                connection.prepareStatement("UPDATE $table SET $column = ? WHERE $iden = ?").use { statement ->
                    statement.setString(1, data)
                    if (iden == "uuid") {
                        statement.setString(2, id)
                    }
                    if (iden == "RID") {
                        statement.setDouble(2, id.toDouble())
                    }

                    statement.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
    private fun getHighestValue (column: String, table: String): Int? {
        var highestValue: Int? = null
        try {
            databaseManager.getConnection()?.use { connection: Connection ->
                connection.prepareStatement("SELECT MAX($column) FROM $table").use { statement ->
                    statement.executeQuery().use { resultSet ->
                        if (resultSet.next()) {
                            highestValue = resultSet.getInt(1) // Get the first column's value (MAX(columnName))
                            if (resultSet.wasNull()) {
                                highestValue = null //Handle null case, important!
                            }
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return highestValue
    }
    fun createPlayerMainData(uuid: UUID, gameName: String, packetToken: String) {
        val sQL = "INSERT INTO mainauthdata (RID, uuid, Gamename, PacketToken, Email, Username, Displayname, Password, GoogleToken, DiscordToken, XToken, GithubToken, TiktokToken, InstagramToken, TwitchToken) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
        try {
            databaseManager.getConnection()?.use { connection ->
                connection.prepareStatement(sQL).use { statement ->
                    statement.setDouble(1, 0.0)
                    statement.setString(2, uuid.toString())
                    statement.setString(3, gameName)
                    statement.setString(4, packetToken)
                    statement.setString(5, "0xe")
                    statement.setString(6, "0xu")
                    statement.setString(7, "0xd")
                    statement.setString(8, "0xp")
                    statement.setString(9, "0xgt")
                    statement.setString(10, "0xdt")
                    statement.setString(11, "0xxt")
                    statement.setString(12, "0xght")
                    statement.setString(13, "0xttt")
                    statement.setString(14, "0xit")
                    statement.setString(15, "0xtt")
                    statement.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}