package com.example.project418.storage

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.project418.models.Department
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DataBaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val database: SQLiteDatabase

    init {
        database = open()
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    private fun open(): SQLiteDatabase {
        val dbFile = context.getDatabasePath("$DATABASE_NAME.db")

        if (!dbFile.exists()) {
            try {
                val checkDb =
                    context.openOrCreateDatabase("$DATABASE_NAME.db", Context.MODE_PRIVATE, null)
                checkDb.close()
                copyDatabase(dbFile)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    private fun copyDatabase(dbFile: File) {
        val inputStream = context.assets.open("$DATABASE_NAME.db")
        val outputStream = FileOutputStream(dbFile)

        inputStream.copyTo(outputStream)

        outputStream.flush()
        outputStream.close()
        inputStream.close()
    }

    fun closeDb() = database.close()

    fun getListOfDepartments(): List<Department> {
        val returnList = mutableListOf<Department>()

        val selectQuery = "select * from $DEPARTMENT_TABLE"
        val cursor: Cursor = database.rawQuery(selectQuery, null)

        if (cursor.moveToFirst())
            do {
                returnList.add(
                    Department(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                    )
                )
            } while (cursor.moveToNext())

        cursor.close()

        return returnList
    }

    companion object {
        private const val DATABASE_NAME = "Project418"
        private const val DATABASE_VERSION = 1
        private const val DEPARTMENT_TABLE = "Department"
    }
}