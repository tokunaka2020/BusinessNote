package com.websarva.wings.android.businessnote

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AddActivity : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance()
    var reference = database.reference
    var titleEditText: EditText? = null
    var contentEditText: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        titleEditText = findViewById(R.id.etTitle)
        contentEditText = findViewById(R.id.etContent)

    }

    fun save() {

        val title = titleEditText!!.text.toString()
        val content = contentEditText!!.text.toString()
        val key = reference.push().key
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user!!.uid

        //    引数のToDoDataの内容をデータベースに送る。
        val memoData = MemoData(key,title,content)

        reference.child("users").child(uid).child(key!!).setValue(memoData)
            .addOnSuccessListener { finish() }

    }

}
