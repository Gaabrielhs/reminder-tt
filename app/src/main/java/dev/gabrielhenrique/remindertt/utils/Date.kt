package dev.gabrielhenrique.remindertt.utils

import java.util.*


infix fun Int.joinMinute(minute: Int) : String{
    val hours = if(this >= 10) this else "0$this"
    val minutes = if(minute >= 10) minute else "0$minute"
    return "$hours:$minutes"
}