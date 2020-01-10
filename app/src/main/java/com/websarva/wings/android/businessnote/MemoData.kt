package com.websarva.wings.android.businessnote

class MemoData {

    constructor(key: String, title: String?, content: String?): this() {
        _firebaseKey = key
        _title = title
        _content = content
    }

    var _title: String? = null
    var _content: String? = null
    var _firebaseKey: String? = null

    fun MemoData() {

    }

    fun getFirebaseKey(): String? {
        return _firebaseKey
    }

    fun setFirebaseKey(firebaseKey: String?) {
        _firebaseKey = firebaseKey
    }

    fun getTitle(): String? {
        return _title
    }

    fun setTitle(title: String?) {
        _title = title
    }

    fun getContent(): String? {
        return _content
    }

    fun setContent(context: String?) {
        _content = context
    }

}