package com.example.project418.screens.tabs.di

import com.example.project418.modules.journalModule
import com.example.project418.modules.studentModule
import com.example.project418.screens.tabs.subjects.di.subjectModule
import com.example.project418.screens.tabs.teachers.di.teacherModule
import org.koin.dsl.module

val tabModule = module {
    includes(
        journalModule,
        studentModule,
        subjectModule,
        teacherModule
    )
}