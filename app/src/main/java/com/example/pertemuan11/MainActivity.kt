package com.example.pertemuan11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    var mySQLitedb : myDBHelper? = null
    var myFirstRunSharePref : FirstRunSharePref? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mySQLitedb = myDBHelper(this)
        myFirstRunSharePref = FirstRunSharePref(this)

        if(myFirstRunSharePref!!.firstRun){
            val secondIntent = Intent(this,pre_load::class.java)
            startActivity(secondIntent)
        }
        updateAdapter()
        btn_submit.setOnClickListener {
            var userTmp = User()
            userTmp.nama = edit_text_name.text.toString()
            userTmp.email = edit_text_email.text.toString()
            userTmp.no_hp= edit_text_phone_number.text.toString()
            var result = mySQLitedb?.addUser(userTmp)
            if(result!=-1L){
                Toast.makeText(this,"Berhasil", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
            }
            updateAdapter()
            edit_text_name.text.clear()
            edit_text_email.text.clear()
            edit_text_phone_number.text.clear()
        }
        btn_delete.setOnClickListener {
            var nama = spinner1.selectedItem.toString()
            if(nama!=null || nama != ""){
                doAsync {
                    mySQLitedb?.deleteUser(nama)
                    updateAdapter()
                }
            }
        }
    }
    fun updateAdapter(){
        doAsync {
            var nameList = mySQLitedb?.viewAllName()?.toTypedArray()
            uiThread {
                if(spinner1 != null && nameList?.size != 0){
                    var arrayAdapter = ArrayAdapter(applicationContext,
                        android.R.layout.simple_spinner_dropdown_item,
                        nameList!!
                    )
                    spinner1.adapter = arrayAdapter
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateAdapter()
    }

}