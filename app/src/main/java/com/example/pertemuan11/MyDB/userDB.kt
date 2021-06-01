package MyDB

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