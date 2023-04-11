package com.example.project418.storage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.project418.models.*
import org.koin.dsl.module
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DataBaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private var database: SQLiteDatabase

    init {
        database = open()
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        database = open()
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

    private fun closeDb(db: SQLiteDatabase) = db.close()

    fun getListOfDepartments(): List<Department> {
        val db = database

        val returnList = mutableListOf<Department>()

        val selectQuery = "select * from $DEPARTMENT_TABLE"
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) do {
            returnList.add(
                Department(
                    cursor.getInt(0), cursor.getString(1), cursor.getString(2)
                )
            )
        } while (cursor.moveToNext())

        cursor.close()

        return returnList
    }

    fun getListOfTeachers(): List<Teachers> {
        val db = database

        val returnList = mutableListOf<Teachers>()

        val selectQuery =
            "select $ID_FIELD, $LAST_NAME_FIELD, $FIRST_NAME_FIELD, $MIDDLE_NAME_FIELD" + " from $TEACHER_TABLE where $DEPARTMENT_ID_FIELD = ${
                UserConfig.getID()
            }"
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) do {
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
        val db = database

        val returnList = mutableListOf<Student>()

        val selectQuery =
            "select $ID_FIELD, $LAST_NAME_FIELD, $FIRST_NAME_FIELD, $MIDDLE_NAME_FIELD, " +
                    "(select $TITLE_FIELD from $MAJOR_TABLE where $ID_FIELD = $STUDENT_TABLE.$STUDENT_MAJOR_ID_FIELD)||$STUDENT_GROUP_FIELD as SG, " +
                    "$STUDENT_COURSE_FIELD from $STUDENT_TABLE"
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) do {
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

    fun getListOfSubjects(): List<Subject> {
        val db = database
        val returnList = mutableListOf<Subject>()

        val selectQuery =
            "select $ID_FIELD, $TITLE_FIELD, " +
                    "(select $TITLE_FIELD from $TYPE_OF_WORK_TABLE where $ID_FIELD = $SUBJECT_TABLE.$SUBJECT_TYPE_OF_WORK_FIELD) as TOW, " +
                    "$SUBJECT_TYPE_OF_WORK_FIELD " +
                    "from $SUBJECT_TABLE " +
                    "where $DEPARTMENT_ID_FIELD = ${UserConfig.getID()}"
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst())
            do {
                returnList.add(
                    Subject(
                        id = cursor.getInt(0),
                        title = cursor.getString(1),
                        typeOfWork = cursor.getString(2),
                        typeOfWorkID = cursor.getInt(3)
                    )
                )
            } while (cursor.moveToNext())

        cursor.close()

        return returnList
    }

    fun getStudent(studentID: Int): String {
        val db = database

        val selectQuery =
            "select ($LAST_NAME_FIELD||' '||$FIRST_NAME_FIELD||' '||$MIDDLE_NAME_FIELD) as FIO " +
                    "from $STUDENT_TABLE where $ID_FIELD = $studentID"
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        val student = if (cursor.moveToFirst()) cursor.getString(0) else "Error"

        cursor.close()

        return student
    }

    fun getSubject(subjectID: Int): String {
        val db = database

        val selectQuery = "select $TITLE_FIELD from $SUBJECT_TABLE where $ID_FIELD = $subjectID"

        val cursor: Cursor = db.rawQuery(selectQuery, null)

        val subject = if (cursor.moveToFirst()) cursor.getString(0) else "Error"

        cursor.close()

        return subject
    }

    fun getTypeOfWork(typeOfWorkID: Int): String {
        val db = database

        val selectQuery =
            "select $TITLE_FIELD from $TYPE_OF_WORK_TABLE where $ID_FIELD = $typeOfWorkID"

        val cursor: Cursor = db.rawQuery(selectQuery, null)

        val typeOfWork = if (cursor.moveToFirst()) cursor.getString(0) else "Error"

        cursor.close()

        return typeOfWork
    }

    fun registerTest(
        student: Int,
        subject: Int,
        teacher: Int,
        registrationDate: Long
    ): Long {
        val values = ContentValues()
        values.put(MAIN_STUDENT_ID_FIELD, student)
        values.put(MAIN_SUBJECT_ID_FIELD, subject)
        values.put(MAIN_TEACHER_ID_FIELD, teacher)
        values.put(TITLE_FIELD, EMPTY_TITLE)
        values.put(MAIN_REGISTRATION_FIELD, registrationDate)
        values.put(DEPARTMENT_ID_FIELD, UserConfig.getID())

        return database.insert(MAIN_TABLE, null, values)
    }

    fun registerCourseWork(
        student: Int,
        subject: Int,
        teacher: Int,
        titleOfWork: String,
        registrationDate: Long
    ): Long {
        val values = ContentValues()
        values.put(MAIN_STUDENT_ID_FIELD, student)
        values.put(MAIN_SUBJECT_ID_FIELD, subject)
        values.put(MAIN_TEACHER_ID_FIELD, teacher)
        values.put(TITLE_FIELD, titleOfWork)
        values.put(MAIN_REGISTRATION_FIELD, registrationDate)
        values.put(DEPARTMENT_ID_FIELD, UserConfig.getID())

        return database.insert(MAIN_TABLE, null, values)
    }

    fun getJournal(): List<Journal> {
        val db = database

        val returnList = mutableListOf<Journal>()

        val selectQuery =
            "select $ID_FIELD, $TITLE_FIELD, " +
                    "(select $LAST_NAME_FIELD||' '||$FIRST_NAME_FIELD||' '||$MIDDLE_NAME_FIELD from $STUDENT_TABLE where $ID_FIELD = $MAIN_TABLE.$MAIN_STUDENT_ID_FIELD) " +
                    "as Student, " +
                    "(select $TITLE_FIELD from $SUBJECT_TABLE where $ID_FIELD = $MAIN_TABLE.$MAIN_SUBJECT_ID_FIELD) as Subject, " +
                    "(select $TITLE_FIELD from $TYPE_OF_WORK_TABLE where $ID_FIELD = (select $SUBJECT_TYPE_OF_WORK_FIELD from $SUBJECT_TABLE where $ID_FIELD = $MAIN_TABLE.$MAIN_SUBJECT_ID_FIELD)) as TypeOfWork, " +
                    "(select $LAST_NAME_FIELD||' '||$FIRST_NAME_FIELD||' '||$MIDDLE_NAME_FIELD from $TEACHER_TABLE where $ID_FIELD =$MAIN_TABLE.$MAIN_TEACHER_ID_FIELD) as Teacher, " +
                    "$MAIN_REGISTRATION_FIELD from $MAIN_TABLE where $DEPARTMENT_ID_FIELD = ${
                        UserConfig.getID()
                    }"

        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst())
            do {
                returnList.add(
                    Journal(
                        id = cursor.getInt(0),
                        title = cursor.getString(1),
                        student = cursor.getString(2),
                        subject = cursor.getString(3),
                        typeOfWork = cursor.getString(4),
                        teacher = cursor.getString(5),
                        registrationData = cursor.getLong(6)
                    )
                )
            } while (cursor.moveToNext())

        cursor.close()

        return returnList
    }

    fun singIn(user: Department): Int {
        val db = database


        val selectQuery =
            "select $ID_FIELD from $DEPARTMENT_TABLE where $LOGIN = '${user.login}' and $PASSWORD = '${user.password}'"

        val cursor: Cursor = db.rawQuery(selectQuery, null)

        val departmentID = if (cursor.moveToFirst()) cursor.getInt(0) else FALSE_USER

        cursor.close()

        return departmentID
    }

    companion object {
        private const val DATABASE_NAME = "Project418"
        private const val DATABASE_VERSION = 1

        private const val MAIN_TABLE = "Main"
        private const val DEPARTMENT_TABLE = "Department"
        private const val TEACHER_TABLE = "Teachers"
        private const val STUDENT_TABLE = "Student"
        private const val MAJOR_TABLE = "Major"
        private const val SUBJECT_TABLE = "Subject"
        private const val TYPE_OF_WORK_TABLE = "Type_Of_Work"

        private const val ID_FIELD = "ID"
        private const val TITLE_FIELD = "Title"

        private const val FIRST_NAME_FIELD = "First_Name"
        private const val LAST_NAME_FIELD = "Last_Name"
        private const val MIDDLE_NAME_FIELD = "Middle_Name"

        private const val STUDENT_GROUP_FIELD = "[Group]"
        private const val STUDENT_COURSE_FIELD = "Course"
        private const val STUDENT_MAJOR_ID_FIELD = "Major_ID"

        private const val SUBJECT_TYPE_OF_WORK_FIELD = "Type_Of_Work_ID"
        private const val DEPARTMENT_ID_FIELD = "Department_ID"

        private const val MAIN_STUDENT_ID_FIELD = "Student_ID"
        private const val MAIN_SUBJECT_ID_FIELD = "Subject_ID"
        private const val MAIN_TEACHER_ID_FIELD = "Teacher_ID"
        private const val MAIN_REGISTRATION_FIELD = "Registration_Date"

        private const val LOGIN = "Login"
        private const val PASSWORD = "Password"

        private const val EMPTY_TITLE = "-"

        private const val FALSE_USER = -1
    }
}

val databaseModule = module {
    single { DataBaseHelper(get()) }
}