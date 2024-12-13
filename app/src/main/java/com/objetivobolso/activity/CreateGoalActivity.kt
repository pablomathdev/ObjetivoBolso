package com.objetivobolso.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import com.objetivobolso.App
import com.objetivobolso.R
import com.objetivobolso.entity.Goal
import helpers.addCurrencyFormatter
import helpers.cleanStringAndConvertToDouble
import helpers.setupCurrencyFormatterAndDisplayError
import helpers.showHelpActivity

class CreateGoalActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private lateinit var btnCreateGoal: Button

    private lateinit var goalTitleTextView: TextView

    private lateinit var goalImgImageView: ImageView

    private lateinit var wantSaveValue: EditText

    private lateinit var alreadySavedValue: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_goal)

       toolbar = findViewById(R.id.toolbar)
       toolbar.setNavigationIcon(R.drawable.arrow_back_1)
        toolbar.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }


        btnCreateGoal = findViewById(R.id.button)
        btnCreateGoal.setText(R.string.create_goal)
        btnCreateGoal.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null)


        val data = intent.extras
        val goalTitle = data?.getString("goalTitle")
        val goalImg = data?.getInt("goalImg")

        goalTitleTextView = findViewById(R.id.goal_title)
        goalTitleTextView.text = goalTitle

        goalImgImageView = findViewById(R.id.goal_img_1)
        goalImgImageView.setImageResource(goalImg!!)


        wantSaveValue= findViewById(R.id.want_to_save_field)
        val textViewError1:TextView = findViewById(R.id.text_view_error_1)

        setupCurrencyFormatterAndDisplayError(this,wantSaveValue,textViewError1)

        alreadySavedValue = findViewById(R.id.already_have_field)
        alreadySavedValue.addCurrencyFormatter {}


        btnCreateGoal.setOnClickListener{


            val wantSaveValueToDouble = cleanStringAndConvertToDouble(wantSaveValue.text.toString())



            if(wantSaveValueToDouble >= 1){
                Thread {
                    val app = application as App
                    val dao = app.database.GoalDao()

                    dao.insert(
                        Goal(
                            goalTitle = goalTitle!!,
                            goalValue = wantSaveValueToDouble,
                            currentValue = cleanStringAndConvertToDouble(alreadySavedValue.text.toString()),
                            imgGoal = goalImg
                        )
                    )
                }.start()

                val intent = Intent(this, SavedGoalActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                startActivity(intent)
            } else{
                setTextAndDrawable(
                    textViewError1,
                    R.drawable.error_icon,
                    R.string.enter_an_amount_greater_than_or_equal_to_one
                )
            }
        }

    }

    private fun setTextAndDrawable(textView: TextView, idDrawable: Int, idString: Int) {
        textView.setText(idString)
        textView.setCompoundDrawablesWithIntrinsicBounds(
            AppCompatResources.getDrawable(
                this,
                idDrawable
            ), null, null, null
        )
        textView.compoundDrawablePadding = 5
    }

    fun clickHelpButton(view: View) {
        showHelpActivity(this)
    }
}