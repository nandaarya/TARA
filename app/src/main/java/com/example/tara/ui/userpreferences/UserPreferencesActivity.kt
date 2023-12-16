package com.example.tara.ui.userpreferences

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tara.R
import com.example.tara.ViewModelFactory
import com.example.tara.data.Result
import com.example.tara.databinding.ActivityUserPreferencesBinding
import com.example.tara.ui.main.MainActivity

class UserPreferencesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserPreferencesBinding
    private lateinit var userPreferencesViewModel: UserPreferencesViewModel
    private val selectedPreferences: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        userPreferencesViewModel = ViewModelProvider(this, factory)[UserPreferencesViewModel::class.java]

        userPreferencesViewModel.userPreferencesResponse.observe(this) {
            when (it) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is Result.Error -> {
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.login_failed_dialog_title))
                        setMessage(getString(R.string.setUserPreferences_failed_dialog))
                        create()
                        show()
                    }
                    showLoading(false)
                }
            }
        }

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

        binding.chipMuseum.setOnCheckedChangeListener { chip, isChecked ->
            if (isChecked) {
                selectedPreferences.add(chip.text.toString())
            } else {
                selectedPreferences.remove(chip.text.toString())
            }
        }

        binding.chipTaman.setOnCheckedChangeListener { chip, isChecked ->
            if (isChecked) {
                selectedPreferences.add(chip.text.toString())
            } else {
                selectedPreferences.remove(chip.text.toString())
            }
        }

        binding.chipGoa.setOnCheckedChangeListener { chip, isChecked ->
            if (isChecked) {
                selectedPreferences.add(chip.text.toString())
            } else {
                selectedPreferences.remove(chip.text.toString())
            }
        }

        binding.chipCandi.setOnCheckedChangeListener { chip, isChecked ->
            if (isChecked) {
                selectedPreferences.add(chip.text.toString())
            } else {
                selectedPreferences.remove(chip.text.toString())
            }
        }

        binding.chipBukit.setOnCheckedChangeListener { chip, isChecked ->
            if (isChecked) {
                selectedPreferences.add(chip.text.toString())
            } else {
                selectedPreferences.remove(chip.text.toString())
            }
        }

        binding.chipKebun.setOnCheckedChangeListener { chip, isChecked ->
            if (isChecked) {
                selectedPreferences.add(chip.text.toString())
            } else {
                selectedPreferences.remove(chip.text.toString())
            }
        }

        binding.btnSave.setOnClickListener {
            Log.d("preferences", selectedPreferences.toString())
            userPreferencesViewModel.setUserPreferences(selectedPreferences)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}