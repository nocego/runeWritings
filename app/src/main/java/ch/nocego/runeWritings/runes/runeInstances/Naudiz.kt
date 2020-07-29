package ch.nocego.runeWritings.runes.runeInstances

import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.runes.Rune
import java.util.*

class Naudiz : Rune {
    private val correspondingLetter: String = "N"
    private val unicodeSymbol: String = "\u16BE"
    private val name: String = ContextHolder.getMainContext().getString(R.string.nameNaudiz)
    private val description: String =
        ContextHolder.getMainContext().getString(R.string.descriptionNaudiz)
    private val urlEn: String = "https://en.wikipedia.org/wiki/Naudiz"
    private val urlDe: String = "https://de.wikipedia.org/wiki/Naudiz"

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