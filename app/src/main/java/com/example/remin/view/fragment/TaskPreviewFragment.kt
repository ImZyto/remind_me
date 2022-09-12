package com.example.remin.view.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.remin.R
import com.example.remin.model.Constants
import com.example.remin.presenter.fragment.TaskPreviewPresenter
import com.example.remin.view.display.TaskPreviewDisplay
import kotlinx.android.synthetic.main.fragment_task_preview.*

class TaskPreviewFragment : Fragment(), TaskPreviewDisplay {

    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var presenter: TaskPreviewPresenter

    private lateinit var navigation: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_task_preview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigation = Navigation.findNavController(view)

        TaskPreviewPresenter(this, arguments?.getParcelable(Constants.EXTRA_TASK)!!)
    }

    override fun getFragmentContext(): Context = requireContext()

    override fun initDatePicker(clickListener: (Int, Int, Int) -> Unit) {
        datePickerDialog = DatePickerDialog(requireContext())
        datePickerDialog.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            clickListener(year, monthOfYear, dayOfMonth)
        }
    }

    override fun showDatePicker() = datePickerDialog.show()

    override fun setTaskDate(date: String) {
        taskDateTv.text = date
    }

    override fun setTaskPriority(textId: Int) {
        //taskPriorityTv.text = getString(textId)
    }

    override fun getName(): String = taskNameEt.text.toString()

    override fun getDescription(): String = taskDescriptionEt.text.toString()

    override fun getLocalization(): String = taskLocalizationEt.text.toString()

    override fun setName(text: String) = taskNameEt.setText(text)

    override fun setDescription(text: String) = taskDescriptionEt.setText(text)

    override fun setLocalization(text: String) = taskLocalizationEt.setText(text)

    override fun setOnDateBtnClickListener(clickListener: () -> Unit) {
        taskDateTv.setOnClickListener { clickListener() }
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