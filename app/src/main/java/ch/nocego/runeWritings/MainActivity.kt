package ch.nocego.runeWritings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import ch.nocego.runeWritings.runes.LetterToRunes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val ltr = LetterToRunes()
    var actionbar: ActionBar? = null
    lateinit var appBarSwitch: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionbar = supportActionBar
        actionbar!!.title = getText(R.string.title)

        checkSwitch()
        checkRunicTexts()
    }

    fun switchRunesToggle(v: View) {
        val editor = getSharedPrefs().edit()
        editor.putBoolean("useRunes", switchRunes.isChecked)
        editor.apply()
        checkRunicTexts()
    }

    fun generateUnicodeRunesIntent(v: View) {
        val intent = Intent(this, UnicodeActivity::class.java)
        startActivity(intent)
    }

    private fun getSharedPrefs() : SharedPreferences {
        return getSharedPreferences("RUNES_INSTED_OF_LETTERS", Context.MODE_PRIVATE)
    }

    private fun checkSwitch() {
        val switchState = getSharedPrefs().getBoolean("useRunes", false)
        switchRunes.isChecked = switchState
    }

    private fun checkRunicTexts() {
        transpileText(mainActivityTitle, R.string.title)
        transpileText(description, R.string.appDescription)
        transpileText(switchRunes, R.string.useRunesInsteadOfLetters)
        transpileText(buttonGenerateUnicodeRunes, R.string.generateUnicodeRunes)
        transpileActionBar(R.string.title)
    }

    private fun transpileText(tv: TextView, resourceId: Int) {
        if(switchRunes.isChecked){
            tv.text = ltr.getRunesFromText(getString(resourceId))
        }else{
            tv.text = getString(resourceId)
        }
    }

    private fun transpileActionBar(resourceId: Int) {
        if(switchRunes.isChecked){
            actionbar!!.title = ltr.getRunesFromText(getString(resourceId))
        }else{
            actionbar!!.title = getString(resourceId)
        }
    }
}
