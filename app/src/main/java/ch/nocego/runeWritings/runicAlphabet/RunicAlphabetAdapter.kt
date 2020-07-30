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
import ch.nocego.runeWritings.runes.LetterToRunes
import ch.nocego.runeWritings.runes.ObjectTranspiler.Companion.transpileRuneOpposite
import ch.nocego.runeWritings.runes.ObjectTranspiler.Companion.transpileText
import ch.nocego.runeWritings.runes.Rune

class RunicAlphabetAdapter(context: Context) : BaseAdapter() {

    private val mContext: Context = context
    private val ltr: LetterToRunes = LetterToRunes()

    private val runes = arrayListOf<Rune>(
        ltr.getRuneInstanceOfOneChar('A')!!,
        ltr.getRuneInstanceOfOneChar('B')!!,
        ltr.getRuneInstanceOfOneChar('C')!!,
        ltr.getRuneInstanceOfOneChar('D')!!,
        ltr.getRuneInstanceOfOneChar('E')!!,
        ltr.getRuneInstanceOfOneChar('F')!!,
        ltr.getRuneInstanceOfOneChar('G')!!,
        ltr.getRuneInstanceOfOneChar('H')!!,
        ltr.getRuneInstanceOfOneChar('I')!!,
        ltr.getRuneInstanceOfOneChar('J')!!,
        ltr.getRuneInstanceOfOneChar('K')!!,
        ltr.getRuneInstanceOfOneChar('L')!!,
        ltr.getRuneInstanceOfOneChar('M')!!,
        ltr.getRuneInstanceOfOneChar('N')!!,
        ltr.getRuneInstanceOfOneChar('O')!!,
        ltr.getRuneInstanceOfOneChar('P')!!,
        ltr.getRuneInstanceOfOneChar('Q')!!,
        ltr.getRuneInstanceOfOneChar('R')!!,
        ltr.getRuneInstanceOfOneChar('S')!!,
        ltr.getRuneInstanceOfOneChar('T')!!,
        ltr.getRuneInstanceOfOneChar('U')!!,
        ltr.getRuneInstanceOfOneChar('V')!!,
        ltr.getRuneInstanceOfOneChar('W')!!,
        ltr.getRuneInstanceOfOneChar('X')!!,
        ltr.getRuneInstanceOfOneChar('Y')!!,
        ltr.getRuneInstanceOfOneChar('Z')!!
    )

    // responsible for rendering out each row_main
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val rowMain = layoutInflater.inflate(R.layout.row_main, parent, false)

        if (position % 2 == 0) {
            rowMain.setBackgroundColor(Color.parseColor("#4D000000"))
        }

        val rowLetter = rowMain.findViewById<TextView>(R.id.rowLetter)
        transpileText(rowLetter, runes[position].correspondingLetter())

        val rowRune = rowMain.findViewById<TextView>(R.id.rowRune)
        transpileRuneOpposite(rowRune, runes[position].unicodeSymbol())

        val rowRuneName = rowMain.findViewById<TextView>(R.id.rowRuneName)
        transpileText(rowRuneName, runes[position].name())

        val rowDescription = rowMain.findViewById<TextView>(R.id.rowDescriptionText)
        transpileText(rowDescription, runes[position].description())

        rowMain.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(runes[position].url())
            startActivity(mContext, intent, null)
        }
        runes[position].url()

        return rowMain
    }

    override fun getItem(position: Int): Rune {
        return runes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //responsible for how many rows in my list
    override fun getCount(): Int {
        return runes.size
    }

}