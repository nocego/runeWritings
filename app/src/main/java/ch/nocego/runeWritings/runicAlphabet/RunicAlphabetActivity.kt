package ch.nocego.runeWritings.runicAlphabet

import android.os.Bundle
import android.view.Menu
import android.widget.Switch
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.model.Transpiler.Companion.transpileActionBar
import ch.nocego.runeWritings.model.Transpiler.Companion.transpileTextResourceOnTextView
import ch.nocego.runeWritings.runicAlphabet.furtherRuneInformation.FragmentFurtherRuneInformation
import ch.nocego.runeWritings.runicAlphabet.furtherRuneInformation.FurtherRuneInformationAdapter
import ch.nocego.runeWritings.runicAlphabet.runicAlphabet.FragmentRunicAlphabet
import ch.nocego.runeWritings.sharedPrefs.SharedPrefs
import ch.nocego.runeWritings.sharedPrefs.SharedPrefs.Companion.getUseRunes
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_runic_alphabet.*
import kotlinx.android.synthetic.main.further_rune_information.*
import java.util.*

class RunicAlphabetActivity : AppCompatActivity() {

    var actionbar: ActionBar? = null
    var useRunesSwitch: Switch? = null
    var adapter: MyViewPagerAdapter? = null
    var fragmentRunicAlphabet: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_runic_alphabet)

        actionbar = supportActionBar
        actionbar!!.title = getString(R.string.runicAlphabet).toUpperCase(Locale.ROOT)
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        val adapter = MyViewPagerAdapter(supportFragmentManager)
        this.adapter = adapter
        val fragmentFurtherRuneInformation =
            FragmentFurtherRuneInformation()
        fragmentRunicAlphabet = FragmentRunicAlphabet()
        adapter.addFragment(fragmentRunicAlphabet!!, getString(R.string.runicAlphabet))
        adapter.addFragment(
            fragmentFurtherRuneInformation,
            getString(R.string.furtherInformation)
        )
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
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

    override fun onBackPressed() {
        val tabView: TabLayout = this.findViewById<TabLayout>(R.id.tabs)
        if (tabView.selectedTabPosition == 1) {
            tabView.getTabAt(0)!!.select()
        } else {
            super.onBackPressed()
        }
    }

    private fun transpileTexts() {
        transpileActionBar(R.string.runicAlphabet, actionbar!!)
        transpileTextResourceOnTextView(useRunesSwitch!!, R.string.useRunes)

        adapter!!.notifyDataSetChanged()

        supportFragmentManager.beginTransaction().detach(fragmentRunicAlphabet!!)
            .attach(fragmentRunicAlphabet!!).commit()

        val adapter: FurtherRuneInformationAdapter =
            runicAlphabetFurtherInformationListView.adapter as FurtherRuneInformationAdapter
        adapter.notifyDataSetChanged()
    }
}
