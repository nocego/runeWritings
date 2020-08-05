package ch.nocego.runeWritings.runicAlphabet.furtherRuneInformation

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
import ch.nocego.runeWritings.model.Alphabet
import ch.nocego.runeWritings.model.LetterToRunes
import ch.nocego.runeWritings.model.Rune
import ch.nocego.runeWritings.model.Transpiler.Companion.transpileRuneOpposite
import ch.nocego.runeWritings.model.Transpiler.Companion.transpileTextOnTextView

class FurtherRuneInformationAdapter(context: Context) : BaseAdapter() {

    private val mContext: Context = context
    private val ltr: LetterToRunes =
        LetterToRunes()

    // responsible for rendering out each row_main
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val rowMain = layoutInflater.inflate(R.layout.row_main, parent, false)

        if (position % 2 == 0) {
            rowMain.setBackgroundColor(Color.parseColor("#4D000000"))
        }

        val rowLetter = rowMain.findViewById<TextView>(R.id.rowLetter)
        transpileTextOnTextView(
            rowLetter,
            Alphabet.runeObjectAtIndex(position).correspondingLetter()
        )

        val rowRune = rowMain.findViewById<TextView>(R.id.rowRune)
        transpileRuneOpposite(rowRune, Alphabet.runeObjectAtIndex(position).unicodeSymbol())

        val rowRuneName = rowMain.findViewById<TextView>(R.id.rowRuneName)
        transpileTextOnTextView(rowRuneName, Alphabet.runeObjectAtIndex(position).name())

        val rowDescription = rowMain.findViewById<TextView>(R.id.rowDescriptionText)
        transpileTextOnTextView(rowDescription, Alphabet.runeObjectAtIndex(position).description())

        rowMain.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(Alphabet.runeObjectAtIndex(position).url())
            startActivity(mContext, intent, null)
        }
        Alphabet.runeObjectAtIndex(position).url()

        return rowMain
    }

    override fun getItem(position: Int): Rune {
        return Alphabet.runeObjectAtIndex(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //responsible for how many rows in my list
    override fun getCount(): Int {
        return Alphabet.runesAlphabeticallyOrdered().size
    }

}