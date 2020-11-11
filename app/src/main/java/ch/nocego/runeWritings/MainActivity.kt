package ch.nocego.runeWritings

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.letterToRuneQuiz.LetterToRuneQuizActivity
import ch.nocego.runeWritings.model.Transpiler.Companion.transpileActionBar
import ch.nocego.runeWritings.model.Transpiler.Companion.transpileTextResourceOnTextView
import ch.nocego.runeWritings.runeToLetterQuiz.RuneToLetterQuizActivity
import ch.nocego.runeWritings.runicAlphabet.RunicAlphabetActivity
import ch.nocego.runeWritings.sharedPrefs.SharedPrefs.Companion.getSharedPrefs
import ch.nocego.runeWritings.sharedPrefs.SharedPrefs.Companion.getUseRunes
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var actionbar: ActionBar? = null
    var useRunesSwitch: Switch? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionbar = supportActionBar
        actionbar!!.title = getString(R.string.title).toUpperCase(Locale.ROOT)

        ContextHolder.setContext(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu!!.findItem(R.id.useRunesMenuItem)
        item.setActionView(R.layout.switch_layout)


        val mySwitch = item.actionView.findViewById<Switch>(R.id.useRunesSwitch)
        useRunesSwitch = mySwitch
        mySwitch.setOnCheckedChangeListener { _, isChecked ->
            val editor = getSharedPrefs().edit()
            editor.putBoolean("useRunes", isChecked)
            editor.apply()
            transpileTexts()
        }

        useRunesSwitch!!.isChecked = getUseRunes()
        transpileTexts()

        return true
    }

    override fun onResume() {
        super.onResume()
        useRunesSwitch?.isChecked = getUseRunes()
    }

    fun runicAlphabetIntent(v: View) {
        val intent = Intent(this, RunicAlphabetActivity::class.java)
        startActivity(intent)
    }

    fun letterToRuneQuizIntent(v: View) {
        val intent = Intent(this, LetterToRuneQuizActivity::class.java)
        startActivity(intent)
    }

    fun runeToLetterQuizIntent(v: View) {
        val intent = Intent(this, RuneToLetterQuizActivity::class.java)
        startActivity(intent)
    }

    private fun transpileTexts() {
        transpileActionBar(R.string.title, actionbar!!)
        transpileTextResourceOnTextView(useRunesSwitch!!, R.string.useRunes)
        transpileTextResourceOnTextView(mainActivityTitle, R.string.title)
        transpileTextResourceOnTextView(description, R.string.appDescription)
        transpileTextResourceOnTextView(buttonRunicAlphabet, R.string.runicAlphabet)
        transpileTextResourceOnTextView(buttonletterToRuneQuiz, R.string.letterToRuneQuiz)
        transpileTextResourceOnTextView(copyright, R.string.copyright)
        transpileTextResourceOnTextView(transpilationNote, R.string.transpilationNote)
        transpileTextResourceOnTextView(buttonRuneToLetterQuiz, R.string.runeToLetterQuiz)
    }
}
