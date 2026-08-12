package com.example.advanceenglishdictionary.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.lifecycleScope
import com.example.advanceenglishdictionary.adapters.WordDefinitionAdapter
import com.example.advanceenglishdictionary.dao.DictionaryDao
import com.example.advanceenglishdictionary.databinding.ActivityWordDetailBinding
import com.example.advanceenglishdictionary.viewmodel.WordDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class WordDetailActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityWordDetailBinding
    private val viewModel: WordDetailViewModel by viewModels()
    private lateinit var adapter: WordDefinitionAdapter

    private var tts: TextToSpeech? = null
    private var isTtsInitialized = false

    private var idRef: Int = -1
    private var wordTitle: String = ""

    companion object {
        private const val EXTRA_ID_REF = "extra_id_ref"
        private const val EXTRA_WORD_TITLE = "extra_word_title"

        fun start(context: Context, idRef: Int, wordTitle: String) {
            val intent = Intent(context, WordDetailActivity::class.java).apply {
                putExtra(EXTRA_ID_REF, idRef)
                putExtra(EXTRA_WORD_TITLE, wordTitle)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idRef = intent.getIntExtra(EXTRA_ID_REF, -1)
        wordTitle = intent.getStringExtra(EXTRA_WORD_TITLE) ?: ""

        setupToolbar()
        setupHeader()
        setupRecyclerView()
        initTTS()
        observeViewModel()

        if (idRef != -1) {
            viewModel.loadWordDetail(idRef)
        } else {
            binding.tvEmptyDefinitions.visibility = View.VISIBLE
        }
    }

    private fun setupToolbar() {
        binding.toolbar.tvTitle.text = wordTitle.ifEmpty { "Word Detail" }
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setupHeader() {
        binding.tvWordTitle.text = wordTitle
        binding.btnSpeak.setOnClickListener {
            speakWord()
        }
    }

    private fun setupRecyclerView() {
        adapter = WordDefinitionAdapter { relatedWord ->
            lifecycleScope.launch(Dispatchers.IO) {
                val matches = DictionaryDao(this@WordDetailActivity).searchWords(relatedWord, 1)
                withContext(Dispatchers.Main) {
                    if (matches.isNotEmpty()) {
                        WordDetailActivity.start(this@WordDetailActivity, matches[0].idRef, matches[0].word)
                    } else {
                        Toast.makeText(this@WordDetailActivity, "Word '$relatedWord' not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.rvDefinitions.layoutManager = LinearLayoutManager(this)
        binding.rvDefinitions.adapter = adapter
    }

    private fun initTTS() {
        tts = TextToSpeech(this, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                isTtsInitialized = false
            } else {
                isTtsInitialized = true
            }
        } else {
            isTtsInitialized = false
        }
    }

    private fun speakWord() {
        if (wordTitle.isNotEmpty() && isTtsInitialized) {
            tts?.speak(wordTitle, TextToSpeech.QUEUE_FLUSH, null, "WordDetailTTS")
        } else {
            Toast.makeText(this, "Text-to-Speech unavailable", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.wordKey.observe(this) { key ->
            if (key != null && wordTitle.isEmpty()) {
                wordTitle = key.word
                binding.tvWordTitle.text = key.word
                binding.toolbar.tvTitle.text = key.word
            }
        }

        viewModel.descriptions.observe(this) { list ->
            adapter.submitList(list)
            if (list.isNullOrEmpty()) {
                binding.tvEmptyDefinitions.visibility = View.VISIBLE
                binding.rvDefinitions.visibility = View.GONE
            } else {
                binding.tvEmptyDefinitions.visibility = View.GONE
                binding.rvDefinitions.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroy() {
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        super.onDestroy()
    }
}
