package com.example.iniciativamdlz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.iniciativamdlz.CevesRepository
import com.example.iniciativamdlz.databinding.FragmentCifrasControlBinding

class CifrasControlFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCifrasControlBinding.inflate(layoutInflater)
        val adapter = ArrayAdapter<String>(
            binding.root.context,
            android.R.layout.simple_list_item_1,
            CevesRepository.ceves
        )
        binding.seleccionaCeveSpinner.adapter = adapter
        return binding.root
    }
}