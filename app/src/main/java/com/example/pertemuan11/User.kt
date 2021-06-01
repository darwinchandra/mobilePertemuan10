package com.example.pertemuan11

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id :Int = 0,
    var nama : String = "",
    var email : String = "",
    var no_hp : String = ""): Parcelable {

}