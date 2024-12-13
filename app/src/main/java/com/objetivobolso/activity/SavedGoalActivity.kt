package com.objetivobolso.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.objetivobolso.R

class SavedGoalActivity : AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_goal)


        button = findViewById(R.id.button)
        button.setText(R.string.back_to_main)
        button.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null)

        button.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            startActivity(intent)
        }

    }
}