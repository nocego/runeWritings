package ch.nocego.runeWritings.contextHolder

import android.content.Context

class ContextHolder {
    companion object {
        private lateinit var context: Context

        fun setContext(con: Context) {
            context = con
        }

        fun getMainContext(): Context {
            return context
        }
    }
}