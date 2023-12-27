package com.example.iniciativamdlz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.iniciativamdlz.R
import com.example.iniciativamdlz.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FragmentMainBinding.inflate(inflater)
        val binding = FragmentMainBinding.inflate(inflater)

        binding.cifrasControlButton.setOnClickListener {
            naviageteCifrasControl()
        }
        binding.encuenstaSatisfaccionButton.setOnClickListener {
            naviageteEncuestaSatisfaccion()
        }
        binding.reporteIncidentesButton.setOnClickListener {
            naviateReporteIncidentes()
        }

        return binding.root
    }

    private fun naviateReporteIncidentes() {
        findNavController().navigate(R.id.action_mainFragment_to_reporteIncidentesFragment)
    }

    private fun naviageteEncuestaSatisfaccion() {
        findNavController().navigate(R.id.action_mainFragment_to_encuestaSatisfaccionFragment)
    }

    private fun naviageteCifrasControl() {
        findNavController().navigate(R.id.action_mainFragment_to_cifrasControlFragment)
    }
}