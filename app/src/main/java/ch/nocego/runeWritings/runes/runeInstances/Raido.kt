package ch.nocego.runeWritings.runes.runeInstances

import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.runes.Rune
import java.util.*

class Raido : Rune {
    private val correspondingLetter: String = "R"
    private val unicodeSymbol: String = "\u16B1"
    private val name: String = ContextHolder.getMainContext().getString(R.string.nameRaido)
    private val description: String =
        ContextHolder.getMainContext().getString(R.string.descriptionRaido)
    private val urlEn: String = "https://en.wikipedia.org/wiki/Raido"
    private val urlDe: String = "https://de.wikipedia.org/wiki/Raidho"

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