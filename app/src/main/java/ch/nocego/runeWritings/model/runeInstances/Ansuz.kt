package ch.nocego.runeWritings.model.runeInstances

import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.contextHolder.ContextHolder.Companion.getMainContext
import ch.nocego.runeWritings.model.Rune
import java.util.*

class Ansuz : Rune {
    private val correspondingLetter: String = "A"
    private val unicodeSymbol: String = "\u16A8"
    private val name: String = getMainContext().getString(R.string.nameAnsuz)
    private val description: String = getMainContext().getString(R.string.descriptionAnsuz)
    private val urlEn: String = "https://en.wikipedia.org/wiki/Ansuz_(rune)"
    private val urlDe: String = "https://de.wikipedia.org/wiki/Ansuz"

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