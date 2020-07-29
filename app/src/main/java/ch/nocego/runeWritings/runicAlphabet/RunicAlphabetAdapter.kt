package ch.nocego.runeWritings.runicAlphabet

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.runes.Rune
import ch.nocego.runeWritings.runes.runeInstances.*

class RunicAlphabetAdapter(context: Context) : BaseAdapter() {

    private val mContext: Context
    private val runes = arrayListOf<Rune>(
        Ansuz(),
        Berkanan(),
        Kaun(0),
        Dagaz(),
        Ehwaz(),
        Fehu(),
        Gyfu(),
        Haglaz(),
        Isaz(),
        Jeran(0),
        Kaun(1),
        Laguz(),
        Mannaz(),
        Naudiz(),
        Odal(),
        Peord(),
        Kaun(2),
        Raido(),
        Sigel(),
        Tiwaz(),
        Ur(),
        Wynn(0),
        Wynn(1),
        Eihwaz(),
        Jeran(1),
        Algiz()
    )

    init {
        mContext = context
    }

    // responsible for rendering out each row_main
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val rowMain = layoutInflater.inflate(R.layout.row_main, parent, false)

        if (position % 2 == 0) {
            rowMain.setBackgroundColor(Color.parseColor("#4D000000"))
        }

        val rowLetter = rowMain.findViewById<TextView>(R.id.rowLetter)
        rowLetter.text = runes[position].correspondingLetter()

        val rowRune = rowMain.findViewById<TextView>(R.id.rowRune)
        rowRune.text = runes[position].unicodeSymbol()

        val rowRuneName = rowMain.findViewById<TextView>(R.id.rowRuneName)
        rowRuneName.text = runes[position].name()

        val rowDescription = rowMain.findViewById<TextView>(R.id.rowDescriptionText)
        rowDescription.text = runes[position].description()

        rowMain.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(runes[position].url())
            startActivity(mContext, intent, null)
        }
        runes[position].url()

        return rowMain
    }

    override fun getItem(position: Int): Any {
        return "TEST STRING"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //responsible for how many rows in my list
    override fun getCount(): Int {
        return runes.size
    }

}