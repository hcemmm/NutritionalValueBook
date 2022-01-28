package com.example.foodbookedu.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodbookedu.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashScreenImageView.alpha = 0f
            splashScreenTextView.alpha = 0f
            splashScreenImageView.animate().setDuration(1500).alpha(1f).withEndAction{
                splashScreenTextView.animate().setDuration(1500).alpha(1f).withEndAction{
                    val action = Intent(this,MainActivity::class.java)
                    startActivity(action)
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                    finish()
                }
            }

    }
}