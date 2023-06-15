package com.example.losdelaheroica.addSong.ui.utils

import com.example.losdelaheroica.addSong.ui.model.Rhythm

object Utils {

    fun numberToTone(number: Int): String {
        return when (number) {
            1 -> "Do"
            2 -> "Reb"
            3 -> "Re"
            4 -> "Mib"
            5 -> "Mi"
            6 -> "Fa"
            7 -> "Solb"
            8 -> "Sol"
            9 -> "Lab"
            10 -> "La"
            11 -> "Sib"
            12 -> "Si"
            else -> "Fa"
        }
    }

    fun toneToNumber(tone: String): Int {
        return when (tone) {
            "Do" -> 1
            "Reb" -> 2
            "Re" -> 3
            "Mib" -> 4
            "Mi" -> 5
            "Fa" -> 6
            "Solb" -> 7
            "Sol" -> 8
            "Lab" -> 9
            "La" -> 10
            "Sib" -> 11
            "Si" -> 12
            else -> 6
        }
    }

    fun stringToRhythm(rhythmString: String): Rhythm {
        var rhythmToReturn: Rhythm = Rhythm.Nortena
        val listRhythm = Rhythm::class.sealedSubclasses.mapNotNull { it.objectInstance }
        listRhythm.forEach { rhythm ->
            if(rhythmString.contains(songsToString(rhythm))){
                rhythmToReturn = rhythm
            }
        }
        return rhythmToReturn
    }

    fun songsToString(rhythmSelected: Rhythm): String {
        return when (rhythmSelected) {
            Rhythm.Corrido -> "Corrido"
            Rhythm.Cumbia -> "Cumbia"
            Rhythm.Huapango -> "Huapango"
            Rhythm.Nortena -> "Norteña"
            Rhythm.Zapateado -> "Zapateado"
        }
    }
}