package dev.gabrielhenrique.remindertt.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.gabrielhenrique.remindertt.Application
import dev.gabrielhenrique.remindertt.utils.setHourAndMinute
import java.util.*

class MainViewModel: ViewModel() {

    val beginDate: MutableLiveData<Calendar> by lazy {
        val savedBeginDate = Application.get(Application.BEGIN_HOUR_KEY)
            ?: return@lazy MutableLiveData<Calendar>(Calendar.getInstance())

        return@lazy MutableLiveData<Calendar>(Calendar.getInstance().apply {
            setHourAndMinute(savedBeginDate)
        })
    }

    val endDate: MutableLiveData<Calendar> by lazy {
        val savedBeginDate = Application.get(Application.END_HOUR_KEY)
            ?: return@lazy MutableLiveData<Calendar>(Calendar.getInstance())

        return@lazy MutableLiveData<Calendar>(Calendar.getInstance().apply {
            setHourAndMinute(savedBeginDate)
        })
    }
}