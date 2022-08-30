package com.example.remin.view.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remin.R
import com.example.remin.model.dataclass.Task
import com.example.remin.presenter.ToDoListPresenter
import com.example.remin.view.adapter.TaskAdapter
import com.example.remin.view.display.TodoListDisplay
import kotlinx.android.synthetic.main.fragment_create_task.*
import kotlinx.android.synthetic.main.fragment_to_do_list.*

class ToDoListFragment : Fragment(), TodoListDisplay {

    private lateinit var datePickerDialog: DatePickerDialog

    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_to_do_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ToDoListPresenter(this)
    }

    override fun loadTaskList(taskList: List<Task>) {
        adapter = TaskAdapter(requireContext(), taskList)
        taskListRv.layoutManager = LinearLayoutManager(requireContext())
        taskListRv.adapter = adapter

    }

    override fun setOnCheckedChangeListener(checkListener: (Boolean) -> Unit) {
        toggleSw.setOnCheckedChangeListener { _, isChecked ->
            checkListener(isChecked)
        }
    }

    override fun setStatusFieldsVisibility(show: Boolean) {
        //TODO Implement layout status part
    }

    override fun initDatePicker(clickListener: (Int, Int, Int) -> Unit) {
        datePickerDialog = DatePickerDialog(requireContext())
        datePickerDialog.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            clickListener(year, monthOfYear, dayOfMonth)
        }
    }

    override fun showDatePicker() = datePickerDialog.show()

    override fun setOnDateBtnClickListener(clickListener: () -> Unit) {
        taskDateBtn.setOnClickListener { clickListener() }
    }

    override fun navigateToAddTaskFragment() =
        Navigation.findNavController(requireView())
            .navigate(R.id.action_toDoList_to_createTaskFragment)

    override fun setTaskDate(date: String) {
        taskDateTv.text = date
    }

    override fun getFragmentContext() = requireContext()
}