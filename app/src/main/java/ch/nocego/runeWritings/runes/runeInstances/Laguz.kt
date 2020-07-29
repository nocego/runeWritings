package ch.nocego.runeWritings.runes.runeInstances

import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.runes.Rune
import java.util.*

class Laguz : Rune {
    private val correspondingLetter: String = "L"
    private val unicodeSymbol: String = "\u16DA"
    private val name: String = ContextHolder.getMainContext().getString(R.string.nameLaguz)
    private val description: String =
        ContextHolder.getMainContext().getString(R.string.descriptionLaguz)
    private val urlEn: String = "https://en.wikipedia.org/wiki/Laguz"
    private val urlDe: String = "https://de.wikipedia.org/wiki/Laguz"

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