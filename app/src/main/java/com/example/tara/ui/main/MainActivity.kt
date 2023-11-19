package com.example.tara.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tara.ViewModelFactory
import com.example.tara.databinding.ActivityMainBinding
import com.example.tara.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.btnLogout.setOnClickListener {
            mainViewModel.logout()
        }
    }
}