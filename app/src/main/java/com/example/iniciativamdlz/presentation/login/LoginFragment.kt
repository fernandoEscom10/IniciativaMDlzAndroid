package com.example.iniciativamdlz.presentation.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.iniciativamdlz.R
import com.example.iniciativamdlz.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private var lastUserName: String? = null
    private lateinit var sharedPref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        singInClient()

        lastUserName = hasPreference()
        if (lastUserName != null) {
            navigateToHome(lastUserName!!)
        }

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    try {
                        val credential = oneTapClient.getSignInCredentialFromIntent(result.data)

                        if (credential.googleIdToken != null) {
                            val email = credential.id
                            val user = credential.displayName.toString()

                            savePreference(email, user)
                            navigateToHome(user)
                        }
                    } catch (e: ApiException) {
                        Toast.makeText(
                            requireActivity(),
                            R.string.unexpected_error_label,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }


        binding.googleButton.setOnClickListener {
            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(requireActivity()) { result ->
                    try {
                        val intent =
                            IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                        resultLauncher.launch(intent)
                    } catch (e: Exception) {
                        Toast.makeText(
                            requireActivity(),
                            R.string.unexpected_error_label,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .addOnFailureListener(requireActivity()) { e ->
                    Log.e("Google", e.localizedMessage)
                    Toast.makeText(
                        requireActivity(),
                        R.string.credentials_error_label,
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    private fun singInClient() {
        oneTapClient = Identity.getSignInClient(requireActivity())
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    private fun navigateToHome(user: String) {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment(user))
    }

    private fun savePreference(email: String, username: String) {
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(getString(R.string.email_share_preference), email)
            putString(getString(R.string.username_share_preference), username)
            apply()
        }
    }

    private fun hasPreference(): String? {
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return null
        val username = getString(R.string.username_share_preference)
        return sharedPref.getString(username, null)
    }

}