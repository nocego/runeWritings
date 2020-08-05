package ch.nocego.runeWritings.letterToRuneQuiz

import android.os.Bundle
import android.view.Menu
import android.widget.Switch
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.model.Transpiler.Companion.transpileActionBar
import ch.nocego.runeWritings.model.Transpiler.Companion.transpileTextResourceOnTextView
import ch.nocego.runeWritings.sharedPrefs.SharedPrefs
import ch.nocego.runeWritings.sharedPrefs.SharedPrefs.Companion.getUseRunes
import java.util.*

class LetterToRuneQuizActivity : AppCompatActivity() {

    var actionbar: ActionBar? = null
    var useRunesSwitch: Switch? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_to_rune_quiz)

        actionbar = supportActionBar
        actionbar!!.title = getString(R.string.letterToRuneQuiz).toUpperCase(Locale.ROOT)
        actionbar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu!!.findItem(R.id.useRunesMenuItem)
        item.setActionView(R.layout.switch_layout)

        val mySwitch = item.actionView.findViewById<Switch>(R.id.useRunesSwitch)
        useRunesSwitch = mySwitch
        mySwitch.setOnCheckedChangeListener { _, isChecked ->
            val editor = SharedPrefs.getSharedPrefs().edit()
            editor.putBoolean("useRunes", isChecked)
            editor.apply()
            transpileTexts()
        }

        useRunesSwitch!!.isChecked = getUseRunes()
        transpileTexts()

        return true
    }

    private fun transpileTexts() {
        transpileActionBar(R.string.letterToRuneQuiz, actionbar!!)
        transpileTextResourceOnTextView(useRunesSwitch!!, R.string.useRunes)
    }
}
