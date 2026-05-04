package com.example.advanceenglishdictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.IVerbAdapter
import com.example.advanceenglishdictionary.dao.IVerbsDao
import com.example.advanceenglishdictionary.databinding.FragmentIVerbBinding

class IVerbFragment : Fragment() {

    private var _binding: FragmentIVerbBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: IVerbAdapter
    private lateinit var dao: IVerbsDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentIVerbBinding.inflate(inflater, container, false)

        dao = IVerbsDao(requireContext())

        val iVerbList = dao.getAllIVerbs()

        adapter = IVerbAdapter(requireContext(), iVerbList)

        binding.iVerbsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.iVerbsRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}