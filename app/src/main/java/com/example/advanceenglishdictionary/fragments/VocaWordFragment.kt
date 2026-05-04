package com.example.advanceenglishdictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.VocaWordAdapter
import com.example.advanceenglishdictionary.dao.VocaWordDao
import com.example.advanceenglishdictionary.databinding.FragmentVocaWordBinding

class VocaWordFragment : Fragment() {
    private var _binding: FragmentVocaWordBinding? =  null
    private val binding get() = _binding!!

    private lateinit var adapter: VocaWordAdapter

    private lateinit var dao: VocaWordDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentVocaWordBinding.inflate(inflater, container, false)

        dao = VocaWordDao(requireContext())

        val voaWordList = dao.getAllVocaWords()

        adapter = VocaWordAdapter(requireContext(), voaWordList)

        binding.vocaWordsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.vocaWordsRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
