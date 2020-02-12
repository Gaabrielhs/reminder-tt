package dev.gabrielhenrique.remindertt

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService


object Application{
    val SHARED_PREF_KEY = "REMINDERTT"
    val BEGIN_HOUR_KEY = "BEGIN_HOUR"
    val END_HOUR_KEY = "END_HOUR"
    val EXPEDIENT_DURATION = 9

    val LAST_NOTIFICATION_ID_KEY = "LAST_NOTIFICATION_ID"

    var notificationId: Int = 0

    lateinit var sharedPreferences: SharedPreferences

    object ReminderNotificationChannel {
        val ID = "RTT"
        val name = "Reminder Default"
        val descriptionText = "Notification channel for daily reminders"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
    }


    fun initialize(context: Context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
        notificationId = sharedPreferences.getInt(LAST_NOTIFICATION_ID_KEY, 0)
        initializeNotificationCenter(context)
    }

    fun notificar(context: Context) {
        val horas = sharedPreferences.getInt(LAST_NOTIFICATION_ID_KEY, 0)
        val builder = NotificationCompat.Builder(context, ReminderNotificationChannel.ID).apply {
            setContentTitle("TT CI&T")
            setContentText("$horas horas para o expediente")
            setSmallIcon(R.drawable.ic_access_time_primary_24dp)
        }
        notificationId++
        NotificationManagerCompat.from(context).notify(notificationId,builder.build())
        sharedPreferences.edit().putInt(LAST_NOTIFICATION_ID_KEY, notificationId).apply()
    }

    fun save(key: String, value: String) : Boolean {
        return sharedPreferences.edit().putString(key, value).commit()
    }

    fun get(key: String) : String? {
        return sharedPreferences.getString(key, null)
    }

    private fun initializeNotificationCenter(context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(ReminderNotificationChannel.ID, ReminderNotificationChannel.name, ReminderNotificationChannel.importance).apply {
                description = ReminderNotificationChannel.descriptionText
            }
            // Register the channel with the system
            getSystemService(context, NotificationManager::class.java)?.also {
                it.createNotificationChannel(channel)
            }
        }
    }

}