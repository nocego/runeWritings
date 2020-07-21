package ch.nocego.runeWritings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun switchRunesToggle(v: View) {
        changeTexts()
    }

    private fun changeTexts() {
        changeTitle()
    }

    private fun changeTitle() {
        if(switchRunes.isChecked){
            mainActivityTitle.text = getString(R.string.titleRunic)
        }else{
            mainActivityTitle.text = getString(R.string.title)
        }
    }
}
