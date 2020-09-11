package com.zoey.firebaselogintest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.zoey.firebaselogintest.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_signup.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mAuth: FirebaseAuth
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        // val binding2 = ActivityMainBinding.inflate(layoutInflater) -> 이 방법도 가
        binding.lifecycleOwner = this

        mAuth = FirebaseAuth.getInstance()

        signup_button.setOnClickListener(this)
        login_button.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        Log.d(Constants.TAG, "Current User is $currentUser")
    }

    override fun onClick(view: View?) {
        when(view) {
            signup_button -> {
                val dialog = SignUpDialong(this)
                dialog.show()
            }
            login_button -> {
                val id = input_id_inputtext.text.toString()
                val pw = input_pw_inputtext.text.toString()
                signIn(id, pw)
            }
        }
    }

    fun signIn(id: String, pw: String) {
        mAuth.signInWithEmailAndPassword(id, pw).addOnCompleteListener(this) {
            if(it.isSuccessful) {
                Log.d(Constants.TAG, "MainActivity - signIn() : Login Success")
                user = mAuth.currentUser
                nextActivity()

            } else {
                Log.d(Constants.TAG, "MainActivity - signIn() : Login Failed")
                Toast.makeText(this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
            }
        }
    }

    fun nextActivity() {
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }
}