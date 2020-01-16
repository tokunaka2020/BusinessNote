package com.websarva.wings.android.businessnote

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*


class ListActivity : AppCompatActivity() {

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

    }

    override fun onStart() {
        super.onStart()

        //ログイン情報を取得
        user = FirebaseAuth.getInstance().currentUser

        //user id = Uid を取得する
        uid = user!!.uid

        database = FirebaseDatabase.getInstance()
        reference = database!!.getReference("users").child(uid!!)
        mListView = findViewById<View>(R.id.lvRecord) as ListView

        //CustomAdapterをセット
        mCustomAdapter = CustomAdapter(applicationContext, R.layout.card_view, ArrayList<MemoData>())
        //mListView!!.setAdapter(mCustomAdapter)
        mListView!!.adapter = mCustomAdapter

        //クリックリスナーを設定
        mListView!!.onItemClickListener = ListItemClickListener()
        //ロングクリックリスナーを設定
        mListView!!.onItemLongClickListener = ListItemLongClickListener()

        //firebaseと同期するリスナー
        reference!!.addChildEventListener(object : ChildEventListener {

            // データを読み込むときはイベントリスナーを登録して行う。
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
                val item = mCustomAdapter!!.getMemoDataKey(result.firebaseKey)

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

    fun onAddRecordButtonClick(view: View?) {
        val intent = Intent(applicationContext, AddActivity::class.java)
        startActivity(intent)
    }

    private inner class ListItemLongClickListener: AdapterView.OnItemLongClickListener {

        override fun onItemLongClick(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ): Boolean {

            val memoData: MemoData? =  mCustomAdapter!!.getItem(position)
            uid = user!!.uid

            AlertDialog.Builder(parent!!.context)
                .setTitle("確認")
                .setMessage("この行を削除しますか？")
                .setPositiveButton("はい", { dialog, which ->
                    reference!!.child(memoData!!.firebaseKey!!).removeValue()
                })
                .setNegativeButton("いいえ", null)
                .show()

            // trueを返すことによってonItemLongClick() のあとに onItemClick() が呼ばれなくなる
            return true
        }
    }

    fun onLogoutButtonClick(view: View?) {
        mAuth = FirebaseAuth.getInstance()
        mAuth!!.signOut()
        val intent = Intent(this@ListActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private inner class ListItemClickListener: AdapterView.OnItemClickListener {

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val memoData: MemoData? =  mCustomAdapter!!.getItem(position)
            uid = user!!.uid

            val title = memoData!!.title
            val content = memoData.content
            val firebasekey = memoData.firebaseKey

            val intent = Intent(applicationContext, ModifyActivity::class.java)

            intent.putExtra("parameter_title", title)
            intent.putExtra("parameter_content", content)
            intent.putExtra("parameter_firebasekey", firebasekey)

            startActivity(intent)
        }

    }

}