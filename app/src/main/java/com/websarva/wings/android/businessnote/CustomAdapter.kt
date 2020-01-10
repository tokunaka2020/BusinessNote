package com.websarva.wings.android.businessnote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.Nullable


class CustomAdapter( context: Context?, layoutResourceId: Int, toDoData: List<MemoData>): ArrayAdapter<MemoData>(context!!, layoutResourceId, toDoData) {

    private val mCards: List<MemoData>

    override fun getCount(): Int {
        return mCards.size
    }

    @Nullable
    override fun getItem(position: Int): MemoData {
        return mCards[position]
    }

    override fun getView(
        position: Int, @Nullable convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        if (convertView != null) {
            viewHolder = convertView.tag as ViewHolder
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_view, null)
            viewHolder = ViewHolder()
            viewHolder.titleTextView =
                convertView.findViewById<View>(R.id.tvListTitle) as TextView
            viewHolder.contentTextView =
                convertView.findViewById<View>(R.id.tvListContent) as TextView
            convertView.tag = viewHolder
        }
        val toDoData: MemoData = mCards[position]
        viewHolder.titleTextView?.setText(toDoData.getTitle())
        viewHolder.contentTextView?.setText(toDoData.getContent())
        return convertView!!
    }

    fun getMemoDataKey(key: String?): MemoData? {
        for (toDoData in mCards) {
            if (toDoData.getFirebaseKey().equals(key)) {
                return toDoData
            }
        }
        return null
    }

    internal class ViewHolder {
        var titleTextView: TextView? = null
        var contentTextView: TextView? = null
    }

    init {
        mCards = toDoData
    }
}