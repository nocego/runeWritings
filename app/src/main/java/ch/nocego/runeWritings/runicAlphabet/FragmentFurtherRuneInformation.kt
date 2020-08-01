package ch.nocego.runeWritings.runicAlphabet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import ch.nocego.runeWritings.R
import kotlinx.android.synthetic.main.further_rune_information.*

class FragmentFurtherRuneInformation : Fragment() {

    private var listView: ListView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.further_rune_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listView = runicAlphabetListView
        listView.adapter = FurtherRuneInformationAdapter(this.context!!)
        this.listView = listView
    }

}
