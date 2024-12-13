package helpers

import android.text.Editable
import android.text.TextWatcher

import android.widget.EditText
import android.widget.TextView
import com.objetivobolso.R

fun EditText.countTitleCharacter(textView: TextView,getCount:(Int) -> Unit){


    this.addTextChangedListener(object :TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val length = s?.length
            getCount(length!!)

            textView.text =  String.format(resources.getString(R.string.number_of_caracter_goal_title), s.length.toString())
//            textView.text = s?.length.toString() + "/20"

        }

        override fun afterTextChanged(s: Editable?) {
        }

    })
}