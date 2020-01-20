package com.websarva.wings.android.businessnote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class CustomAdapter( context: Context?, layoutResourceId: Int, memoData: List<MemoData>): ArrayAdapter<MemoData>(context!!, layoutResourceId, memoData) {

    private val mCards: List<MemoData>

    // 配列のサイズ（要素数）を返す
    override fun getCount(): Int {
        return mCards.size
    }

    // 配列の値を返す
    override fun getItem(position: Int): MemoData {
        return mCards[position]
    }

    // ビューを返す
    override fun getView( position: Int, convertView: View?, parent: ViewGroup ): View {

        var view = convertView
        val viewHolder: ViewHolder

        if (view != null) {
            viewHolder = view.tag as ViewHolder
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.card_view, null)
            viewHolder = ViewHolder()
            viewHolder.titleTextView = view.findViewById<View>(R.id.tvListTitle) as TextView
            viewHolder.contentTextView = view.findViewById<View>(R.id.tvListContent) as TextView
            view.tag = viewHolder
        }

        val memoData: MemoData = mCards[position]

        viewHolder.titleTextView?.setText(memoData.title)
        viewHolder.contentTextView?.setText(memoData.content)

        return view!!
    }

    fun getMemoDataKey(key: String?): MemoData? {
        for (memoData in mCards) {
            if (memoData.firebaseKey.equals(key)) {
                return memoData
            }
        }
        return null
    }

    internal class ViewHolder {
        var titleTextView: TextView? = null
        var contentTextView: TextView? = null
    }

    init {
        mCards = memoData
    }
}