package com.example.imlearn

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.text.Editable
import android.widget.EditText

class DatabaseHelper(context: Context, factory: CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT," +
                EMAIL_COL + " TEXT" + ")")

        db?.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)

    }

    fun addData(NAME : String, EMAIL : String ){

        val values = ContentValues()

        values.put(NAME_COl, NAME)
        values.put(EMAIL_COL, EMAIL)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun getData(): Cursor? {

        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }

    fun Login(username: String, email: String) : Boolean{
        val db = this.writableDatabase

        val mCursor : Cursor= db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE username=? AND email=?",
            arrayOf<String>(username.toString(), email.toString())
        )
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true
            }
        }
        mCursor.close()
        return false
    }

    @SuppressLint("Range")
    fun updateData(name: String, email: String ): Boolean{

      val db: SQLiteDatabase= this.writableDatabase
       val query =  db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

        val contentValues: ContentValues= ContentValues()
        contentValues.put(NAME_COl, name)
        contentValues.put(EMAIL_COL, email)

        val res:Int = db.update(TABLE_NAME,contentValues, NAME_COl+ "=?",
                                arrayOf(name))
        return res>0

    }

    companion object{
        // here we have defined variables for our database

        private val DATABASE_NAME = "IM_LEARN"

        private val DATABASE_VERSION = 1

        val TABLE_NAME = "Login_details"

        val ID_COL = "ID"

        val NAME_COl = "USERNAME"

        val EMAIL_COL = "EMAIL"
    }
}