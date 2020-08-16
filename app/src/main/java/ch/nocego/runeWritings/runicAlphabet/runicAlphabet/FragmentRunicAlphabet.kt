package ch.nocego.runeWritings.runicAlphabet.runicAlphabet

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.model.Alphabet
import ch.nocego.runeWritings.model.Transpiler.Companion.runeUnicodeByLetter
import ch.nocego.runeWritings.model.Transpiler.Companion.transpileRuneOpposite
import ch.nocego.runeWritings.model.Transpiler.Companion.transpileTextOnTextView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_runic_alphabet.*
import kotlin.math.ceil
import kotlin.math.floor

class FragmentRunicAlphabet : Fragment() {

    private val alphabetItemWidthInDp = 70 //50+2*10
    private lateinit var alphabetItems: ArrayList<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_runic_alphabet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alphabetItems = ArrayList()
        Alphabet.alphabetUpperCase()
            .map { letter -> alphabetItems.add(generateAlphabetItem(letter)) }

        val linearLayoutVertical: View = runicAlphabetLinearScrollLayout

        linearLayoutVertical as LinearLayout
        for (i in 0 until numberOfRows()) {
            linearLayoutVertical.addView(
                generateLinearLayoutHorizontalWithChildren(
                    alphabetItems.drop(i * alphabetItemsPerRow()).take(alphabetItemsPerRow())
                )
            )
        }
    }

    private fun generateAlphabetItem(value: String): View {
        val layoutInflater = LayoutInflater.from(context)
        val alphabetItem: View = layoutInflater.inflate(R.layout.alphabet_item, null)

        val alphabetItemLetter = alphabetItem.findViewById<TextView>(R.id.alphabetItemLetter)
        transpileTextOnTextView(alphabetItemLetter, value)

        val alphabetItemRune = alphabetItem.findViewById<TextView>(R.id.alphabetItemRune)
        transpileRuneOpposite(alphabetItemRune, runeUnicodeByLetter(value))

        alphabetItem.setOnClickListener {
            activity!!.findViewById<TabLayout>(R.id.tabs).getTabAt(1)!!.select()
            val listView: ListView =
                activity!!.findViewById(R.id.runicAlphabetFurtherInformationListView)
            listView.setSelection(Alphabet.indexOfLetter(value))
        }


        return alphabetItem
    }

    private fun numberOfRows(): Int {
        val numberOfLetters: Int = alphabetItems.size
        val numberOfRows: Int = ceil(numberOfLetters.toDouble() / alphabetItemsPerRow()).toInt()
        return numberOfRows
    }

    private fun alphabetItemsPerRow(): Int {
        var alphabetItemsPerRow: Int = floor(screenWidthInDp() / alphabetItemWidthInDp).toInt()
        if (alphabetItemsPerRow > 7) alphabetItemsPerRow = 7
        return alphabetItemsPerRow
    }

    private fun screenWidthInDp(): Float {
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val screenWidthInDp: Float = displayMetrics.widthPixels / displayMetrics.density
        return screenWidthInDp
    }

    private fun generateLinearLayoutHorizontalWithChildren(alphabetItems: List<View>): LinearLayout {
        val llh = LinearLayout(context)
        llh.orientation = LinearLayout.HORIZONTAL
        llh.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llh.gravity = Gravity.CENTER_HORIZONTAL
        alphabetItems.map { alphabetItem -> llh.addView(alphabetItem) }

        return llh
    }

}
