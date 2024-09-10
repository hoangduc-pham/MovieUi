package com.hoangpd15.smartmovie.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.hoangpd15.smartmovie.R
import com.hoangpd15.smartmovie.databinding.ActivityOtpBinding
import com.hoangpd15.smartmovie.databinding.ActivityPhoneBinding
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var verifyBtn: Button
    private lateinit var resendTv: TextView
    private lateinit var otp1: EditText
    private lateinit var otp2: EditText
    private lateinit var otp3: EditText
    private lateinit var otp4: EditText
    private lateinit var otp5: EditText
    private lateinit var otp6: EditText
    private lateinit var OTP: String
    private lateinit var phoneNumber: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        OTP = intent.getStringExtra("OTP").toString()
//        resendToken = intent.getParcelableExtra("resendToken")!!
        phoneNumber = intent.getStringExtra("phoneNumber").toString()
        init()
        addTextChangeListener()
        binding.verify.setOnClickListener {
            val typeOtp =
                (otp1.text.toString() + otp2.text.toString() + otp3.text.toString() +
                        otp4.text.toString() + otp5.text.toString() + otp6.text.toString())
            if (typeOtp.isNotEmpty()) {
                if (typeOtp.length == 6) {
                    val authCredential = PhoneAuthProvider.getCredential(
                        OTP,typeOtp
                    )
                    signInWithPhoneAuthCredential(authCredential)
                } else {
                    Toast.makeText(this, "Please enter correct OTP", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
        binding.resend.setOnClickListener {
            resendVerificationCode()
        }
    }
    private fun resendVerificationCode(){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this,"Authenticate Successfully", Toast.LENGTH_SHORT).show()
                    setToMain()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d("TAG", "signInWithPhoneAuthCredential: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    private fun setToMain() {
        startActivity(Intent(this, PhoneActivity::class.java))
    }
    private fun addTextChangeListener() {
        otp1.addTextChangedListener(EditTextWatcher(otp1))
        otp2.addTextChangedListener(EditTextWatcher(otp2))
        otp3.addTextChangedListener(EditTextWatcher(otp3))
        otp4.addTextChangedListener(EditTextWatcher(otp4))
        otp5.addTextChangedListener(EditTextWatcher(otp5))
        otp6.addTextChangedListener(EditTextWatcher(otp6))
    }
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.

            // Save verification ID and resending token so we can use them later
            OTP = verificationId
            resendToken = token
        }
    }
    private fun init() {
        auth = FirebaseAuth.getInstance()
        verifyBtn = binding.verify
        resendTv = binding.resend
        otp1 = binding.otp1
        otp2 = binding.otp2
        otp3 = binding.otp3
        otp4 = binding.otp4
        otp5 = binding.otp5
        otp6 = binding.otp6
    }

    inner class EditTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            val text = s.toString()
            when (view.rootView) {
                binding.otp1 -> if (text.length == 1) otp2.requestFocus()
                binding.otp2 -> if (text.length == 1) otp3.requestFocus() else if (text.isEmpty()) otp1.requestFocus()
                binding.otp3 -> if (text.length == 1) otp4.requestFocus() else if (text.isEmpty()) otp2.requestFocus()
                binding.otp4 -> if (text.length == 1) otp5.requestFocus() else if (text.isEmpty()) otp3.requestFocus()
                binding.otp5 -> if (text.length == 1) otp6.requestFocus() else if (text.isEmpty()) otp4.requestFocus()
                binding.otp6 -> if (text.isEmpty()) otp5.requestFocus()
            }
        }
    }
}