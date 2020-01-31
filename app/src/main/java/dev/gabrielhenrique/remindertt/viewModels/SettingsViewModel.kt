package dev.gabrielhenrique.remindertt.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SettingsViewModel: ViewModel() {

    val beginDate = MutableLiveData<Calendar>(Calendar.getInstance())
    val endDate = MutableLiveData<Calendar>(Calendar.getInstance())
}