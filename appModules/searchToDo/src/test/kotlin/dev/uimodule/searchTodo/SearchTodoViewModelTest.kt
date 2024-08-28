package dev.uimodule.searchTodo

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.google.common.truth.Truth
import dev.uimodule.searchTodo.domain.modal.ModalToDo
import dev.uimodule.searchTodo.domain.usecase.SearchToDoUseCase
import dev.uimodule.searchTodo.fixtures.FakeLocalDataSource
import dev.uimodule.searchTodo.util.MainDispatcherRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.time.Duration.Companion.seconds

@RunWith(JUnit4::class)
class SearchTodoViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var searchUseCase: SearchToDoUseCase

    private val savedStateHandle: SavedStateHandle = SavedStateHandle()

    lateinit var searchViewModel: SearchTodoViewModel

    @Before
    fun setUp() {
        searchUseCase = SearchToDoUseCase(FakeLocalDataSource())
        searchViewModel = SearchTodoViewModel(searchUseCase, savedStateHandle)
    }

    @Test
    fun `get paginated data for a particular search`() = runTest(timeout = 5.seconds) {

        searchViewModel.updateSearch("one")

        val items: Flow<PagingData<ModalToDo>> = searchViewModel.searchResults
        val result = items.asSnapshot()

        Truth.assertThat(result).isNotEmpty()
        Truth.assertThat(result.size).isEqualTo(1)
        Truth.assertThat(result.first().description).isEqualTo("one item")
    }
}
