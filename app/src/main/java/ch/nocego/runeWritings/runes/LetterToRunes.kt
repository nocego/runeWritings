package ch.nocego.runeWritings.runes

import ch.nocego.runeWritings.runes.runeInstances.*

class LetterToRunes {
    fun getRunesFromText(s: String): String {
        val stringBuilder = StringBuilder()

        for(i in s.indices){
            val runicLetter = getRuneUnicodeOfOneChar(s[i])
            stringBuilder.append(runicLetter)
        }
        return stringBuilder.toString()
    }

    fun getUpperCaseTextFromRunes(unicodeRunes: String): String {
        val stringBuilder = StringBuilder()

        for (i in unicodeRunes.indices) {
            val letter = getUppercaseLetterFromUnicodeSymbol(unicodeRunes)
            stringBuilder.append(letter)
        }
        return stringBuilder.toString()
    }

    fun getRuneInstanceOfOneChar(c: Char): Rune? {
        var rune: Rune? = null
        when (c) {
            'a', 'A' -> rune = Ansuz()
            'b', 'B' -> rune = Berkanan()
            'c', 'C' -> rune = Kaun(0)
            'd', 'D' -> rune = Dagaz()
            'e', 'E' -> rune = Ehwaz()
            'f', 'F' -> rune = Fehu()
            'g', 'G' -> rune = Gyfu()
            'h', 'H' -> rune = Haglaz()
            'i', 'I' -> rune = Isaz()
            'j', 'J' -> rune = Jeran(0)
            'k', 'K' -> rune = Kaun(1)
            'l', 'L' -> rune = Laguz()
            'm', 'M' -> rune = Mannaz()
            'n', 'N' -> rune = Naudiz()
            'o', 'O' -> rune = Odal()
            'p', 'P' -> rune = Peord()
            'q', 'Q' -> rune = Kaun(2)
            'r', 'R' -> rune = Raido()
            's', 'S' -> rune = Sigel()
            't', 'T' -> rune = Tiwaz()
            'u', 'U' -> rune = Ur()
            'v', 'V' -> rune = Wynn(0)
            'w', 'W' -> rune = Wynn(1)
            'x', 'X' -> rune = Eihwaz()
            'y', 'Y' -> rune = Jeran(1)
            'z', 'Z' -> rune = Algiz()
        }

        return rune
    }

    private fun getUppercaseLetterFromUnicodeSymbol(runeUnicode: String): String? {
        var letter: String? = null
        when (runeUnicode) {
            Ansuz().unicodeSymbol() -> letter = "A"
            Berkanan().unicodeSymbol() -> letter = "B"
            Kaun(0).unicodeSymbol() -> letter = "C"
            Dagaz().unicodeSymbol() -> letter = "D"
            Ehwaz().unicodeSymbol() -> letter = "E"
            Fehu().unicodeSymbol() -> letter = "F"
            Gyfu().unicodeSymbol() -> letter = "G"
            Haglaz().unicodeSymbol() -> letter = "H"
            Isaz().unicodeSymbol() -> letter = "I"
            Jeran(0).unicodeSymbol() -> letter = "J"
            Kaun(1).unicodeSymbol() -> letter = "K"
            Laguz().unicodeSymbol() -> letter = "L"
            Mannaz().unicodeSymbol() -> letter = "M"
            Naudiz().unicodeSymbol() -> letter = "N"
            Odal().unicodeSymbol() -> letter = "O"
            Peord().unicodeSymbol() -> letter = "P"
            Kaun(2).unicodeSymbol() -> letter = "Q"
            Raido().unicodeSymbol() -> letter = "R"
            Sigel().unicodeSymbol() -> letter = "S"
            Tiwaz().unicodeSymbol() -> letter = "T"
            Ur().unicodeSymbol() -> letter = "U"
            Wynn(0).unicodeSymbol() -> letter = "V"
            Wynn(1).unicodeSymbol() -> letter = "W"
            Eihwaz().unicodeSymbol() -> letter = "X"
            Jeran(1).unicodeSymbol() -> letter = "Y"
            Algiz().unicodeSymbol() -> letter = "Z"
        }

        return letter
    }

    private fun getRuneUnicodeOfOneChar(c: Char): String {
        val stringBuilder = StringBuilder()

        when (c) {
            'ä', 'Ä' -> {
                stringBuilder.append(Ansuz().unicodeSymbol())
                stringBuilder.append(Ehwaz().unicodeSymbol())
            }
            'ö', 'Ö' -> {
                stringBuilder.append(Odal().unicodeSymbol())
                stringBuilder.append(Ehwaz().unicodeSymbol())
            }
            'ü', 'Ü' -> {
                stringBuilder.append(Ur().unicodeSymbol())
                stringBuilder.append(Ehwaz().unicodeSymbol())
            }
            else -> {
                stringBuilder.append(getRuneInstanceOfOneChar(c)?.unicodeSymbol() ?: c.toString())
            }
        }
        return stringBuilder.toString()
    }
}