package com.example.advanceenglishdictionary.activities

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.advanceenglishdictionary.dao.ConfusedWordsDao
import com.example.advanceenglishdictionary.databinding.ActivityConfusedWordsDetailBinding

class ConfusedWordsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfusedWordsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfusedWordsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val index = intent.getIntExtra("WORD_INDEX", -1)
        if (index == -1) {
            finish()
            return
        }

        val dao = ConfusedWordsDao(this)
        val wordList = dao.loadConfusedWords()
        val word = wordList.getOrNull(index) ?: run { finish(); return }

        // --- Pair title e.g. "Accept / Except" ---
        binding.tvPairTitle.text = word.pair.joinToString(" / ")

        // --- Definitions with first keyword bolded ---
        val defViews = listOf(binding.tvDefinition1, binding.tvDefinition2, binding.tvDefinition3)

        for (i in defViews.indices) {
            val tv = defViews[i]
            val pairWord = word.pair.getOrNull(i)
            val definition = word.definitions.getOrNull(i)

            if (pairWord != null && definition != null) {
                tv.visibility = View.VISIBLE

                // Bold the leading keyword in the definition text
                val boldHtml = if (definition.startsWith(pairWord, ignoreCase = true)) {
                    "<b>${definition.substring(0, pairWord.length)}</b>${definition.substring(pairWord.length)}"
                } else {
                    "<b>$pairWord</b>: $definition"
                }

                tv.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(boldHtml, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    @Suppress("DEPRECATION")
                    Html.fromHtml(boldHtml)
                }
            } else {
                tv.visibility = View.GONE
            }
        }

        // --- Example sentence ---
        binding.tvExample.text = word.example
    }
}
