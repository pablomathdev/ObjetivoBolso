package com.objetivobolso.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.objetivobolso.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.statusBarColor = getColor(R.color.Very_light_gray)

       Handler(Looper.getMainLooper()).postDelayed({
           val intent = Intent(this,MainActivity::class.java)
           startActivity(intent)
           finish()
       },2000)

    }
}