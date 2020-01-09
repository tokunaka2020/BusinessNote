package com.websarva.wings.android.businessnote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    var data: Intent? = null
    var mAuth: FirebaseAuth? = null
    private val TAG = "EmailPassword"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.etLoginTextEmail);
        passwordEditText = findViewById(R.id.etLoginTextPassword);

        mAuth = FirebaseAuth.getInstance();

    }

    private fun checkEmpty(): Boolean {
        if (TextUtils.isEmpty(emailEditText?.getText())) {
            Log.d("MainActivity", "何も記入されていません")
            return false
        }
        if (TextUtils.isEmpty(passwordEditText?.getText())) {
            Log.d("MainActivity", "何も記入されていません")
            return false
        }
        return true
    }

    fun onLoginButtonClick(v: View?) {

        signIn(
            emailEditText?.getText().toString(),
            passwordEditText?.getText().toString()
        )

        setResult(Activity.RESULT_OK, data)
    }

    fun onNewAccountButtonClick(v: View?) {

        createAccount(
            emailEditText?.getText().toString(),
            passwordEditText?.getText().toString()
        )
        setResult(Activity.RESULT_OK, data)
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!checkEmpty()) {
            return
        }
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) { // ログインに成功したら、ログインしたユーザーの情報でUIを更新します。
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(this@MainActivity, "ログインに成功しました！", Toast.LENGTH_SHORT).show()
                    changeActivity()
                } else { // サインインに失敗した場合は、ユーザーにメッセージを表示します。
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                    builder.setMessage(task.exception!!.message)
                        .setTitle("Error!")
                        .setPositiveButton(android.R.string.ok, null)
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!checkEmpty()) {
            return
        }
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) { // ログインに成功したら、ログインしたユーザーの情報でUIを更新します。
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(this@MainActivity, "新規作成に成功しました！", Toast.LENGTH_SHORT).show()
                    changeActivity()
                } else { // サインインに失敗した場合は、ユーザーにメッセージを表示します。
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this@MainActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun changeActivity() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
        finish()
    }


}
