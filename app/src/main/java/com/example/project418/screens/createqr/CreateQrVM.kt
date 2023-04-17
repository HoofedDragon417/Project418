package com.example.project418.screens.createqr

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project418.R
import com.example.project418.common.AppGlobal
import com.example.project418.models.Student
import com.example.project418.models.Subject
import com.example.project418.storage.DataBaseHelper
import com.example.project418.storage.UserConfig
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class CreateQrVM(private val dataBaseHelper: DataBaseHelper) : ViewModel() {
    private var listOfStudents = listOf<Student>()
    private var listOfSubjects = listOf<Subject>()

    val stringListOfStudents = MutableStateFlow(listOf<String>())
    val stringListOfSubjects = MutableStateFlow(listOf<String>())

    val showStudentError = MutableStateFlow<String?>(null)
    val showSubjectError = MutableStateFlow<String?>(null)

    private var studentPosition = -1
    private var subjectPosition = -1

    fun getLists() {
        viewModelScope.launch {
            listOfStudents = dataBaseHelper.getListOfStudents()
            listOfSubjects = dataBaseHelper.getListOfSubjects()

            val listStudents = mutableListOf<String>()
            val listSubjects = mutableListOf<String>()

            for (student in listOfStudents) {
                listStudents.add("${student.lastName} ${student.firstName} ${student.middleName}")
            }
            stringListOfStudents.value = listStudents

            for (subject in listOfSubjects) {
                listSubjects.add(subject.title)
            }
            stringListOfSubjects.value = listSubjects

        }
    }

    fun getStudentIndex(student: String) {
        viewModelScope.launch {
            studentPosition = stringListOfStudents.value.indexOf(student)
        }
    }

    fun getSubjectIndex(subject: String) {
        viewModelScope.launch {
            subjectPosition = stringListOfSubjects.value.indexOf(subject)
        }
    }

    fun generateQr() {
        viewModelScope.launch {
            if (studentPosition != -1 && subjectPosition != -1) {
                val qrBitmap = generateBitmap()

                saveQr(qrBitmap)
            } else {
                if (studentPosition == -1)
                    showStudentError.value = AppGlobal.Instance.getString(R.string.choose_student)
                if (subjectPosition == -1)
                    showSubjectError.value = AppGlobal.Instance.getString(R.string.choose_subject)
            }
        }
    }

    private fun generateBitmap(): Bitmap {
        val qrWriter = QRCodeWriter()
        val qrSize = QR_CODE_QUALITY
        val content =
            "${listOfStudents[studentPosition].id},${listOfSubjects[subjectPosition].id},${listOfSubjects[subjectPosition].typeOfWorkID};${UserConfig.getID()}"
        val bitMatrix = qrWriter.encode(content, BarcodeFormat.QR_CODE, qrSize, qrSize)
        val bitmap = Bitmap.createBitmap(qrSize, qrSize, Bitmap.Config.RGB_565)

        for (x in 0 until qrSize) {
            for (y in 0 until qrSize) {
                bitmap.setPixel(
                    x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
                )
            }
        }

        return bitmap
    }

    private fun saveQr(currentBitmap: Bitmap) {
        val picturePath = getOutMediaPath()

        try {
            val out = FileOutputStream(
                "$picturePath/${stringListOfSubjects.value[subjectPosition]}.png"
            )
            currentBitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()

            Toast.makeText(
                AppGlobal.Instance, "An error occurred while saving", Toast.LENGTH_SHORT
            ).show()
        } finally {
            Toast.makeText(AppGlobal.Instance, "QR code saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getOutMediaPath(): File {
        val pathName =
            "${AppGlobal.Instance.getExternalFilesDir(DIRECTORY_QR_CODES)}/${stringListOfStudents.value[studentPosition]}"
        val mediaStorage = File(pathName)

        if (!mediaStorage.exists()) {
            val dirExists = mediaStorage.mkdir()

            if (!dirExists) {
                Log.e("FileTest", "Folder not created")
            }
        }

        return mediaStorage
    }

    fun cleanStudentError() {
        showStudentError.value = null
    }

    fun cleanSubjectError() {
        showSubjectError.value = null
    }

    companion object {
        private const val QR_CODE_QUALITY = 256

        private const val DIRECTORY_QR_CODES = "QRCodes"
    }
}