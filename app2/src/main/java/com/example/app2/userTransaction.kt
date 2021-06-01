package com.example.app2

import android.content.Context
import com.example.app2.userDB.userTable.Companion.COLUMN_EMAIL
import com.example.app2.userDB.userTable.Companion.COLUMN_ID
import com.example.app2.userDB.userTable.Companion.COLUMN_NAME
import com.example.app2.userDB.userTable.Companion.COLUMN_PHONE

class userTransaction(context: Context) {
    private val myContentResolver = context.contentResolver

    fun viewAllName() : List<String>{
        var myNameList = ArrayList<String>()
        var mProjection = arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PHONE)
        var cursor = myContentResolver.query(myContentProviderURI.CONTENT_URI,mProjection,null,null,null)
        if(cursor!=null){
            var userName: String =""
            if (cursor.moveToFirst()) {
                do {
                    userName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                    myNameList.add(userName)
                } while (cursor.moveToNext())
            }

        }
        return myNameList
    }
}