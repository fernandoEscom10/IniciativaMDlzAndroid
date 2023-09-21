package com.example.iniciativamdlz.presentation.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.iniciativamdlz.R
import com.example.iniciativamdlz.databinding.FragmentHomeBinding
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //private lateinit var oneTapClient: SignInClient
    private lateinit var username: String
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //oneTapClient = Identity.getSignInClient(requireActivity())

        arguments?.let {
            username = it.getString("username").toString()
        }

        binding.usernameText.text = username

        binding.incidentButton.setOnClickListener { navigateToIncidentForm() }
        binding.controlButton.setOnClickListener { navigateToControlForm() }
        binding.experienceButton.setOnClickListener { navigateToExpirienceForm() }
        binding.signOutButton.setOnClickListener { signOut() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun signOut() {
        clearPreference()
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
        //oneTapClient.signOut()
    }

    private fun navigateToIncidentForm() {
        Toast.makeText(activity, "Incidente", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToControlForm() {
        Toast.makeText(activity, "Control", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToExpirienceForm() {
        Toast.makeText(activity, "Satisfaccion", Toast.LENGTH_SHORT).show()
    }

    private fun clearPreference() {
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            clear()
            apply()
        }
    }

}