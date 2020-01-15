package com.websarva.wings.android.businessnote

class MemoData {

    var title: String? = null
    var content: String? = null
    var firebaseKey: String? = null

    constructor(parameter_key: String?, parameter_title: String?, parameter_content: String?) {
        firebaseKey = parameter_key
        title = parameter_title
        content = parameter_content
    }

    constructor() {}

}
