package com.websarva.wings.android.businessnote

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import java.util.ArrayList


class ListActivity : AppCompatActivity(), AdapterView.OnItemLongClickListener {

    var user: FirebaseUser? = null
    var uid: String? = null
    var mAuth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null
    var reference: DatabaseReference? = null
    var mCustomAdapter: CustomAdapter? = null
    var mListView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        //ログイン情報を取得
        user = FirebaseAuth.getInstance().currentUser

        //user id = Uid を取得する
        uid = user!!.uid

        database = FirebaseDatabase.getInstance()
        reference = database!!.getReference("users").child(uid!!)
        mListView = findViewById<View>(R.id.lvRecord) as ListView

        //CustomAdapterをセット
        mCustomAdapter = CustomAdapter(applicationContext, R.layout.card_view, ArrayList<MemoData>())
        mListView!!.setAdapter(mCustomAdapter)

        //LongListenerを設定
        mListView!!.setOnItemLongClickListener(this)

        //firebaseと同期するリスナー
        reference!!.addChildEventListener(object : ChildEventListener {

            //            データを読み込むときはイベントリスナーを登録して行う。
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // アイテムのリストを取得するか、アイテムのリストへの追加がないかリッスンします。
                val memoData = dataSnapshot.getValue(MemoData::class.java)

                mCustomAdapter!!.add(memoData)
                mCustomAdapter!!.notifyDataSetChanged()
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // リスト内のアイテムに対する変更がないかリッスンします。
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                // リストから削除されるアイテムがないかリッスンします。
                Log.d("ListActivity", "onChildRemoved:" + dataSnapshot.getKey())
                val result = dataSnapshot.getValue(MemoData::class.java) ?: return
                val item = mCustomAdapter!!.getMemoDataKey(result.getFirebaseKey())

                mCustomAdapter!!.remove(item)
                mCustomAdapter!!.notifyDataSetChanged()
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // 並べ替えリストの項目順に変更がないかリッスンします。
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // ログを記録するなどError時の処理を記載する。
            }
        })
    }

    fun onAddRecordButtonClick(v: View?) {
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }

    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ): Boolean {

        val toDoData: MemoData = mCustomAdapter!!.getItem(position)

        uid = user!!.uid
        AlertDialog.Builder(this)
            .setTitle("Done?")
            .setMessage("この項目を完了しましたか？")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    // OK button pressed
                    reference!!.child(toDoData.getFirebaseKey()).removeValue()
                    //                        mCustomAdapter.remove(toDoData);
                })
            .setNegativeButton("No", null)
            .show()
        return false
    }

    fun onLogoutButtonClick(v: View?) {
        mAuth = FirebaseAuth.getInstance()
        mAuth!!.signOut()
        val intent = Intent(this@ListActivity, LoginActivity::class.java)
        intent.putExtra("check", true)
        startActivity(intent)
        finish()
    }
}