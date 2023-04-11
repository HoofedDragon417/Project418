package com.example.project418.models

data class Journal(
    val id: Int,
    val title: String,
    val student: String,
    val subject: String,
    val typeOfWork: String,
    val teacher: String,
    val registrationData: Long
)