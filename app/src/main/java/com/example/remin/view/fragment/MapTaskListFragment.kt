package com.example.remin.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remin.R
import com.example.remin.model.dataclass.Task
import com.example.remin.presenter.MapTaskListPresenter
import com.example.remin.view.adapter.MapTaskListAdapter
import com.example.remin.view.adapter.TaskAdapter
import com.example.remin.view.display.MapTaskListDisplay
import kotlinx.android.synthetic.main.fragment_map_task_list.*

class MapTaskListFragment : Fragment(), MapTaskListDisplay {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_map_task_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MapTaskListPresenter(this)
    }

    override fun loadTaskList(taskList: List<Task>) {
        mapTaskListRv.layoutManager = LinearLayoutManager(activity!!.applicationContext, RecyclerView.HORIZONTAL, false)
        mapTaskListRv.adapter = MapTaskListAdapter(requireContext(), taskList)
    }

    override fun getFragmentContext() = requireContext()
}