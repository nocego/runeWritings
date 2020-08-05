package ch.nocego.runeWritings.model.runeInstances

import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.model.Rune
import java.util.*

class Wynn(private val i: Int) : Rune {
    private val correspondingLetter: Array<String> = arrayOf("V", "W")
    private val unicodeSymbol: String = "\u16B9"
    private val name: String = ContextHolder.getMainContext().getString(R.string.nameWynn)
    private val description: String =
        ContextHolder.getMainContext().getString(R.string.descriptionWynn)
    private val urlEn: String = "https://en.wikipedia.org/wiki/Wynn"
    private val urlDe: String = "https://de.wikipedia.org/wiki/Wunjo"

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