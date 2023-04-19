package com.example.project418.screens.completeregistration.di

import com.example.project418.screens.completeregistration.screens.coursework.di.completeCoursework
import com.example.project418.screens.completeregistration.screens.testwork.di.completeTestwork
import org.koin.dsl.module

val completeRegistrationModule = module {
    includes(completeCoursework, completeTestwork)
}