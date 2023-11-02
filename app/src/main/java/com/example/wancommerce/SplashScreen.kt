package com.example.wancommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    private val ANIMATION_LONG: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(this.mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, ANIMATION_LONG)
    }
}