package ch.nocego.runeWritings.runes.runeInstances

import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.runes.Rune
import java.util.*

class Peord : Rune {
    private val correspondingLetter: String = "P"
    private val unicodeSymbol: String = "\u16C8"
    private val name: String = ContextHolder.getMainContext().getString(R.string.namePeord)
    private val description: String =
        ContextHolder.getMainContext().getString(R.string.descriptionPeord)
    private val urlEn: String = "https://en.wikipedia.org/wiki/Peor%C3%B0"
    private val urlDe: String = "https://de.wikipedia.org/wiki/Perthro"

    override fun correspondingLetter(): String {
        return correspondingLetter
    }

    override fun unicodeSymbol(): String {
        return unicodeSymbol
    }

    override fun name(): String {
        return name
    }

    override fun description(): String {
        return description
    }

    override fun url(): String {
        return if (Locale.getDefault().language == "de") {
            urlDe
        } else {
            urlEn
        }
    }
}