package com.example.pertemuan11.provider

import MyDB.userDB
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.example.pertemuan11.myDBHelper

class myContentProvider : ContentProvider(){
    private var dbHelper : myDBHelper? = null
    override fun onCreate(): Boolean {
        dbHelper = myDBHelper(context!!)
        return true
    }

    override fun query(p0: Uri, p1: Array<out String>?, p2: String?, p3: Array<out String>?, p4: String?): Cursor? {

        var queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = MyDB.userDB.userTable.TABLE_USER
        var cursor = queryBuilder.query(dbHelper?.readableDatabase,
            p1,p2,p3,null, null,p4)
        cursor.setNotificationUri(context?.contentResolver,p0)
        return cursor
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }
    companion object{
        val AUTHORITY = "com.example.pertemuan11.provider.provider.myContentProvider"
        private val USER_TABLE = userDB.userTable.TABLE_USER
        val CONTENT_URI : Uri = Uri.parse("content://$AUTHORITY/$USER_TABLE")
    }

}