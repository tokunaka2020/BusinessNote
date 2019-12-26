package com.websarva.wings.android.businessnote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val googleSignInButton = findViewById<SignInButton>(R.id.btGoogleLogin)
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD)
        val listener = LoginListener()
        googleSignInButton.setOnClickListener(listener)

        val loginButton = findViewById<Button>(R.id.btMailLogin)
        loginButton.setOnClickListener(listener)

    }

    private inner class LoginListener : View.OnClickListener {
        override fun onClick(view: View) {
            when(view.id){
                // Google サインイン
                R.id.btGoogleLogin -> {
                    loginGoogle()
                }
                // メールアドレス ログイン
                R.id.btMailLogin -> {
                    loginEmail()
                }
            }
        }
    }

    // Google サインイン
    fun loginGoogle(){
        Toast.makeText(applicationContext,"Google SignIn",Toast.LENGTH_SHORT).show()
        val auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    // メールアドレス ログイン
    fun loginEmail(){
        Toast.makeText(applicationContext,"Email SignIn",Toast.LENGTH_SHORT).show()
    }
}
