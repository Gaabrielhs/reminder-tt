package dev.gabrielhenrique.remindertt.utils

import dev.gabrielhenrique.remindertt.Application
import java.util.*

infix fun Int.joinMinute(minute: Int) : String{
    val hours = if(this >= 10) this else "0$this"
    val minutes = if(minute >= 10) minute else "0$minute"
    return "$hours:$minutes"
}

fun Calendar.calculateEndTime(): Calendar {
    val anotherObj = this.clone() as Calendar
    return anotherObj.apply {
        this.add(Calendar.HOUR, Application.EXPEDIENT_DURATION)
    }
}

fun Calendar.toFormattedDate() : String {
    return this.get(Calendar.HOUR_OF_DAY) joinMinute this.get(Calendar.MINUTE)
}

fun Calendar.setHourAndMinute(hour: String) {
    this.set(Calendar.HOUR_OF_DAY, hour.split(":").first().toInt())
    this.set(Calendar.MINUTE, hour.split(":").last().toInt())
}