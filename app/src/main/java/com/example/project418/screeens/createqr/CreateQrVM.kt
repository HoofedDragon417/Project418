package com.example.project418.screens.createqr

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.project418.common.BaseVM
import com.example.project418.models.Student
import com.example.project418.models.Subject
import com.example.project418.storage.DataBaseHelper
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class CreateQrVM : BaseVM() {
    private var listOfStudents = listOf<Student>()
    private var listOfSubjects = listOf<Subject>()

    val stringListOfStudents = MutableStateFlow(listOf<String>())
    val stringListOfSubjects = MutableStateFlow(listOf<String>())

    private var studentPosition = -1
    private var subjectPosition = -1

    fun getLists(context: Context) {
        viewModelScope.launch {
            listOfStudents = DataBaseHelper(context).getListOfStudents()
            listOfSubjects = DataBaseHelper(context).getListOfSubjects()

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

    fun generateQr(context: Context) {
        viewModelScope.launch {
            if (studentPosition != -1 && subjectPosition != -1) {
                val qrBitmap = generateBitmap()

                saveQr(qrBitmap, context)
            } else
                Log.e(TAG, "Empty values")
        }
    }

    private fun generateBitmap(): Bitmap {
        val qrWriter = QRCodeWriter()
        val qrSize = QR_CODE_QUALITY
        val content =
            "${listOfStudents[studentPosition].id},${listOfSubjects[subjectPosition].id}," + "${listOfSubjects[subjectPosition].typeOfWorkID}"
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

    private fun saveQr(currentBitmap: Bitmap, context: Context) {
        val picturePath = getOutMediaPath(context)

        try {
            val out = FileOutputStream(
                "$picturePath/${stringListOfSubjects.value[subjectPosition]}.png"
            )
            currentBitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()

            Toast.makeText(
                context, "An error occurred while saving", Toast.LENGTH_SHORT
            ).show()
        } finally {
            Toast.makeText(context, "QR code saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getOutMediaPath(context: Context): File {
        val pathName =
            "${context.getExternalFilesDir(DIRECTORY_QR_CODES)}/${stringListOfStudents.value[studentPosition]}"
        val mediaStorage = File(pathName)

        if (!mediaStorage.exists()) {
            val dirExists = mediaStorage.mkdir()

            if (!dirExists) {
                Log.e("FileTest", "Folder not created")
            }
        }

        return mediaStorage
    }

    companion object {
        private const val QR_CODE_QUALITY = 256

        private const val DIRECTORY_QR_CODES = "QRCodes"

        private const val TAG = "QR generator"
    }
}