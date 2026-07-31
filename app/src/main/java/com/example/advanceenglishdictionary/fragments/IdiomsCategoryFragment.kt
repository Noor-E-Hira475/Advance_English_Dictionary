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
import com.example.advanceenglishdictionary.adapters.IdiomsAdapter
import com.example.advanceenglishdictionary.dao.IdiomsDao
import com.example.advanceenglishdictionary.databinding.FragmentIdiomsCategoryBinding
import com.example.advanceenglishdictionary.repository.IdiomsRepository
import com.example.advanceenglishdictionary.ui.state.UiState
import com.example.advanceenglishdictionary.viewmodel.IdiomsViewModel
import com.example.advanceenglishdictionary.viewmodel.IdiomsViewModelFactory
import kotlinx.coroutines.launch

class IdiomsCategoryFragment : Fragment() {

    private var _binding: FragmentIdiomsCategoryBinding? = null
    private val binding get() = _binding!!

    private var category: String = ""

    private val viewModel: IdiomsViewModel by lazy {
        val factory = IdiomsViewModelFactory(IdiomsRepository(IdiomsDao(requireContext().applicationContext)))
        ViewModelProvider(requireActivity(), factory)[IdiomsViewModel::class.java]
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
        _binding = FragmentIdiomsCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.idiomsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.btnRetry.setOnClickListener {
            viewModel.loadIdiomsByCategory(category)
        }

        observeUiState()

        if (category.isNotEmpty()) {
            viewModel.loadIdiomsByCategory(category)
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
        binding.idiomsRecyclerView.visibility = View.GONE
        binding.tvEmptyState.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
    }

    private fun showSuccess(idioms: List<com.example.advanceenglishdictionary.models.Idioms>) {
        binding.progressBar.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
        if (idioms.isEmpty()) {
            binding.idiomsRecyclerView.visibility = View.GONE
            binding.tvEmptyState.visibility = View.VISIBLE
        } else {
            binding.tvEmptyState.visibility = View.GONE
            binding.idiomsRecyclerView.visibility = View.VISIBLE
            binding.idiomsRecyclerView.adapter = IdiomsAdapter(requireContext(), idioms)
        }
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.idiomsRecyclerView.visibility = View.GONE
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

        fun newInstance(category: String): IdiomsCategoryFragment {
            val fragment = IdiomsCategoryFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }
}
