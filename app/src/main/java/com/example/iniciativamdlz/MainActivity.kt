package com.example.iniciativamdlz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.iniciativamdlz.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException

class MainActivity : AppCompatActivity() {

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    private lateinit var binding: ActivityMainBinding
    private val REQ_ONE_TAP = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        singInClient()

        binding.googleButton.setOnClickListener {
            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this) { result ->
                    try {
                        Log.e("Google ---- Success", "Estamos adentro")
                        startIntentSenderForResult(
                            result.pendingIntent.intentSender, REQ_ONE_TAP,
                            null, 0, 0, 0, null
                        )
                    } catch (e: Exception) {
                        Log.e(
                            "Error in Success",
                            "Couldn't start One Tap UI: ${e.localizedMessage}"
                        )
                    }
                }
                .addOnFailureListener(this) { e ->
                    // No saved credentials found. Launch the One Tap sign-up flow, or
                    // do nothing and continue presenting the signed-out UI.
                    Log.d("Error in Fail", e.localizedMessage)
                }
        }

        binding.signOutButton.setOnClickListener {
            oneTapClient.signOut()
            binding.emailText.visibility = View.GONE
            binding.emailText.text = ""
            binding.signOutButton.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    val username = credential.id
                    val password = credential.password
                    when {
                        idToken != null -> {
                            // Got an ID token from Google. Use it to authenticate
                            // with your backend.
                            Log.d("TAG", "Got ID token.")
                            binding.emailText.visibility = View.VISIBLE
                            binding.emailText.text = username
                            binding.signOutButton.visibility = View.VISIBLE
                        }

                        password != null -> {
                            // Got a saved username and password. Use them to authenticate
                            // with your backend.
                            Log.d("TAG", "Got password.")
                        }

                        else -> {
                            // Shouldn't happen.
                            Log.d("TAG", "No ID token or password!")
                        }
                    }
                } catch (e: ApiException) {
                    // ...
                }
            }
        }
    }

    private fun singInClient() {
        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(true)
            .build()
    }

}