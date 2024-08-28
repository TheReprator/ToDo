package dev.uimodule.createTodo.data.mapper

import dev.module.roomDb.modals.DBEntityToDo
import dev.root.baseKotlin.util.Mapper
import javax.inject.Inject

class CreateToDoDbMapper @Inject constructor(): Mapper<String, DBEntityToDo> {

    override fun map(from: String): DBEntityToDo {
       return DBEntityToDo(description = from)
    }
}