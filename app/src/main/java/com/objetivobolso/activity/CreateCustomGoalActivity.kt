package com.objetivobolso.activity
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import com.objetivobolso.App
import com.objetivobolso.R
import com.objetivobolso.entity.Goal
import helpers.addCurrencyFormatter
import helpers.cleanStringAndConvertToDouble
import helpers.setupCountTitleCharacterAndDisplayError
import helpers.setupCurrencyFormatterAndDisplayError
import helpers.showHelpActivity


class CreateCustomGoalActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private lateinit var button: Button

    private lateinit var addImage: ImageButton

    private lateinit var goalImg: ImageView

    private lateinit var alreadySaved: EditText

    private lateinit var goalTitle: EditText

    private lateinit var wantSaveEditText: EditText

    private var imgGoalId: Int = R.drawable.pig_bank


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_custom_goal)

        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.arrow_back_1)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        wantSaveEditText = findViewById(R.id.want_to_save_field)

        var wantSaveValue = 0.0

        wantSaveEditText.addCurrencyFormatter { value ->

            wantSaveValue = value
        }
        val textViewError1: TextView = findViewById(R.id.text_view_error_1)
        setupCurrencyFormatterAndDisplayError(this,wantSaveEditText, textViewError1)

        alreadySaved = findViewById(R.id.already_have_field)

        alreadySaved.addCurrencyFormatter {}



        button = findViewById(R.id.button)
        button.setText(R.string.create_goal)
        button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)



        goalTitle = findViewById(R.id.goal_title)
        val textViewCountTitleCharacter: TextView = findViewById(R.id.text_view_count_character)

        val textViewErrorGoalTitle: TextView = findViewById(R.id.text_view_error_goal_title)

        setupCountTitleCharacterAndDisplayError(
            this,
            goalTitle,
            textViewErrorGoalTitle,
            textViewCountTitleCharacter
        )


        goalImg = findViewById(R.id.goal_img)
        goalImg.setImageResource(imgGoalId)
        goalImg.scaleType = ImageView.ScaleType.CENTER_CROP

        val imageSelectLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == Activity.RESULT_OK) {
                    val selectedImage = result.data?.getIntExtra("image", 1)

                    goalImg.setImageResource(selectedImage!!)
                    imgGoalId = selectedImage
                    goalImg.scaleType = ImageView.ScaleType.CENTER_CROP
                }

            }


        addImage = findViewById(R.id.btn_add_image)


        addImage.setOnClickListener {
            val i = Intent(this, ChooseImageActivity::class.java)

            imageSelectLauncher.launch(i)


        }

        button.setOnClickListener {
            val goalTitleText = goalTitle.text

            if (goalTitleText.isEmpty() && wantSaveValue < 0.9 ) { // xxxx
                setTextAndDrawable(
                    textViewErrorGoalTitle,
                    R.drawable.error_icon,
                    R.string.enter_a_name_for_your_goal
                )

                setTextAndDrawable(
                    textViewError1,
                    R.drawable.error_icon,
                    R.string.enter_an_amount_greater_than_or_equal_to_one
                )

            }

            if (goalTitleText.isNotEmpty() && wantSaveValue < 1) {
                setTextAndDrawable(
                    textViewError1,
                    R.drawable.error_icon,
                    R.string.enter_an_amount_greater_than_or_equal_to_one
                )
            }

            if (goalTitleText.isEmpty() && wantSaveValue > 1) {
                setTextAndDrawable(
                    textViewErrorGoalTitle,
                    R.drawable.error_icon,
                    R.string.enter_a_name_for_your_goal
                )
            }

            if (goalTitleText.isNotEmpty() && wantSaveValue >= 1) {


                Thread {
                    val app = application as App
                    val dao = app.database.GoalDao()

                    dao.insert(
                        Goal(
                            goalTitle = goalTitle.text.toString(),
                            goalValue = wantSaveValue,
                            currentValue = cleanStringAndConvertToDouble(alreadySaved.text.toString()),
                            imgGoal = imgGoalId
                        )
                    )
                }.start()

                val intent = Intent(this, SavedGoalActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                startActivity(intent)
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
