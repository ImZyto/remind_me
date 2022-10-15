package com.example.remin.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remin.R
import com.example.remin.model.Constants
import com.example.remin.model.dataclass.Task
import com.example.remin.presenter.fragment.ToDoListPresenter
import com.example.remin.view.adapter.TaskAdapter
import com.example.remin.view.display.TodoListDisplay
import kotlinx.android.synthetic.main.fragment_to_do_list.*

class ToDoListFragment : Fragment(), TodoListDisplay {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_to_do_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ToDoListPresenter(this)
    }

    override fun loadTaskList(taskList: List<Task>, itemClickListener: (Task) -> Unit) {
        taskListRv.layoutManager = LinearLayoutManager(requireContext())
        taskListRv.adapter = TaskAdapter(requireContext(), taskList, itemClickListener)
    }

    override fun navigateToEditTaskFragment(task: Task) {
        val bundle = Bundle().apply { putParcelable(Constants.EXTRA_TASK, task) }
        Navigation.findNavController(requireView())
            .navigate(R.id.action_toDoListFragment_to_taskPreviewFragment, bundle)
    }

    override fun getFragmentContext() = requireContext()


}