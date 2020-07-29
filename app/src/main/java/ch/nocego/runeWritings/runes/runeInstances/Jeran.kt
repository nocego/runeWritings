package ch.nocego.runeWritings.runes.runeInstances

import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.runes.Rune
import java.util.*

class Jeran(private val i: Int) : Rune {
    private val correspondingLetter: Array<String> = arrayOf("J", "Y")
    private val unicodeSymbol: String = "\u16C3"
    private val name: String = ContextHolder.getMainContext().getString(R.string.nameJeran)
    private val description: String =
        ContextHolder.getMainContext().getString(R.string.descriptionJeran)
    private val urlEn: String = "https://en.wikipedia.org/wiki/J%C4%93ran"
    private val urlDe: String = "https://de.wikipedia.org/wiki/Jera"

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