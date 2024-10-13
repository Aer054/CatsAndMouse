package com.example.catandmouse

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "clicks.db"
        const val DB_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("DbHelper", "onCreate called")
        db.execSQL("CREATE TABLE IF NOT EXISTS clicks(" +
                "id INTEGER PRIMARY KEY, " +
                "screenClick INT, " +
                "mouseClick INT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    fun insertClickData(screenClick: Int, mouseClick: Int) {
        val db = writableDatabase
        val sql = "INSERT INTO clicks (screenClick, mouseClick) VALUES (?, ?)"
        val statement = db.compileStatement(sql)
        statement.bindLong(1, screenClick.toLong())
        statement.bindLong(2, mouseClick.toLong())
        statement.executeInsert()
        Log.d("DbHelper", "Data inserted: screenClick = $screenClick, mouseClick = $mouseClick") // Логирование вставки данных
        db.close()
    }

    @SuppressLint("Range")
    fun getLast10Clicks(): List<Clicks> {
        val clicksList = mutableListOf<Clicks>()
        val db = readableDatabase

        // Запрос, который возвращает последние 10 записей, отсортированных по id
        val cursor = db.rawQuery("SELECT screenClick, mouseClick FROM clicks ORDER BY id DESC LIMIT 10", null)

        if (cursor.moveToFirst()) {
            do {
                val screenClick = cursor.getInt(cursor.getColumnIndex("screenClick"))
                val mouseClick = cursor.getInt(cursor.getColumnIndex("mouseClick"))
                clicksList.add(Clicks(screenClick.toString(), mouseClick.toString()))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return clicksList
    }

}
data class Clicks(
    val screenClick: String,
    val mouseClick: String
)