package com.doubleclick.doctorapp.android.utils

import java.util.*


fun getYears(): List<String> {
    val year: MutableList<String> = mutableListOf()
    for (i in 1940..Calendar.getInstance().get(Calendar.YEAR)) {
        year.add(i.toString())
    }
    return year;
}

fun getMonth(): List<String> {
    val year: MutableList<String> = mutableListOf()
    for (i in 1..12) {
        year.add(i.toString())
    }
    return year;
}

fun getDays(): List<String> {
    val year: MutableList<String> = mutableListOf()
    for (i in 1..31) {
        year.add(i.toString())
    }
    return year;
}