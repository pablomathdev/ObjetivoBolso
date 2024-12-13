package helpers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.Locale



fun EditText.addCurrencyFormatter(
    onValue: (Double) -> Unit
){



    this.addTextChangedListener(object : TextWatcher {
        var current = ""

        override fun afterTextChanged(s: Editable?) {


            if(s.toString() != current){
                this@addCurrencyFormatter.removeTextChangedListener(this)

                val cleanString = s.toString().replace("""[R$,.]""".toRegex(),"")
                var parsed = cleanString.trim().toDouble() / 100


                onValue(parsed)

                val formatted = NumberFormat.getCurrencyInstance(Locale("pt","BR")).format(parsed)

                current = formatted
                this@addCurrencyFormatter.setText((formatted))
                this@addCurrencyFormatter.setSelection(formatted.length)

                this@addCurrencyFormatter.addTextChangedListener(this)
            }


        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}



    })
}