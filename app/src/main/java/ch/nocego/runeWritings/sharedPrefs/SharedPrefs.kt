package ch.nocego.runeWritings.sharedPrefs

import android.content.Context
import android.content.SharedPreferences
import ch.nocego.runeWritings.contextHolder.ContextHolder.Companion.getMainContext

class SharedPrefs {

    companion object {

        fun getSharedPrefs(): SharedPreferences {
            return getMainContext().getSharedPreferences(
                "RUNES_INSTED_OF_LETTERS",
                Context.MODE_PRIVATE
            )
        }

        fun getUseRunes(): Boolean {
            return getSharedPrefs().getBoolean("useRunes", false)
        }
    }
}