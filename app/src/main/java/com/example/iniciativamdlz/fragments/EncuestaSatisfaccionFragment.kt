package com.example.iniciativamdlz.fragments

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.iniciativamdlz.databinding.FragmentEncuestaSatisfaccionBinding

class EncuestaSatisfaccionFragment : Fragment() {
    val facilidad = listOf("Muy Díficil", "Díficil", "Regular", "Fácil", "Muy fácil")
    val velocidad = listOf("Muy Lenta", "Lenta", "Regular", "Rápida", "Muy Rápida")
    val experiencia = listOf("Muy Mala", "Mala", "Regular", "Buena", "Muy Buena")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentEncuestaSatisfaccionBinding.inflate(inflater)
        val adapter = ArrayAdapter(
            binding.root.context,
            R.layout.simple_list_item_1,
            facilidad
        )
        binding.facilidadSpinner.adapter = adapter
        val adapterV = ArrayAdapter(
            binding.root.context,
            R.layout.simple_list_item_1,
            velocidad
        )
        binding.velocidadSpinner.adapter = adapterV
        val adapterE = ArrayAdapter(
            binding.root.context,
            R.layout.simple_list_item_1,
            experiencia
        )
        binding.experienciaSpinner.adapter = adapterE
        return binding.root
    }
}