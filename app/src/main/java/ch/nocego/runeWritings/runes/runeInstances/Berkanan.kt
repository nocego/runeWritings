package ch.nocego.runeWritings.runes.runeInstances

import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.contextHolder.ContextHolder.Companion.getMainContext
import ch.nocego.runeWritings.runes.Rune
import java.util.*

class Berkanan : Rune {
    private val correspondingLetter: String = "B"
    private val unicodeSymbol: String = "\u16D2"
    private val name: String = getMainContext().getString(R.string.nameBerkanan)
    private val description: String = getMainContext().getString(R.string.descriptionBerkanan)
    private val urlEn: String = "https://en.wikipedia.org/wiki/Berkanan"
    private val urlDe: String = "https://de.wikipedia.org/wiki/Berkano"

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