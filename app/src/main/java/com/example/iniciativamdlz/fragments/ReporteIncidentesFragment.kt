package com.example.iniciativamdlz.fragments

import android.R
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.iniciativamdlz.CevesRepository
import com.example.iniciativamdlz.databinding.FragmentReporteIncidentesBinding
import java.util.Calendar

class ReporteIncidentesFragment : Fragment() {

    private val fragmentContext by lazy { context }
    private lateinit var  binding: FragmentReporteIncidentesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReporteIncidentesBinding.inflate(inflater)
        binding.horaTimeButton.setOnClickListener {
            showTimePicker()
        }
        val adapter = ArrayAdapter<String>(
            binding.root.context,
            R.layout.simple_list_item_1,
            CevesRepository.ceves
        )
        binding.seleccionaCeveSpinner.adapter = adapter
        return binding.root
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(fragmentContext, { view, hourOfDay, minute ->
                binding.horaSelectedText.text = "$hourOfDay:$minute"
            },
            hour,
            minute,
            false
        )
        timePickerDialog.show()
    }
}