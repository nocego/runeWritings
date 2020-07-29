package ch.nocego.runeWritings.runes

interface Rune {
    fun correspondingLetter(): String
    fun unicodeSymbol(): String
    fun name(): String
    fun description(): String
    fun url(): String
}