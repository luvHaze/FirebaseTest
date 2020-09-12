package com.zoey.firebaselogintest

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.api.Result
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.zoey.firebaselogintest.Constants.TAG
import kotlinx.android.synthetic.main.dialog_signup.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class SignUpDialong(context: Context) : Dialog(context) {

    private var mAuth = FirebaseAuth.getInstance()
    private var job = Job()
    private var uiContext = CoroutineScope(Dispatchers.Main + job)
    init {
        // 액티비티의 컨택스트를 가져오고 나서 setOwnerAcitivty 주인이 누구인지 설정해줘야
        // ownerActivity를 사용할 수 있다 (안하면 호출하면 null값 계속 출력함)
        if (context is Activity) {
            setOwnerActivity(context)
            Log.d(TAG, "Dialog - ownerAcitivty : ${ownerActivity.toString()}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_signup)

        Log.d(TAG, "Dialog - onCrate()")
        dialog_signup_button.setOnClickListener {
            val id = dialog_id_inputtext.text.toString()
            val pw = dialog_pw_inputtext.text.toString()
            firebaseSignUp(id, pw, this)

        }
    }


    fun firebaseSignUp(id: String, pw: String, dialog: Dialog) {

        mAuth.createUserWithEmailAndPassword(id, pw)
            .addOnCompleteListener(this.ownerActivity!!) {
                if (it.isSuccessful) {
                    Log.d(TAG, "Dialog - firebaseSignUp() - 계정 생성 성공")
                    dialog.dismiss()
                    Toast.makeText(context, "계정생성 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(TAG, "Dialog - firebaseSignUp() - 계정 생성 실패")
                    Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }

            }
    }

}