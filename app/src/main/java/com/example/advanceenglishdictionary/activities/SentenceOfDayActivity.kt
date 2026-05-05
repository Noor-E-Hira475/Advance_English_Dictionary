package com.example.advanceenglishdictionary.activities

import android.icu.util.Calendar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.advanceenglishdictionary.dao.SentenceOfDayDao
import com.example.advanceenglishdictionary.databinding.ActivitySentenceOfDayBinding

class SentenceOfDayActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySentenceOfDayBinding

    private lateinit var sentenceOfDayDao: SentenceOfDayDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySentenceOfDayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sentenceOfDayDao = SentenceOfDayDao(this)

        showTodaySentence()

    }

    private fun showTodaySentence(){
        val sentences = sentenceOfDayDao.getAllSentences()

        if(sentences.isNotEmpty()){
            val day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
            val index = day % sentences.size

            val todaySentence = sentences[index]

            binding.sentenceCard.tvSentenceContent.text = todaySentence.sentence
            binding.sentenceCard.tvExampleContent.text = todaySentence.example
            binding.sentenceCard.tvMeaningContent.text = todaySentence.meaning

        }
    }
}