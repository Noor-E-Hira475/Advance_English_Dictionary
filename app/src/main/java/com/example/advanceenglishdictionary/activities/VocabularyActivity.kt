package com.example.advanceenglishdictionary.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.VocabularyAdapter
import com.example.advanceenglishdictionary.databinding.ActivityVocabularyBinding
import com.example.advanceenglishdictionary.fragments.IVerbFragment
import com.example.advanceenglishdictionary.fragments.ProverbsFragment
import com.example.advanceenglishdictionary.fragments.VocaWordFragment
import com.example.advanceenglishdictionary.models.VocabularyItem

class VocabularyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVocabularyBinding

    companion object {
        const val TYPE_VOCAB = 0
        const val TYPE_PROVERB = 1
        const val TYPE_IVERB = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVocabularyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        // Default fragment
        openFragment(VocabularyItem("IRREGULAR VERBS", TYPE_IVERB))
    }

    private fun setupRecyclerView() {

        val list = listOf(
            VocabularyItem("500 IELTS VOCABULARY", TYPE_VOCAB),
            VocabularyItem("ENGLISH PROVERBS", TYPE_PROVERB),
            VocabularyItem("IRREGULAR VERBS", TYPE_IVERB)
        )

        val adapter = VocabularyAdapter(list) { item ->
            openFragment(item)
        }

        binding.vocabularyRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.vocabularyRecyclerView.adapter = adapter
    }

    private fun openFragment(item: VocabularyItem) {

        val fragment = when (item.type) {
            TYPE_VOCAB -> VocaWordFragment()
            TYPE_PROVERB -> ProverbsFragment()
            TYPE_IVERB -> IVerbFragment()
            else -> VocaWordFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}