package com.websarva.wings.android.businessnote

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ModifyActivity : AppCompatActivity()  {

    var database = FirebaseDatabase.getInstance()
    var reference = database.reference
    var titleEditText: EditText? = null
    var contentEditText: EditText? = null
    var firebasekey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        titleEditText = findViewById(R.id.etTitle)
        contentEditText = findViewById(R.id.etContent)

        val title = intent.getStringExtra("parameter_title")
        titleEditText!!.setText(title)

        val content = intent.getStringExtra("parameter_content")
        contentEditText!!.setText(content)

        firebasekey = intent.getStringExtra("parameter_firebasekey")
    }

    fun modify(view: View) {

        val title = titleEditText!!.text.toString()
        val content = contentEditText!!.text.toString()
        val key = firebasekey
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user!!.uid

        //    引数のToDoDataの内容をデータベースに送る。
        val memoData = MemoData(key,title,content)

        reference.child("users").child(uid).child(key!!).setValue(memoData)
            .addOnSuccessListener { finish() }

    }

    fun cancel(view: View) {
        finish()
    }
}