package ch.nocego.runeWritings.model

import android.widget.TextView
import androidx.appcompat.app.ActionBar
import ch.nocego.runeWritings.contextHolder.ContextHolder.Companion.getMainContext
import ch.nocego.runeWritings.sharedPrefs.SharedPrefs
import java.util.*

class Transpiler {
    companion object {
        private val ltr = LetterToRunes()

        fun transpileActionBar(resourceId: Int, actionBar: ActionBar) {
            if (SharedPrefs.getUseRunes()) {
                actionBar.title = ltr.getRunesFromText(getMainContext().getString(resourceId))
            } else {
                actionBar.title = getMainContext().getString(resourceId).toUpperCase(Locale.ROOT)
            }
        }

        fun transpileTextResourceOnTextView(tv: TextView, resourceId: Int) {
            if (SharedPrefs.getUseRunes()) {
                tv.text = ltr.getRunesFromText(getMainContext().getString(resourceId))
            } else {
                tv.text = getMainContext().getString(resourceId)
            }
        }

        fun transpileTextOnTextView(tv: TextView, s: String) {
            if (SharedPrefs.getUseRunes()) {
                tv.text = ltr.getRunesFromText(s)
            } else {
                tv.text = s
            }
        }

        fun transpileText(s: String): String {
            return if (SharedPrefs.getUseRunes()) {
                ltr.getRunesFromText(s)
            } else {
                s
            }
        }

        fun transpileRuneOpposite(tv: TextView, unicodeRune: String) {
            if (SharedPrefs.getUseRunes()) {
                tv.text = ltr.getUpperCaseTextFromRunes(unicodeRune)
            } else {
                tv.text = unicodeRune
            }
        }

        fun runeByLetter(letter: String): Rune {
            return ltr.getRuneInstanceOfOneChar(letter[0])!!
        }

        fun runeUnicodeByLetter(letter: String): String {
            return ltr.getRuneInstanceOfOneChar(letter[0])!!.unicodeSymbol()
        }
    }
}