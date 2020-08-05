package ch.nocego.runeWritings.model.runeInstances

import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.model.Rune
import java.util.*

class Gyfu : Rune {
    private val correspondingLetter: String = "G"
    private val unicodeSymbol: String = "\u16B7"
    private val name: String = ContextHolder.getMainContext().getString(R.string.nameGyfu)
    private val description: String =
        ContextHolder.getMainContext().getString(R.string.descriptionGyfu)
    private val urlEn: String = "https://en.wikipedia.org/wiki/Gyfu"
    private val urlDe: String = "https://de.wikipedia.org/wiki/Gebo"

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