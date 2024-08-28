package dev.uimodule.searchTodo.data.mapper

import dev.module.roomDb.modals.DBEntityToDo
import dev.root.baseKotlin.util.Mapper
import dev.uimodule.searchTodo.domain.modal.ModalToDo
import javax.inject.Inject

class ToDoDbMapper @Inject constructor(): Mapper<DBEntityToDo, ModalToDo> {

    override fun map(from: DBEntityToDo): ModalToDo = ModalToDo(from.id, from.description)
}