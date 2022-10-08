package com.example.remin.view.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.remin.R
import com.example.remin.model.Constants.EXTRA_TASK_LOCALIZATION
import com.example.remin.presenter.fragment.CreateTaskPresenter
import com.example.remin.view.display.CreateTaskDisplay
import kotlinx.android.synthetic.main.fragment_create_task.*


class CreateTaskFragment : Fragment(), CreateTaskDisplay {

    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var timePickerDialog: TimePickerDialog

    private lateinit var navigation: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_create_task, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigation = Navigation.findNavController(view)
        CreateTaskPresenter(this)
        setLocalization(arguments?.getString(EXTRA_TASK_LOCALIZATION) ?: "")
    }

    override fun getFragmentContext(): Context = requireContext()

    override fun initDatePicker(clickListener: (Int, Int, Int) -> Unit) {
        datePickerDialog = DatePickerDialog(requireContext())
        datePickerDialog.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            clickListener(year, monthOfYear, dayOfMonth)
        }
    }

    override fun showDatePicker() = datePickerDialog.show()

    override fun initTimePicker(clickListener: (Int, Int) -> Unit) {
        val timeSetListener =
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                clickListener(
                    hourOfDay,
                    minute
                )
            }

        timePickerDialog = TimePickerDialog(
            requireContext(),
            timeSetListener,
            12,
            10,
            false
        )
    }

    override fun showTimePicker() = timePickerDialog.show()

    override fun setTaskDate(date: String) {
        taskDateTv.text = date
    }

    override fun setTaskTime(time: String) {
        taskTimeTv.text = time
    }

    override fun setTaskPriority(textId: Int) {
        //taskPriorityTv.text = getString(textId)
    }

    override fun getName(): String = taskNameEt.text.toString()

    override fun getDescription(): String = taskDescriptionEt.text.toString()

    override fun getLocalization(): String = taskLocalizationEt.text.toString()

    override fun setLocalization(text: String) = taskLocalizationEt.setText(text)

    override fun setOnDateBtnClickListener(clickListener: () -> Unit) {
        taskDateTv.setOnClickListener { clickListener() }
    }

    override fun setOnTimeBtnClickListener(clickListener: () -> Unit) {
        taskTimeTv.setOnClickListener { clickListener() }
    }

    override fun setOnPrioritySwCheckListener(checkListener: (Boolean) -> Unit) {
        //taskPrioritySw.setOnCheckedChangeListener { _, isChecked ->
        //    checkListener(isChecked)
        //}
    }

    override fun setOnSubmitButtonClickListener(clickListener: () -> Unit) {
        submitBtn.setOnClickListener { clickListener() }
    }

    override fun navigateBack() {
        navigation.navigateUp()
    }
}