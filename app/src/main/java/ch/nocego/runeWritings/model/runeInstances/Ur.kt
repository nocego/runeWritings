package ch.nocego.runeWritings.model.runeInstances

import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.model.Rune
import java.util.*

class Ur : Rune {
    private val correspondingLetter: String = "U"
    private val unicodeSymbol: String = "\u16A2"
    private val name: String = ContextHolder.getMainContext().getString(R.string.nameUr)
    private val description: String =
        ContextHolder.getMainContext().getString(R.string.descriptionUr)
    private val urlEn: String = "https://en.wikipedia.org/wiki/Ur_(rune)"
    private val urlDe: String = "https://de.wikipedia.org/wiki/Uruz"

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