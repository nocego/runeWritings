package ch.nocego.runeWritings.runicAlphabet.runicAlphabet

import android.os.Bundle
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

class FragmentRunicAlphabet : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_runic_alphabet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val alphabetItems: ArrayList<View> = ArrayList()
        Alphabet.alphabetUpperCase()
            .map { letter -> alphabetItems.add(generateAlphabetItem(letter)) }

        val linearLayoutVertical: View = runicAlphabetLinearScrollLayout

        (linearLayoutVertical as LinearLayout).addView(
            generateLinearLayoutHorizontalWithChildren(
                alphabetItems.take(7)
            )
        )
        linearLayoutVertical.addView(
            generateLinearLayoutHorizontalWithChildren(
                alphabetItems.drop(7).take(6)
            )
        )
        linearLayoutVertical.addView(
            generateLinearLayoutHorizontalWithChildren(
                alphabetItems.drop(13).take(6)
            )
        )
        linearLayoutVertical.addView(
            generateLinearLayoutHorizontalWithChildren(
                alphabetItems.drop(19)
            )
        )
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
