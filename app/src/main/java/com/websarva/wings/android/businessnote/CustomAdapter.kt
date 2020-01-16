package com.websarva.wings.android.businessnote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class CustomAdapter( context: Context?, layoutResourceId: Int, memoData: List<MemoData>): ArrayAdapter<MemoData>(context!!, layoutResourceId, memoData) {

    private val mCards: List<MemoData>

    override fun getCount(): Int {
        return mCards.size
    }

    override fun getItem(position: Int): MemoData {
        return mCards[position]
    }

    override fun getView(
        position: Int, convertView: View?,
        parent: ViewGroup
    ): View {

        var convert_view = convertView
        val viewHolder: ViewHolder

        if (convert_view != null) {
            viewHolder = convert_view.tag as ViewHolder
        } else {
            convert_view = LayoutInflater.from(context).inflate(R.layout.card_view, null)
            viewHolder = ViewHolder()
            viewHolder.titleTextView =
                convert_view.findViewById<View>(R.id.tvListTitle) as TextView
            viewHolder.contentTextView =
                convert_view.findViewById<View>(R.id.tvListContent) as TextView
            convert_view.tag = viewHolder
        }

        val memoData: MemoData = mCards[position]

        viewHolder.titleTextView?.setText(memoData.title)
        viewHolder.contentTextView?.setText(memoData.content)

        return convert_view!!
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