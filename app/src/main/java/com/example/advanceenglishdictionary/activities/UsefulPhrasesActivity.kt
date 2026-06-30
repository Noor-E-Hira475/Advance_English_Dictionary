package com.example.advanceenglishdictionary.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.R
import com.example.advanceenglishdictionary.adapters.UsefulPhraseCategoryAdapter
import com.example.advanceenglishdictionary.databinding.ActivityUsefulPhrasesBinding
import com.example.advanceenglishdictionary.models.UsefulPhraseCategory

class UsefulPhrasesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsefulPhrasesBinding

    // State for Source and Target languages
    private var sourceLanguageCol = "english"
    private var sourceLanguageName = "English"
    private var sourceFlagResId = R.drawable.ic_flag_english

    private var targetLanguageCol = "arabic"
    private var targetLanguageName = "Arabic"
    private var targetFlagResId = R.drawable.ic_flag_arabic

    // Launchers for result
    private val sourceLanguageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                sourceLanguageCol = data.getStringExtra("SELECTED_DB_COLUMN") ?: "english"
                sourceLanguageName = data.getStringExtra("SELECTED_DISPLAY_NAME") ?: "English"
                sourceFlagResId = data.getIntExtra("SELECTED_FLAG_RES", R.drawable.ic_flag_english)
                updateLanguageUI()
            }
        }
    }

    private val targetLanguageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                targetLanguageCol = data.getStringExtra("SELECTED_DB_COLUMN") ?: "arabic"
                targetLanguageName = data.getStringExtra("SELECTED_DISPLAY_NAME") ?: "Arabic"
                targetFlagResId = data.getIntExtra("SELECTED_FLAG_RES", R.drawable.ic_flag_arabic)
                updateLanguageUI()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsefulPhrasesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        updateLanguageUI()
        setupListeners()
    }

    private fun updateLanguageUI() {
        binding.tvSourceLanguage.text = sourceLanguageName
        binding.ivSourceFlag.setImageResource(sourceFlagResId)

        binding.tvTargetLanguage.text = targetLanguageName
        binding.ivTargetFlag.setImageResource(targetFlagResId)
    }

    private fun setupListeners() {
        binding.layoutSourceLanguage.setOnClickListener {
            val intent = Intent(this, LanguageSelectorActivity::class.java).apply {
                putExtra("CURRENT_SELECTION", sourceLanguageCol)
            }
            sourceLanguageLauncher.launch(intent)
        }

        binding.layoutTargetLanguage.setOnClickListener {
            val intent = Intent(this, LanguageSelectorActivity::class.java).apply {
                putExtra("CURRENT_SELECTION", targetLanguageCol)
            }
            targetLanguageLauncher.launch(intent)
        }
    }

    private fun setupRecyclerView() {
        val categories = listOf(
            UsefulPhraseCategory(0, getString(R.string.phrase_greetings), R.drawable.ic_phrase_greetings),
            UsefulPhraseCategory(1, getString(R.string.phrase_general_conversation), R.drawable.ic_phrase_conversation),
            UsefulPhraseCategory(2, getString(R.string.phrase_numbers), R.drawable.ic_phrase_numbers),
            UsefulPhraseCategory(3, getString(R.string.phrase_time_date), R.drawable.ic_phrase_time_date),
            UsefulPhraseCategory(4, getString(R.string.phrase_directions), R.drawable.ic_phrase_directions),
            UsefulPhraseCategory(5, getString(R.string.phrase_transportation), R.drawable.ic_phrase_transport),
            UsefulPhraseCategory(6, getString(R.string.phrase_accommodation), R.drawable.ic_phrase_accommodation),
            UsefulPhraseCategory(7, getString(R.string.phrase_eating_out), R.drawable.ic_phrase_eating),
            UsefulPhraseCategory(8, getString(R.string.phrase_shopping), R.drawable.ic_phrase_shopping),
            UsefulPhraseCategory(9, getString(R.string.phrase_colours), R.drawable.ic_phrase_colours),
            UsefulPhraseCategory(10, getString(R.string.phrase_cities), R.drawable.ic_phrase_cities),
            UsefulPhraseCategory(11, getString(R.string.phrase_countries), R.drawable.ic_phrase_countries),
            UsefulPhraseCategory(12, getString(R.string.phrase_tourist), R.drawable.ic_phrase_tourist),
            UsefulPhraseCategory(13, getString(R.string.phrase_family), R.drawable.ic_phrase_family),
            UsefulPhraseCategory(14, getString(R.string.phrase_dating), R.drawable.ic_phrase_dating),
            UsefulPhraseCategory(15, getString(R.string.phrase_emergency), R.drawable.ic_phrase_emergency),
            UsefulPhraseCategory(16, getString(R.string.phrase_feeling_sick), R.drawable.ic_phrase_sick),
            UsefulPhraseCategory(17, getString(R.string.phrase_tongue_twisters), R.drawable.ic_phrase_tongue)
        )

        val adapter = UsefulPhraseCategoryAdapter(categories) { category ->
            val intent = Intent(this, UsefulPhrasesDetailActivity::class.java).apply {
                putExtra("CATEGORY_ID", category.id)
                putExtra("CATEGORY_NAME", category.name)
                putExtra("SOURCE_LANG_COL", sourceLanguageCol)
                putExtra("TARGET_LANG_COL", targetLanguageCol)
            }
            startActivity(intent)
        }

        binding.rvUsefulPhrases.layoutManager = LinearLayoutManager(this)
        binding.rvUsefulPhrases.adapter = adapter
    }
}
