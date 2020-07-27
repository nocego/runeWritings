package ch.nocego.runeWritings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import ch.nocego.runeWritings.runes.LetterToRunes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val ltr = LetterToRunes()
    var actionbar: ActionBar? = null
    var useRunesSwitch: Switch? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionbar = supportActionBar
        actionbar!!.title = getText(R.string.title)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu!!.findItem(R.id.useRunesMenuItem)
        item.setActionView(R.layout.switch_layout)

        val mySwitch = item.actionView.findViewById<Switch>(R.id.useRunesSwitch)
        useRunesSwitch = mySwitch
        mySwitch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                val editor = getSharedPrefs().edit()
                editor.putBoolean("useRunes", useRunesSwitch!!.isChecked)
                editor.apply()
                checkRunicTexts()
            }
        })

        checkSwitch()
        checkRunicTexts()

        return true
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
        useRunesSwitch!!.isChecked = switchState
    }

    private fun checkRunicTexts() {
        transpileActionBar(R.string.title)
        transpileText(useRunesSwitch!!, R.string.useRunes)
        transpileText(mainActivityTitle, R.string.title)
        transpileText(description, R.string.appDescription)
        transpileText(buttonGenerateUnicodeRunes, R.string.generateUnicodeRunes)
        transpileText(buttonRunicAlphabet, R.string.runicAlphabet)
    }

    private fun transpileText(tv: TextView, resourceId: Int) {
        if(useRunesSwitch!!.isChecked){
            tv.text = ltr.getRunesFromText(getString(resourceId))
        }else{
            tv.text = getString(resourceId)
        }
    }

    private fun transpileActionBar(resourceId: Int) {
        if(useRunesSwitch!!.isChecked){
            actionbar!!.title = ltr.getRunesFromText(getString(resourceId))
        }else{
            actionbar!!.title = getString(resourceId)
        }
    }
}
