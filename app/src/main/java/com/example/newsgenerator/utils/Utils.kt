package com.example.newsgenerator.utils

import android.util.Log
import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "9e7a31c9a5f84e69a921a8c16eca59de"

fun getCountry() = Locale.getDefault().country.toLowerCase()

fun prettyDateTimeFormat(date: String): String {
    val prettyDateTime = PrettyTime(Locale(getCountry()))
    var newDateFormat = ""
    try {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH)
        val simpleDate = simpleDateFormat.parse(date)
        newDateFormat = prettyDateTime.format(simpleDate)
    } catch (e: ParseException) {
        Log.i("ERROR_TAG", e.message ?: "Error with date parsing")
    }

    return newDateFormat
}