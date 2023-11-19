package com.example.tara.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tara.databinding.ActivitySplashscreenBinding
import com.example.tara.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashscreenBinding
    private val splashTimeOut: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.root.postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTimeOut)
    }
}