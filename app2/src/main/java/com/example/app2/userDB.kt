package com.example.app2

import android.net.Uri
import android.provider.BaseColumns

object userDB{
    class userTable : BaseColumns {
        companion object{
            val TABLE_USER = "tbl_User"
            val COLUMN_ID = "User_Id"
            val COLUMN_NAME = "User_Name"
            val COLUMN_EMAIL = "User_Email"
            val COLUMN_PHONE = "User_Phone"
        }
    }
}
class myContentProviderURI{
    companion object{
        val AUTHORITY = "com.example.pertemuan11.provider.provider.myContentProvider"
        private val USER_TABLE = userDB.userTable.TABLE_USER
        val CONTENT_URI : Uri = Uri.parse("content://$AUTHORITY/$USER_TABLE")
    }
}