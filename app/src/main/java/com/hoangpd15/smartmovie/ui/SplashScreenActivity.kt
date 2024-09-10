package com.hoangpd15.smartmovie.ui

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hoangpd15.smartmovie.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        val splashScreenDuration = 4000L // 2 giây
        Thread {
            Thread.sleep(splashScreenDuration)
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }.start()
        val imageView: ImageView = findViewById(R.id.animated_image)
        val animationDrawable = imageView.drawable as AnimationDrawable
        animationDrawable.start()

    }
}