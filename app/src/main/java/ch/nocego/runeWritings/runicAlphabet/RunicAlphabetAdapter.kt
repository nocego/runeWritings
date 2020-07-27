package ch.nocego.runeWritings.runicAlphabet

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class RunicAlphabetAdapter(context: Context) : BaseAdapter() {

    private val mContext: Context

    init {
        mContext = context
    }

    // responsible for rendering out each row
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView = TextView(mContext)
        textView.text = "HERE is my ROW for my LISTVIEW"
        return textView
    }

    override fun getItem(position: Int): Any {
        return "TEST STRING"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //responsible for how many rows in my list
    override fun getCount(): Int {
        return 5
    }

    //TODO: https://www.youtube.com/watch?v=P2I8PGLZEVc !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

}