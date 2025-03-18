package me.siwakornsep.realitylib

import org.bukkit.entity.Player

import java.security.MessageDigest
import kotlin.random.Random

fun generateRandomHash(length: Int = 32, algorithm: String = "SHA-256"): String {
    require(length > 0) { "Length must be positive" }

    val randomBytes = ByteArray(length)
    Random.Default.nextBytes(randomBytes)

    val digest = MessageDigest.getInstance(algorithm)
    val hashBytes = digest.digest(randomBytes)

    return hashBytes.joinToString("") { "%02x".format(it) } // Convert to hexadecimal string
}

fun generateRandomStringHash(inputString: String, algorithm: String = "SHA-256"): String {
    val digest = MessageDigest.getInstance(algorithm)
    val hashBytes = digest.digest(inputString.toByteArray())
    return hashBytes.joinToString("") { "%02x".format(it) }
}

class Pulldata {
    fun createdPlayerDataOnDatabase(player: Player) {
        var UUIDS = player.uniqueId
        var gameName = player.name
        val packetToken = generateRandomHash() // cookie
        var databasePacketToken = generateRandomStringHash(packetToken)

    }
}