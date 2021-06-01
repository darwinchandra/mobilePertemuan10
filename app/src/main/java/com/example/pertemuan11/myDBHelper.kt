package com.example.pertemuan11

import MyDB.userDB
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class myDBHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION)
{

    companion object{
        private val DATABASE_NAME = "mysqlitedb.db"
        private val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_USER_TABLE = "CREATE TABLE ${userDB.userTable.TABLE_USER} " +
                "(${userDB.userTable.COLUMN_ID} INTEGER PRIMARY KEY," +
                "${userDB.userTable.COLUMN_NAME} TEXT," +
                "${userDB.userTable.COLUMN_EMAIL} TEXT," +
                "${userDB.userTable.COLUMN_PHONE} TEXT)"
        db?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${userDB.userTable.TABLE_USER}")
        onCreate(db)
    }

    fun addUser(user: User) : Long{
        var db = this.writableDatabase
        var contentValues = ContentValues().apply {
            put(userDB.userTable.COLUMN_NAME, user.nama)
            put(userDB.userTable.COLUMN_EMAIL, user.email)
            put(userDB.userTable.COLUMN_PHONE, user.no_hp)
        }
        var success = db.insert(userDB.userTable.TABLE_USER, null, contentValues)
        db.close()
        return success
    }

    fun viewAllName() : List<String>{
        val nameList = ArrayList<String>()
        val SELECT_NAME = "SELECT ${userDB.userTable.COLUMN_NAME} FROM " +
                "${userDB.userTable.TABLE_USER}"
        var db = this.readableDatabase
        var cursor : Cursor?=null
        try {
            cursor = db.rawQuery(SELECT_NAME, null)
        }catch (e : SQLException){
            return ArrayList()
        }
        var userName = ""
        if(cursor.moveToFirst()){
            do{
                userName = cursor.getString(cursor.getColumnIndex(userDB.userTable.COLUMN_NAME))
                nameList.add(userName)
            }while (cursor.moveToNext())
        }
        return nameList
    }

    fun deleteUser(nama : String){
        var db = this.writableDatabase
        var selection ="${userDB.userTable.COLUMN_NAME} = ?"
        var selectionArgs = arrayOf(nama)
        db.delete(userDB.userTable.TABLE_USER, selection, selectionArgs)
    }
    fun deleteAllUser(){
        var db = this.writableDatabase
        db.delete(userDB.userTable.TABLE_USER, "",null)
    }

    fun beginUserTransaction(){
        this.writableDatabase.beginTransaction()
    }
    fun successUserTransaction(){
        this.writableDatabase.setTransactionSuccessful()
    }
    fun endUserTransaction(){
        this.writableDatabase.endTransaction()
    }
    fun addUserTransaction(user : User){
        val sqlString = "INSERT INTO ${userDB.userTable.TABLE_USER} " +
                "(${userDB.userTable.COLUMN_ID}" +
                ",${userDB.userTable.COLUMN_NAME}" +
                ",${userDB.userTable.COLUMN_EMAIL}" +
                ",${userDB.userTable.COLUMN_PHONE}) VALUES (?,?,?,?)"
        val myStatement = this.writableDatabase.compileStatement(sqlString)
        myStatement.bindLong(1,user.id.toLong())
        myStatement.bindString(2,user.nama)
        myStatement.bindString(3,user.email)
        myStatement.bindString(4,user.no_hp)
        myStatement.execute()
        myStatement.clearBindings()

    }
}