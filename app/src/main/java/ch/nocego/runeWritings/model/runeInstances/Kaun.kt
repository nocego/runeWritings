package ch.nocego.runeWritings.model.runeInstances

import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.contextHolder.ContextHolder.Companion.getMainContext
import ch.nocego.runeWritings.model.Rune
import java.util.*

class Kaun(private val i: Int) : Rune {
    private val correspondingLetter: Array<String> = arrayOf("C", "K", "Q")
    private val unicodeSymbol: String = "\u16B4"
    private val name: String = getMainContext().getString(R.string.nameKaun)
    private val description: String = getMainContext().getString(R.string.descriptionKaun)
    private val urlEn: String = "https://en.wikipedia.org/wiki/Kaunan"
    private val urlDe: String = "https://de.wikipedia.org/wiki/Kenaz"

    override fun correspondingLetter(): String {
        return correspondingLetter[i]
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