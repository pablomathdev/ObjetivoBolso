package com.objetivobolso.activity


import android.annotation.SuppressLint
import android.content.Intent

import android.os.Bundle
import android.view.View


import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView

import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar

import com.objetivobolso.App
import com.objetivobolso.R
import helpers.formatDoubleToBRL

import helpers.setupCountTitleCharacterAndDisplayError
import helpers.setupCurrencyFormatterAndDisplayError
import helpers.showHelpActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class EditGoalActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private lateinit var button: Button

    private lateinit var goalImgImageView: ImageView

    private lateinit var goalTitleTextView: TextView

    private lateinit var goalValueTextView: TextView

    private lateinit var currentValueTextView: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_goal)

        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.arrow_back_1)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        button = findViewById(R.id.button)
        button.setText(R.string.remove_goal)
        button.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this,R.drawable.remove_icon), null, null, null)


        val data = intent.extras
        val goalId = data?.getInt("goalId")
        var goalTitle = data?.getString("goalTitle")
        val goalImg = data?.getInt("goalImg")
        var goalValue = data?.getDouble("goalValue")
        val currentValue = data?.getDouble("currentValue")

        goalImgImageView = findViewById(R.id.goal_img)
        goalImgImageView.setImageResource(goalImg!!)

        goalTitleTextView = findViewById(R.id.goal_title)
        goalTitleTextView.text = goalTitle

        goalValueTextView = findViewById(R.id.goal_value)


        goalValueTextView.text = String.format(getString(R.string.of), formatDoubleToBRL(goalValue!!))



        currentValueTextView = findViewById(R.id.goal_current_value)
        currentValueTextView.text =
            NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(currentValue)


        val percentage = calculatePercentage(currentValue!!, goalValue).toInt()
        val goalProgress: ProgressBar = findViewById(R.id.goal_progress)

        val goalProgressPercentage: TextView = findViewById(R.id.goal_progress_percentage)


        CoroutineScope(Dispatchers.Main).launch {

            if (percentage != 0) {

                for (i in 0 until percentage) {

                    if (i == 100) break

                    goalProgress.progress = i + 1

                    @SuppressLint("SetTextI18n")
                    goalProgressPercentage.text = "${i + 1}%"
                    delay(20)


                }


            } else {
                goalProgressPercentage.text = "0%"
            }


        }



        button.setOnClickListener {

            createAlertDialogRemoveGoalConfirmation(goalId!!)

        }

        val editGoalImageButton: ImageButton = findViewById(R.id.edit_goal)
        editGoalImageButton.setOnClickListener {

            val alertDialog = AlertDialog.Builder(this).create()
            val dialogLayout = layoutInflater.inflate(R.layout.alert_dialog_with_edit_text, null)
            alertDialog.setTitle(R.string.edit)
            alertDialog.setView(dialogLayout)
            val goalTitleEditText: EditText = dialogLayout.findViewById(R.id.goal_title)
            goalTitleEditText.setText(goalTitle)

            val textViewCountTitleCharacter: TextView = dialogLayout.findViewById(R.id.text_view_count_character)
            textViewCountTitleCharacter.text = String.format(getString(R.string.number_of_caracter_goal_title), goalTitleEditText.text.length.toString() )


            val textViewErrorGoalTitle: TextView =
                dialogLayout.findViewById(R.id.text_view_error_goal_title)


            setupCountTitleCharacterAndDisplayError(
                this,
                goalTitleEditText,
                textViewErrorGoalTitle,
                textViewCountTitleCharacter
            )


            val goalValueEditText: EditText = dialogLayout.findViewById(R.id.goal_value)
            goalValueEditText.setText(formatDoubleToBRL(goalValue!!))
            val textViewErrorGoalValue: TextView =
                dialogLayout.findViewById(R.id.text_view_error_goal_value)
            setupCurrencyFormatterAndDisplayError(this, goalValueEditText, textViewErrorGoalValue)


            alertDialog.setCancelable(false)
            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE,
                getString(R.string.save)
            ) { _, _ ->


                val goalValueEditTextFormatted =
                    cleanStringAndConvertToDouble(goalValueEditText.text.toString())


                if (goalTitle != goalTitleEditText.text.toString() || goalValue != goalValueEditTextFormatted) {
                    Thread {
                        val app = application as App
                        val dao = app.database.GoalDao()
                        dao.updateTitleAndValue(
                            goalId!!,
                            goalValueEditTextFormatted,
                            goalTitleEditText.text.toString()
                        )

                        runOnUiThread{
                            goalTitle = goalTitleEditText.text.toString()
                            goalTitleTextView.text = goalTitle

                            goalValue = goalValueEditTextFormatted
                              goalValueTextView.text = String.format(getString(R.string.of), formatDoubleToBRL(goalValue!!))
                        }

                    }.start()

                }

            }
            alertDialog.setButton(
                AlertDialog.BUTTON_NEGATIVE,
                getString(R.string.cancel)
            ) { _, _ ->

            }
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(getColor(R.color.pure_orange))
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(getColor(R.color.pure_orange))



        }




    }

    private fun calculatePercentage(value1: Double, value2: Double): String {

        return (value1 / value2 * 100).toInt().toString()

    }

    private fun createAlertDialogRemoveGoalConfirmation(goalId: Int) {
        val alertDialog = AlertDialog.Builder(this).create()

        alertDialog.setTitle(R.string.remove_goal)
        alertDialog.setMessage(getString(R.string.this_goal_will_be_permanently_removed_from_your_device))
        alertDialog.setCancelable(false)

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.remove)) { _, _ ->

            Thread {
                val app = application as App
                val dao = app.database.GoalDao()
                dao.delete(goalId)
            }.start()

              finish()
            val intent = Intent(this, MainActivity::class.java)


            startActivity(intent)
        }

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel)) { _, _ -> }

        alertDialog.show()

        val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        val btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)

        val layoutParams = btnPositive.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.leftMargin = 20
        btnPositive.layoutParams = layoutParams


        btnPositive.isAllCaps = false
        btnPositive.setTextColor(getColor(R.color.pure_orange))
        btnNegative.isAllCaps = false
        btnNegative.setTextColor(getColor(R.color.pure_orange))
    }

    private fun cleanStringAndConvertToDouble(string: String): Double {
        val cleanString = string.replace("""[R$,.]""".toRegex(), "").trim()
        return cleanString.toDouble() / 100
    }

    fun clickHelpButton(view: View) {
        showHelpActivity(this)
    }
}
