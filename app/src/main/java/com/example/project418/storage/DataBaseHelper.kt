package com.example.project418.storage

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.project418.models.Department
import com.example.project418.models.Student
import com.example.project418.models.Teachers
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

    fun getListOfTeachers(): List<Teachers> {
        val returnList = mutableListOf<Teachers>()

        val selectQuery =
            "select $ID_FIELD, $LAST_NAME_FIELD, $FIRST_NAME_FIELD, $MIDDLE_NAME_FIELD" +
                    " from $TEACHER_TABLE"
        val cursor: Cursor = database.rawQuery(selectQuery, null)

        if (cursor.moveToFirst())
            do {
                returnList.add(
                    Teachers(
                        id = cursor.getInt(0),
                        lastName = cursor.getString(1),
                        firstName = cursor.getString(2),
                        middleName = cursor.getString(3)
                    )
                )
            } while (cursor.moveToNext())

        cursor.close()

        return returnList
    }

    fun getListOfStudents(): List<Student> {
        val returnList = mutableListOf<Student>()

        val selectQuery =
            "select $ID_FIELD, $LAST_NAME_FIELD, $FIRST_NAME_FIELD, $MIDDLE_NAME_FIELD," +
                    "(select $TITLE_FIELD from $MAJOR_TABLE where $ID_FIELD = $STUDENT_TABLE.$STUDENT_MAJOR_ID_FIELD)||$STUDENT_GROUP_FIELD as SG," +
                    "$STUDENT_COURSE_FIELD from $STUDENT_TABLE"
        val cursor: Cursor = database.rawQuery(selectQuery, null)

        if (cursor.moveToFirst())
            do {
                returnList.add(
                    Student(
                        id = cursor.getInt(0),
                        lastName = cursor.getString(1),
                        firstName = cursor.getString(2),
                        middleName = cursor.getString(3),
                        group = cursor.getString(4),
                        course = cursor.getInt(5)
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
        private const val TEACHER_TABLE = "Teacher"
        private const val STUDENT_TABLE = "Student"
        private const val MAJOR_TABLE = "Major"

        private const val ID_FIELD = "ID"
        private const val TITLE_FIELD = "Title"

        private const val FIRST_NAME_FIELD = "First_Name"
        private const val LAST_NAME_FIELD = "Last_Name"
        private const val MIDDLE_NAME_FIELD = "Middle_Name"

        private const val STUDENT_GROUP_FIELD = "[Group]"
        private const val STUDENT_COURSE_FIELD = "Course"
        private const val STUDENT_MAJOR_ID_FIELD = "Major_ID"
    }
}