package ch.nocego.runeWritings.runes

import java.lang.StringBuilder

class LetterToRunes {
    fun getRunesFromText(s: String): String {
        val stringBuilder = StringBuilder()

        for(i in s.indices){
            val runicLetter = getRuneOfOneChar(s[i])
            stringBuilder.append(runicLetter)
        }
        return stringBuilder.toString()
    }

    private fun getRuneOfOneChar(l: Char): String {
        val kaun = "\u16B4"
        val unicodeSymbol: String
        when (l) {
            'a', 'A' -> unicodeSymbol = "\u16A8"
            'b', 'B' -> unicodeSymbol = "\u16D2"
            'c', 'C' -> unicodeSymbol = kaun
            'd', 'D' -> unicodeSymbol = "\u16DE"
            'e', 'E' -> unicodeSymbol = "\u16D6"
            'f', 'F' -> unicodeSymbol = "\u16A0"
            'g', 'G' -> unicodeSymbol = "\u16B7"
            'h', 'H' -> unicodeSymbol = "\u16BA"
            'i', 'I' -> unicodeSymbol = "\u16C1"
            'j', 'J' -> unicodeSymbol = "\u16C3"
            'k', 'K' -> unicodeSymbol = kaun
            'l', 'L' -> unicodeSymbol = "\u16DA"
            'm', 'M' -> unicodeSymbol = "\u16D7"
            'n', 'N' -> unicodeSymbol = "\u16BE"
            'o', 'O' -> unicodeSymbol = "\u16DF"
            'p', 'P' -> unicodeSymbol = "\u16C8"
            'q', 'Q' -> unicodeSymbol = kaun
            'r', 'R' -> unicodeSymbol = "\u16B1"
            's', 'S' -> unicodeSymbol = "\u16CB"
            't', 'T' -> unicodeSymbol = "\u16CF"
            'u', 'U' -> unicodeSymbol = "\u16A2"
            'v', 'V' -> unicodeSymbol = "\u16B9"
            'w', 'W' -> unicodeSymbol = "\u16B9"
            'x', 'X' -> unicodeSymbol = "\u16C7"
            'y', 'Y' -> unicodeSymbol = "\u16C3"
            'z', 'Z' -> unicodeSymbol = "\u16C9"
            else -> {
                unicodeSymbol = l.toString()
            }
        }
        return unicodeSymbol
    }
}