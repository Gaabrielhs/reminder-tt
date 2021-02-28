package dev.gabrielhenrique.remindertt.ui

import android.app.TimePickerDialog
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dev.gabrielhenrique.remindertt.Application
import dev.gabrielhenrique.remindertt.R
import dev.gabrielhenrique.remindertt.utils.calculateEndTime
import dev.gabrielhenrique.remindertt.utils.toFormattedDate
import dev.gabrielhenrique.remindertt.viewModels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*

class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val backgroundDrawable by lazy {
        BackgroundDrawable(context!!,resources.displayMetrics.widthPixels / 2f, resources.displayMetrics.heightPixels / 2f)
    }
    private val defaultDrawable by lazy {
        context!!.getDrawable(R.drawable.main_background_default)
    }

    private val transitionBackground by lazy {
        TransitionDrawable(arrayOf(
            defaultDrawable,
            backgroundDrawable
        ))
    }


    private val observerBegin = Observer<Calendar> {
        val textTime = it.toFormattedDate()

        val timeEnd = it.calculateEndTime()
        mainViewModel.endDate.postValue(timeEnd)

        settings_time_begin_text.text = textTime
        Application.save(Application.BEGIN_HOUR_KEY, textTime)
    }

    private val observerEnd = Observer<Calendar> {
        val textTime = it.toFormattedDate()
        settings_time_end_text.text = textTime
        Application.save(Application.END_HOUR_KEY, textTime)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        registerListeners()
        registerObservers()
        main_frame.background = transitionBackground
    }


    override fun onDestroy() {
        unregisterObservers()
        super.onDestroy()
    }

    private fun registerListeners(){
        settings_time_begin_card.setOnClickListener {
            openTimePicker(TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                mainViewModel.beginDate.value = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                }
            })
        }
        settings_time_end_card.setOnClickListener {
            openTimePicker(TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                mainViewModel.endDate.value = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                }
            })
        }

        save_fab.setOnClickListener {
            Calendar.getInstance().also {
                mainViewModel.beginDate.postValue(it)
                Application.save(Application.BEGIN_HOUR_KEY, it.toFormattedDate())
            }
        }

        main_frame.setOnLongClickListener {
            backgroundDrawable.expand()
            return@setOnLongClickListener true
        }

        main_frame.setOnTouchListener { v, event ->
            backgroundDrawable.setCoordinates(event.x, event.y)
            transitionBackground.invalidateSelf()

            when(event.actionMasked){
                MotionEvent.ACTION_DOWN -> {
                    transitionBackground.startTransition(1000)
                }
                MotionEvent.ACTION_UP -> {
                    transitionBackground.reverseTransition(1000)
                }
            }
            return@setOnTouchListener false
        }

    }

    private fun registerObservers() {
        mainViewModel.beginDate.observe(viewLifecycleOwner, observerBegin)

        mainViewModel.endDate.observe(viewLifecycleOwner, observerEnd)
    }

    private fun unregisterObservers(){
        mainViewModel.beginDate.removeObserver(observerBegin)
        mainViewModel.endDate.removeObserver(observerEnd)
    }

    private fun openTimePicker(listener: TimePickerDialog.OnTimeSetListener){
        TimePickerFragment(listener).show(parentFragmentManager, "picker")
    }
}
