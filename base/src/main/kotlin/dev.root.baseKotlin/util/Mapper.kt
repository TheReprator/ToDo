package dev.root.baseKotlin.util

fun interface Mapper<in InputModal, out OutputModal> {
     fun map(from: InputModal): OutputModal
}

interface MapperToFrom<InputModal, OutputModal> {
    suspend fun mapTo(from: InputModal): OutputModal
    suspend fun mapFrom(from: OutputModal): InputModal
}

fun <F, T> Mapper<F, T>.toListMapper(): suspend (List<F>) -> List<T> {
    return { list -> list.map { item -> map(item) } }
}

inline fun <Input, Output> Mapper<Input, Output>.mapAll(collection: Collection<Input>) =
    collection.map { map(it) }