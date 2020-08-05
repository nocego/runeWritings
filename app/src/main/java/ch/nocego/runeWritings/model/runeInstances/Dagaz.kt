package ch.nocego.runeWritings.model.runeInstances

import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.model.Rune
import java.util.*

class Dagaz : Rune {
    private val correspondingLetter: String = "D"
    private val unicodeSymbol: String = "\u16DE"
    private val name: String = ContextHolder.getMainContext().getString(R.string.nameDagaz)
    private val description: String =
        ContextHolder.getMainContext().getString(R.string.descriptionDagaz)
    private val urlEn: String = "https://en.wikipedia.org/wiki/Dagaz"
    private val urlDe: String = "https://de.wikipedia.org/wiki/Dagaz"

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