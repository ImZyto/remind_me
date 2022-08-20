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
import com.example.remin.model.Constants.EXTRA_TASK_ID
import com.example.remin.presenter.CreateTaskPresenter
import com.example.remin.view.display.CreateTaskDisplay
import kotlinx.android.synthetic.main.fragment_create_task.*


class CreateTaskFragment : Fragment(), CreateTaskDisplay {

    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var presenter: CreateTaskPresenter

    private lateinit var navigation: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_create_task, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigation = Navigation.findNavController(view)
        presenter = CreateTaskPresenter(this)
    }

    override fun getFragmentContext(): Context = requireContext()

    override fun initView() {
        if (arguments?.getInt(EXTRA_TASK_ID) != null)
            presenter.getTask(id)
    }

    override fun initDatePicker(clickListener: (Int, Int, Int) -> Unit) {
        datePickerDialog = DatePickerDialog(requireContext())
        datePickerDialog.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            clickListener(year, monthOfYear, dayOfMonth)
        }
    }

    override fun showDatePicker() = datePickerDialog.show()


    override fun setTaskDate(date: String) {
        taskDateBtn.text = date
    }

    override fun setTaskPriority(textId: Int) {
        taskPriorityTv.text = getString(textId)
    }

    override fun getName(): String = taskNameEt.text.toString()

    override fun getDescription(): String = taskDescriptionEt.text.toString()


    override fun setOnDateBtnClickListener(clickListener: () -> Unit) {
        taskDateBtn.setOnClickListener { clickListener() }
    }

    override fun setOnPrioritySwCheckListener(checkListener: (Boolean) -> Unit) {
        taskPrioritySw.setOnCheckedChangeListener { _, isChecked ->
            checkListener(isChecked)
        }
    }

    override fun setOnAddClickListener(clickListener: () -> Unit) {
        addBtn.setOnClickListener { clickListener() }
    }

    override fun navigateBack() {
        navigation.navigateUp()
    }
}