package ch.nocego.runeWritings.model.runeInstances

import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.model.Rune
import java.util.*

class Sigel : Rune {
    private val correspondingLetter: String = "S"
    private val unicodeSymbol: String = "\u16CB"
    private val name: String = ContextHolder.getMainContext().getString(R.string.nameSigel)
    private val description: String =
        ContextHolder.getMainContext().getString(R.string.descriptionSigel)
    private val urlEn: String = "https://en.wikipedia.org/wiki/Sowil%C5%8D"
    private val urlDe: String = "https://de.wikipedia.org/wiki/Sowilo"

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