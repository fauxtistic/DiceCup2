package com.example.dicecup

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class DiceThrow (var time: Date, var dies: Array<Int> ) : Serializable {

    override fun toString(): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss")
        val minAndSec = dateFormat.format(time)
        var results = "$minAndSec: "
        dies.forEach { d -> results += "${d} - " }
        results = results.dropLast(3)
        results += "\n"
        return results
    }

    fun getTimeString(): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss")
        val minAndSec = dateFormat.format(time)
        return minAndSec
    }

    fun getDiceString(): String {
        var results = ""
        dies.forEach { d -> results += "${d} - " }
        results = results.dropLast(3)
        return results
    }
}