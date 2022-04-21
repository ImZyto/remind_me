package com.example.remin.presenter

import com.example.remin.model.dataclass.Task
import com.example.remin.view.display.TodoListDisplay

class ToDoListPresenter(private val display: TodoListDisplay) {
    private var taskList = arrayListOf(
        Task(
            "Zadanie 1",
            true,
            "Musze isc do sklepu po zakupy tej",
            "2022-05-05",
            "poznan"
        ),
        Task(
            "Zadanie 2",
            false,
            "Musze isc do apteki po leki hej",
            "2022-05-02",
            "poznan"
        )
    )

    init {
        display.loadTaskList(taskList)
    }

}