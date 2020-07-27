package ch.nocego.runeWritings.runicAlphabet

import android.os.Bundle
import android.view.Menu
import android.widget.ListView
import android.widget.Switch
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.runes.ObjectTranspiler.Companion.transpileActionBar
import ch.nocego.runeWritings.runes.ObjectTranspiler.Companion.transpileText
import ch.nocego.runeWritings.sharedPrefs.SharedPrefs.Companion.getSharedPrefs
import ch.nocego.runeWritings.sharedPrefs.SharedPrefs.Companion.getUseRunes
import java.util.*

class RunicAlphabetActivity : AppCompatActivity() {

    var actionbar: ActionBar? = null
    var useRunesSwitch: Switch? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_runic_alphabet)

        actionbar = supportActionBar
        actionbar!!.title = getString(R.string.runicAlphabet).toUpperCase(Locale.ROOT)

        val listView = findViewById<ListView>(R.id.runicAlphabetListView)
        listView.adapter = RunicAlphabetAdapter(this)
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

    private fun transpileTexts() {
        transpileActionBar(R.string.runicAlphabet, actionbar!!)
        transpileText(useRunesSwitch!!, R.string.useRunes)
    }
}
