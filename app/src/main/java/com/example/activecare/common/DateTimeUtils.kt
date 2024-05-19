package com.example.activecare.common

import com.example.activecare.dataclasses.Limitation
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

val DateTimeParser: DateTimeFormatter = DateTimeFormatter.ofPattern(
    "uuuu-MM-dd'T'HH:mm:ss",
    Locale.ENGLISH
)

fun simpleDateTimeParser(date: Calendar):String{
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).format(date.time)
}

fun simpleStringParser(dateString: String):Calendar{
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val date = format.parse(dateString)
    return Calendar.getInstance().apply {
        time=date!!
    }
}

fun calculateEndDate(limitation: Limitation): String{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val startDate = LocalDateTime.parse(limitation.date, formatter)
    return when(limitation.deltatype){
        "day" -> startDate.minusDays(limitation.date_offset.toLong())
        "week" -> startDate.minusWeeks(limitation.date_offset.toLong())
        "month" -> startDate.minusMonths(limitation.date_offset.toLong())
        "year" -> startDate.minusYears(limitation.date_offset.toLong())
        else -> startDate
    }.format(formatter)
}