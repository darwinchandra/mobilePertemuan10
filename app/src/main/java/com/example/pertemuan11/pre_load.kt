package com.example.pertemuan11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_pre_load.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class pre_load : AppCompatActivity() {
    var mySQLitedb : myDBHelper? = null
    private var mhs = listOf(
        User(1,"A1","",""),
        User(2,"A2","",""),
        User(3,"A3","",""),
        User(4,"A4","",""),
        User(5,"A5","",""),
        User(6,"A6","",""),
        User(7,"A7","",""),
        User(8,"A8","",""),
        User(9,"A9","",""),
        User(10,"A10","",""),
        User(11,"A11","","")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_load)

        btn_yes.setOnClickListener { executeLoadDataTransaction() }
        btn_no.setOnClickListener { finishThisActivity() }
    }

    private fun finishThisActivity() {
        var myFirstRunSharePref = FirstRunSharePref(this)
        myFirstRunSharePref.firstRun = false
        this.finish()
    }

    private fun executeLoadData() {
        btn_no.isEnabled = false
        btn_yes.isEnabled = false
        myProgress. progress = 0
        var progress = 0
        mySQLitedb = myDBHelper(this)
        doAsync {
            for (userData in mhs) {
                progress += 1
                mySQLitedb?.addUser(userData)
                uiThread {
                    myProgress.progress += progress / mhs.size * 100
                    Log.w ("Progress", "${myProgress.progress}") }
            }
            uiThread { finishThisActivity() }
        }
    }
    private fun executeLoadDataTransaction() {
        btn_no.isEnabled = false
        btn_yes.isEnabled = false
        myProgress. progress = 0
        var progress = 0
        mySQLitedb = myDBHelper(this)
        doAsync {
            mySQLitedb?.beginUserTransaction()
            for (userData in mhs) {
                progress += 1
                mySQLitedb?.addUserTransaction(userData)
                uiThread {
                    myProgress.progress += progress / mhs.size * 100
                    Log.w ("Progress", "${myProgress.progress}") }
            }
            mySQLitedb?.successUserTransaction()
            mySQLitedb?.endUserTransaction()
            uiThread { finishThisActivity() }
        }
    }
}