package com.example.advanceenglishdictionary.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.OnlineDictionaryAdapter
import com.example.advanceenglishdictionary.databinding.ActivityOnlineDictionaryBinding
import com.example.advanceenglishdictionary.repository.DictionaryApiRepository
import com.example.advanceenglishdictionary.ui.state.UiState
import com.example.advanceenglishdictionary.viewmodel.DictionaryApiViewModel
import com.example.advanceenglishdictionary.viewmodel.DictionaryApiViewModelFactory

class OnlineDictionaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnlineDictionaryBinding
    private val viewModel: DictionaryApiViewModel by viewModels {
        DictionaryApiViewModelFactory(DictionaryApiRepository())
    }
    private lateinit var adapter: OnlineDictionaryAdapter

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, OnlineDictionaryActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineDictionaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupSearchBar()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.tvTitle.text = "Online Dictionary"
        binding.toolbar.ivBack.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        adapter = OnlineDictionaryAdapter()
        binding.rvResults.layoutManager = LinearLayoutManager(this)
        binding.rvResults.adapter = adapter
    }

    private fun setupSearchBar() {
        val etSearch = binding.searchBar.etSearch
        val ivClear = binding.searchBar.ivClearSearch

        // Hint text specific to this screen
        etSearch.hint = "Search a word online..."

        // Show / hide clear button as user types
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString() ?: ""
                ivClear.visibility = if (query.isNotEmpty()) View.VISIBLE else View.GONE
                // Reset to idle when the field is cleared
                if (query.isEmpty()) viewModel.clearState()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Trigger search on keyboard "Search" action
        etSearch.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                val query = etSearch.text?.toString()?.trim() ?: ""
                if (query.isNotEmpty()) {
                    viewModel.searchWord(query)
                    hideKeyboard()
                }
                true
            } else {
                false
            }
        }

        // Clear button resets field and state
        ivClear.setOnClickListener {
            etSearch.setText("")
            viewModel.clearState()
        }
    }

    private fun observeViewModel() {
        viewModel.wordState.observe(this) { state ->
            when (state) {
                is UiState.Idle -> showIdle()
                is UiState.Loading -> showLoading()
                is UiState.Success -> showResults(state)
                is UiState.Error -> showError(state.message)
            }
        }
    }

    // ── State helpers ──────────────────────────────────────────────────────────

    private fun showIdle() {
        binding.progressBar.visibility = View.GONE
        binding.rvResults.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        binding.layoutIdle.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.layoutIdle.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        binding.rvResults.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showResults(state: UiState.Success<*>) {
        binding.progressBar.visibility = View.GONE
        binding.layoutIdle.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        @Suppress("UNCHECKED_CAST")
        adapter.submitList(state.data as? List<com.example.advanceenglishdictionary.models.api.DictionaryResponse>)
        binding.rvResults.visibility = View.VISIBLE
        binding.rvResults.scrollToPosition(0)
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.layoutIdle.visibility = View.GONE
        binding.rvResults.visibility = View.GONE
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}
