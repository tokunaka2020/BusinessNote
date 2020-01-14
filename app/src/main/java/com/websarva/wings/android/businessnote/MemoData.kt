package com.websarva.wings.android.businessnote

class MemoData {

    var title: String? = null
    var content: String? = null
    var firebaseKey: String? = null

    constructor(prmkey: String?, prmtitle: String?, prmcontent: String?) {
        firebaseKey = prmkey
        title = prmtitle
        content = prmcontent
    }

    constructor() {}

}
