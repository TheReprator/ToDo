package dev.uimodule.todoList.data.mapper

import dev.module.roomDb.modals.DBEntityToDo
import dev.root.baseKotlin.util.Mapper
import dev.uimodule.todoList.domain.modal.ModalTodo
import javax.inject.Inject

class ToDoDbMapper @Inject constructor(): Mapper<DBEntityToDo, ModalTodo> {

    override fun map(from: DBEntityToDo): ModalTodo = ModalTodo(from.id, from.description)
}