package com.hoangpd15.smartmovie.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.hoangpd15.smartmovie.R
import com.hoangpd15.smartmovie.databinding.ActivitySignInBinding
import com.hoangpd15.smartmovie.databinding.FragmentSearchBinding
import com.hoangpd15.smartmovie.ui.genresScreen.movieByGenres.ListMovieGenresFragmentDirections

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickPhoneButton()
        firebaseAuth = FirebaseAuth.getInstance()
        clickSignUp()
        clickSignIn()
    }

    private fun clickPhoneButton() {
        binding.phone.setOnClickListener {
            val intent = Intent(this@SignInActivity, PhoneActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickSignUp() {
        binding.signUpText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
    private fun clickSignIn() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.emailText.text.toString()
            val pass = binding.passwordText.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}