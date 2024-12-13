package helpers

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.objetivobolso.R

fun setupCountTitleCharacterAndDisplayError(
    context: Context,
    goalTitle: EditText,
    textViewErrorGoalTitle: TextView,
    textViewCountCharTitle: TextView
) {
    goalTitle.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {

            if (goalTitle.text.isEmpty()) {
                setTextAndDrawable(
                    context,
                    textViewErrorGoalTitle,
                    R.drawable.error_icon,
                    R.string.enter_a_name_for_your_goal
                )
            }

            goalTitle.countTitleCharacter(textViewCountCharTitle) { currentLength ->

                if (currentLength < 1) {
                    setTextAndDrawable(
                        context,
                        textViewErrorGoalTitle,
                        R.drawable.error_icon,
                        R.string.enter_a_name_for_your_goal
                    )
                } else {
                    removeTextAndDrawable(textViewErrorGoalTitle)
                }
            }

        } else {
            removeTextAndDrawable(textViewErrorGoalTitle)
        }
    }
}

private fun setTextAndDrawable(context: Context,textView: TextView, idDrawable: Int, idString: Int) {
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