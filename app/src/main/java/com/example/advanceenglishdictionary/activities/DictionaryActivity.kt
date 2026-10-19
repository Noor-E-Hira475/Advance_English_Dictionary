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
import com.example.advanceenglishdictionary.databinding.ActivityDictionaryBinding
import com.example.advanceenglishdictionary.models.WordKey
import com.example.advanceenglishdictionary.viewmodel.ThesaurusViewModel

class DictionaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDictionaryBinding
    private val viewModel: ThesaurusViewModel by viewModels()
    private lateinit var adapter: WordAdapter

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, DictionaryActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDictionaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupSearchBar()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.tvTitle.text = "Offline Dictionary"
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
    }

    private fun openWordDetail(wordKey: WordKey) {
        WordDetailActivity.start(this, wordKey.idRef, wordKey.word)
    }
}
