package com.websarva.wings.android.businessnote

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


// ログイン画面Activityクラス
class MainActivity : AppCompatActivity() {

    // メンバ変数宣言
    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    var mAuth: FirebaseAuth? = null
    private val TAG = "MainActivity"

    // Activity作成時（初めてActivityが呼ばれた時だけ呼ばれる）
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.etLoginTextEmail);
        passwordEditText = findViewById(R.id.etLoginTextPassword);

        mAuth = FirebaseAuth.getInstance();

    }

    // ログインボタンクリック時
    fun onLoginButtonClick(v: View?) {

        signIn(
            emailEditText?.getText().toString(),
            passwordEditText?.getText().toString()
        )

    }

    // アカウント作成ボタンクリック時
    fun onNewAccountButtonClick(v: View?) {

        createAccount(
            emailEditText?.getText().toString(),
            passwordEditText?.getText().toString()
        )

    }

    // 終了作成ボタンクリック時
    fun onEndButtonClick(v: View?) {
        finish()
    }

    // メールアドレス、パスワードのＮＵＬＬチェック
    private fun checkEmpty(): Boolean {
        if (TextUtils.isEmpty(emailEditText?.getText())) {
            Toast.makeText(this@MainActivity, "メールアドレスが未入力", Toast.LENGTH_SHORT).show()
            return false
        }
        if (TextUtils.isEmpty(passwordEditText?.getText())) {
            Toast.makeText(this@MainActivity, "パスワードが未入力", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    // ログインの処理
    private fun signIn(email: String, password: String) {

        Log.d(TAG, "signIn:$email")

        // 入力チェック
        if (!checkEmpty()) {
            return
        }

        // ログイン
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // ログインに成功したら、ログインしたユーザーの情報でUIを更新します。
                    Log.d(TAG, "Emailログイン:成功")
                    Toast.makeText(this@MainActivity, "ログインに成功しました！", Toast.LENGTH_SHORT).show()

                    // 画面遷移
                    changeActivity()
                } else {
                    // サインインに失敗した場合は、ユーザーにメッセージを表示します。
                    Log.w(TAG, "Emailログイン:失敗", task.exception)

                    // ダイアログ表示
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                    builder.setMessage(R.string.dialog_error_message)
                        .setTitle("エラー！")
                        .setPositiveButton(android.R.string.ok, null)
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }
    }

    // アカウント作成処理
    private fun createAccount(email: String, password: String) {

        Log.d(TAG, "アカウント作成:$email")

        // 入力チェック
        if (!checkEmpty()) {
            return
        }

        // アカウント作成
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // ログインに成功したら、ログインしたユーザーの情報でUIを更新します。
                    Log.d(TAG, "Emailアカウント作成:成功")
                    Toast.makeText(this@MainActivity, "新規作成に成功しました！", Toast.LENGTH_SHORT).show()

                    // 画面遷移
                    changeActivity()
                } else {
                    // サインインに失敗した場合は、ユーザーにメッセージを表示します。
                    Log.w(TAG, "Emailアカウント作成:失敗", task.exception)
                    Toast.makeText(
                        this@MainActivity, "アカウントの作成に失敗しました！",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    // メモリスト表示Activityへ
    private fun changeActivity() {
        // インテント作成
        val intent = Intent(this, ListActivity::class.java)
        // メモリスト表示Activity開始
        startActivity(intent)
        // 画面終了
        finish()
    }


}
