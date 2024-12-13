package helpers

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.objetivobolso.R

fun setupCurrencyFormatterAndDisplayError(context: Context, editText: EditText, textViewError: TextView) {
    editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {

            editText.addCurrencyFormatter(onValue = { value ->
                checkValueLessThanOne(context,value, textViewError)
            })
        } else {
            removeTextAndDrawable(textViewError)
        }
    }
}

private fun checkValueLessThanOne(context: Context,value: Double, textView: TextView) {

    if (value < 1.0) {

        setTextAndDrawable(
            context,
            textView,
            R.drawable.error_icon,
            R.string.enter_an_amount_greater_than_or_equal_to_one
        )

    } else {
        removeTextAndDrawable(textView)

    }
}

private fun setTextAndDrawable(context:Context, textView: TextView, idDrawable: Int, idString: Int) {
    textView.setText(idString)
    textView.setCompoundDrawablesWithIntrinsicBounds(
        AppCompatResources.getDrawable(
            context,
            idDrawable
        ), null, null, null
    )
    textView.compoundDrawablePadding = 5
}

private fun removeTextAndDrawable(textView: TextView) {
    textView.text = ""
    textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
}
