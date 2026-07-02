package com.example.advanceenglishdictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.QuotesAdapter
import com.example.advanceenglishdictionary.dao.QuotesDao
import com.example.advanceenglishdictionary.databinding.FragmentQuotesCategoryBinding

class QuotesCategoryFragment : Fragment() {

    private var _binding: FragmentQuotesCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var dao: QuotesDao
    private var category: String = ""

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

        dao = QuotesDao(requireContext())
        val quotesList = dao.getQuotesByCategory(category)

        binding.quotesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.quotesRecyclerView.adapter = QuotesAdapter(requireContext(), quotesList)
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
