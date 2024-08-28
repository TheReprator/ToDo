package dev.uimodule.createTodo

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.root.baseKotlin.extensions.computationalBlock
import dev.root.baseKotlin.extensions.mainBlock
import dev.root.baseKotlin.util.AppCoroutineDispatchers
import dev.uimodule.createTodo.domain.usecase.CreateToDoUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val CONSTANT_ERROR = "error"
const val CONSTANT_3SECONDS = 3_000L

@HiltViewModel
class CreateTodoViewModel @Inject constructor(
    private val useCase: CreateToDoUseCase,
    private val dispatchers: AppCoroutineDispatchers,
) : ViewModel() {

    private val _createToDo = MutableStateFlow(TextFieldValue())
    val createToDo = _createToDo.asStateFlow()

    private val _showLoader = MutableStateFlow(false)
    val showLoader = _showLoader.asStateFlow()

    private val _isError = Channel<String>(capacity = Channel.BUFFERED)
    val isError: Flow<String>
        get() = _isError.receiveAsFlow()

    private val _shouldNavigateBack = Channel<Boolean>(capacity = Channel.BUFFERED)
    val shouldNavigateBack: Flow<Boolean>
        get() = _shouldNavigateBack.receiveAsFlow()

    fun updateToDo(toDo: TextFieldValue) {
        _createToDo.value = toDo
    }

    fun submitToDo() {
        try {

            val input = _createToDo.value.text
            if (!validateField(input)) {
                return
            }

            _showLoader.value = true

            viewModelScope.computationalBlock(dispatchers) {
                useCase(input)
                withContext(dispatchers.main) {
                    delay(CONSTANT_3SECONDS)
                    _createToDo.value = TextFieldValue()
                    _showLoader.value = false
                    _shouldNavigateBack.send(true)
                }
            }
        } catch (e: Exception) {
            viewModelScope.mainBlock(dispatchers) {
                _isError.send(e.localizedMessage.orEmpty())
                delay(CONSTANT_3SECONDS)
                _shouldNavigateBack.send(true)
            }
        }
    }

    fun resetToDoText() {
        _createToDo.value = TextFieldValue()
    }

    fun validateField(input: String?): Boolean {
        if (true == input?.trim()?.isEmpty())
            return false


        if (CONSTANT_ERROR.equals(input?.lowercase(), true))
            throw Exception("Custom validation error")

        return true
    }
}