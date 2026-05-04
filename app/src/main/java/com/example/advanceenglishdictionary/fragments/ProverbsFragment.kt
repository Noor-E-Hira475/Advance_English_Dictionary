package com.example.advanceenglishdictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.ProverbsAdapter
import com.example.advanceenglishdictionary.dao.ProverbsDao
import com.example.advanceenglishdictionary.databinding.FragmentProverbsBinding

class ProverbsFragment : Fragment() {

    private var _binding: FragmentProverbsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProverbsAdapter
    private lateinit var dao: ProverbsDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProverbsBinding.inflate(inflater, container, false)

        dao = ProverbsDao(requireContext())

        val proverbsList = dao.getAllProverbs()

        adapter = ProverbsAdapter(requireContext(), proverbsList)

        binding.proverbsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.proverbsRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}