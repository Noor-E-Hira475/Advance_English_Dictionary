package com.example.advanceenglishdictionary.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.R
import com.example.advanceenglishdictionary.adapters.LanguageAdapter
import com.example.advanceenglishdictionary.databinding.ActivityLanguageSelectorBinding
import com.example.advanceenglishdictionary.models.Language

class LanguageSelectorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLanguageSelectorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageSelectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentSelection = intent.getStringExtra("CURRENT_SELECTION") ?: "english"
        setupRecyclerView(currentSelection)
    }

    private fun setupRecyclerView(currentSelection: String) {
        val languages = listOf(
            Language("English", "english", R.drawable.ic_flag_english),
            Language("Arabic", "arabic", R.drawable.ic_flag_arabic),
            Language("German", "german", R.drawable.ic_flag_german),
            Language("Spanish", "spanish", R.drawable.ic_flag_spanish),
            Language("French", "french", R.drawable.ic_flag_french),
            Language("Hindi", "hindi", R.drawable.ic_flag_hindi),
            Language("Italian", "italian", R.drawable.ic_flag_italian),
            Language("Japanese", "japanese", R.drawable.ic_flag_japanese),
            Language("Korean", "korean", R.drawable.ic_flag_korean),
            Language("Portuguese", "portugese", R.drawable.ic_flag_portugese),
            Language("Russian", "russian", R.drawable.ic_flag_russian),
            Language("Urdu", "urdu", R.drawable.ic_flag_urdu)
        )

        val adapter = LanguageAdapter(languages, currentSelection) { selectedLanguage ->
            val resultIntent = Intent().apply {
                putExtra("SELECTED_DISPLAY_NAME", selectedLanguage.displayName)
                putExtra("SELECTED_DB_COLUMN", selectedLanguage.dbColumnName)
                putExtra("SELECTED_FLAG_RES", selectedLanguage.flagResId)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.rvLanguages.layoutManager = LinearLayoutManager(this)
        binding.rvLanguages.adapter = adapter
    }
}
