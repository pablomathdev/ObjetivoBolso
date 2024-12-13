package com.objetivobolso.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import com.objetivobolso.R

class HelpActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        toolbar = findViewById(R.id.toolbar)
        toolbar.background = AppCompatResources.getDrawable(this,android.R.color.transparent)
        toolbar.setNavigationIcon(R.drawable.arrow_back_2)
        toolbar.setBackgroundColor(getColor(android.R.color.transparent))
        toolbar.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
        val helpButton: ImageButton = findViewById(R.id.help_button)
        helpButton.visibility = View.GONE
    }


}