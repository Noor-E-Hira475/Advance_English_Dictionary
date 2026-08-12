package com.example.advanceenglishdictionary.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.WordAdapter
import com.example.advanceenglishdictionary.databinding.ActivityThesaurusBinding
import com.example.advanceenglishdictionary.models.WordKey
import com.example.advanceenglishdictionary.ui.AlphabetIndexView
import com.example.advanceenglishdictionary.viewmodel.ThesaurusViewModel

class ThesaurusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThesaurusBinding
    private val viewModel: ThesaurusViewModel by viewModels()
    private lateinit var adapter: WordAdapter

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ThesaurusActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThesaurusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupSearchBar()
        setupAlphabetIndex()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.tvTitle.text = "Thesaurus"
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = WordAdapter { wordKey ->
            openWordDetail(wordKey)
        }
        binding.rvWords.layoutManager = LinearLayoutManager(this)
        binding.rvWords.adapter = adapter
    }

    private fun setupSearchBar() {
        val etSearch = binding.searchBar.etSearch
        val ivClear = binding.searchBar.ivClearSearch

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString() ?: ""
                ivClear.visibility = if (query.isNotEmpty()) View.VISIBLE else View.GONE
                viewModel.setSearchQuery(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        ivClear.setOnClickListener {
            etSearch.setText("")
        }
    }

    private fun setupAlphabetIndex() {
        binding.azIndexView.onLetterChangeListener = object : AlphabetIndexView.OnLetterChangeListener {
            override fun onLetterChanged(letter: String) {
                binding.searchBar.etSearch.setText("")
                viewModel.selectLetter(letter)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.wordsList.observe(this) { list ->
            adapter.submitList(list)
            if (list.isNullOrEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvWords.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.rvWords.visibility = View.VISIBLE
                binding.rvWords.scrollToPosition(0)
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.selectedLetter.observe(this) { letter ->
            binding.azIndexView.setSelectedLetter(letter)
        }
    }

    private fun openWordDetail(wordKey: WordKey) {
        WordDetailActivity.start(this, wordKey.idRef, wordKey.word)
    }
}
