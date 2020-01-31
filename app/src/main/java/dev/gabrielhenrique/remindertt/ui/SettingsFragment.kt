package dev.gabrielhenrique.remindertt.ui


import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.lifecycle.Observer
import dev.gabrielhenrique.remindertt.Application
import dev.gabrielhenrique.remindertt.R
import dev.gabrielhenrique.remindertt.utils.joinMinute
import dev.gabrielhenrique.remindertt.viewModels.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*

class SettingsFragment : Fragment(){

    private val settingsViewModel: SettingsViewModel by viewModels()

    private val observerBegin = Observer<Calendar> {
        settings_time_begin_text.text = it.get(Calendar.HOUR_OF_DAY) joinMinute it.get(Calendar.MINUTE)
    }

    private val observerEnd = Observer<Calendar> {
        settings_time_end_text.text = it.get(Calendar.HOUR_OF_DAY) joinMinute it.get(Calendar.MINUTE)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        registerListeners()
        registerObservers()
    }


    override fun onDestroy() {
        unregisterObservers()
        super.onDestroy()
    }

    private fun registerListeners(){
        settings_time_begin_card.setOnClickListener {
            openTimePicker(TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                settingsViewModel.beginDate.value = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                }
            })
        }
        settings_time_end_card.setOnClickListener {
            openTimePicker(TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                settingsViewModel.endDate.value = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                }
            })
        }

        save_fab.setOnClickListener {
            Application.notificar(context!!)
        }
    }

    private fun registerObservers() {
        settingsViewModel.beginDate.observe(viewLifecycleOwner, observerBegin)

        settingsViewModel.endDate.observe(viewLifecycleOwner, observerEnd)
    }

    private fun unregisterObservers(){
        settingsViewModel.beginDate.removeObserver(observerBegin)
        settingsViewModel.endDate.removeObserver(observerEnd)
    }

    private fun openTimePicker(listener: TimePickerDialog.OnTimeSetListener){
        TimePickerFragment(listener).show(parentFragmentManager, "picker")
    }
}

