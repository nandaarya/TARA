package com.example.tara.ui.userpreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.tara.R
import com.example.tara.ViewModelFactory
import com.example.tara.databinding.ActivityUserPreferencesBinding
import com.example.tara.ui.signup.SignupViewModel

class UserPreferencesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserPreferencesBinding
    lateinit var userPreferencesViewModel: UserPreferencesViewModel
    private val selectedPreferences: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        userPreferencesViewModel = ViewModelProvider(this, factory)[UserPreferencesViewModel::class.java]

        binding.chipPantai.setOnCheckedChangeListener { chip, isChecked ->
            if (isChecked) {
                selectedPreferences.add(chip.text.toString())
            } else {
                selectedPreferences.remove(chip.text.toString())
            }
        }

        binding.chipGunung.setOnCheckedChangeListener { chip, isChecked ->
            if (isChecked) {
                selectedPreferences.add(chip.text.toString())
            } else {
                selectedPreferences.remove(chip.text.toString())
            }
        }


    }
}