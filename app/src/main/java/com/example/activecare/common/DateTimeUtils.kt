package com.example.activecare.common

import com.example.activecare.common.dataclasses.Limitation
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

val DateTimeParser: DateTimeFormatter = DateTimeFormatter.ofPattern(
    "uuuu-MM-dd'T'HH:mm:ss",
    Locale.ENGLISH
)

val EndDateParser: DateTimeFormatter = DateTimeFormatter.ofPattern(
    "uuuu-MM-dd'T23:59:59'",
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

fun millisToDateStamp(millis: Long):String{
    val instant = Instant.ofEpochMilli(millis)
    val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return DateTimeParser.format(date)
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

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(Date())
}

fun getPrevDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(LocalDate.now().minusDays(1))
}