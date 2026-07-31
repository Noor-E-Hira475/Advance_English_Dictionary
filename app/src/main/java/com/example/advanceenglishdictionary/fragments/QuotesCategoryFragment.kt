package com.example.advanceenglishdictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.QuotesAdapter
import com.example.advanceenglishdictionary.dao.QuotesDao
import com.example.advanceenglishdictionary.databinding.FragmentQuotesCategoryBinding
import com.example.advanceenglishdictionary.repository.QuotesRepository
import com.example.advanceenglishdictionary.ui.state.UiState
import com.example.advanceenglishdictionary.viewmodel.QuotesViewModel
import com.example.advanceenglishdictionary.viewmodel.QuotesViewModelFactory
import kotlinx.coroutines.launch

class QuotesCategoryFragment : Fragment() {

    private var _binding: FragmentQuotesCategoryBinding? = null
    private val binding get() = _binding!!

    private var category: String = ""

    private val viewModel: QuotesViewModel by lazy {
        val factory = QuotesViewModelFactory(QuotesRepository(QuotesDao(requireContext().applicationContext)))
        ViewModelProvider(requireActivity(), factory)[QuotesViewModel::class.java]
    }

    fun getCategory(): String = category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString(ARG_CATEGORY) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuotesCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.quotesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.btnRetry.setOnClickListener {
            viewModel.loadQuotesByCategory(category)
        }

        observeUiState()

        if (category.isNotEmpty()) {
            viewModel.loadQuotesByCategory(category)
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> showLoading()
                        is UiState.Success -> showSuccess(state.data)
                        is UiState.Error -> showError(state.message)
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.quotesRecyclerView.visibility = View.GONE
        binding.tvEmptyState.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
    }

    private fun showSuccess(quotes: List<com.example.advanceenglishdictionary.models.Quote>) {
        binding.progressBar.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
        if (quotes.isEmpty()) {
            binding.quotesRecyclerView.visibility = View.GONE
            binding.tvEmptyState.visibility = View.VISIBLE
        } else {
            binding.tvEmptyState.visibility = View.GONE
            binding.quotesRecyclerView.visibility = View.VISIBLE
            binding.quotesRecyclerView.adapter = QuotesAdapter(requireContext(), quotes)
        }
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.quotesRecyclerView.visibility = View.GONE
        binding.tvEmptyState.visibility = View.GONE
        binding.layoutError.visibility = View.VISIBLE
        binding.tvErrorMessage.text = message
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_CATEGORY = "category_key"

        fun newInstance(category: String): QuotesCategoryFragment {
            val fragment = QuotesCategoryFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }
}
