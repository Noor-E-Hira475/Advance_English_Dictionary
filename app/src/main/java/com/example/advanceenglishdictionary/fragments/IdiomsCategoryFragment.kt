package com.example.advanceenglishdictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.IdiomsAdapter
import com.example.advanceenglishdictionary.dao.IdiomsDao
import com.example.advanceenglishdictionary.databinding.FragmentIdiomsCategoryBinding

class IdiomsCategoryFragment : Fragment() {

    private var _binding: FragmentIdiomsCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var dao: IdiomsDao
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
        _binding = FragmentIdiomsCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dao = IdiomsDao(requireContext())
        val idiomsList = dao.getIdiomsByCategory(category)

        binding.idiomsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.idiomsRecyclerView.adapter = IdiomsAdapter(requireContext(), idiomsList)
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
