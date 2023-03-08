package com.dralsoft.inventory.list.ui

import com.dralsoft.inventory.BaseTest
import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.core.data.ErrorResponse
import com.dralsoft.inventory.core.ui.SearchWidgetState
import com.dralsoft.inventory.core.data.local.mockInventoryResponse
import com.dralsoft.inventory.list.data.response.InventoryItem
import com.dralsoft.inventory.list.domain.ListUseCases
import com.dralsoft.inventory.testFlow
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


@OptIn(ExperimentalCoroutinesApi::class)
class ListInventoryViewModelTest : BaseTest() {

    lateinit var listUseCases: ListUseCases
    private lateinit var sut: ListInventoryViewModel
    val errorResponse = ErrorResponse()

    @Before
    fun setUp() {
        listUseCases = mockk()
        sut = ListInventoryViewModel(listUseCases)
    }

    sealed class ListUseCaseResponses {
        object Success : ListUseCaseResponses()
        object Error : ListUseCaseResponses()
        object Empty : ListUseCaseResponses()
    }


    private fun standardListUseCaseResponse(responseType: ListUseCaseResponses = ListUseCaseResponses.Success) {
        when (responseType) {
            ListUseCaseResponses.Success -> coEvery { listUseCases.listInventoryUseCase() } returns flow {
                emit(
                    Resource.Success(
                        mockInventoryResponse()
                    )
                )
            }
            ListUseCaseResponses.Error -> coEvery { listUseCases.listInventoryUseCase() } returns flow {
                emit(
                    Resource.Error(
                        errorResponse
                    )
                )
            }
            ListUseCaseResponses.Empty -> coEvery { listUseCases.listInventoryUseCase() } returns flow {
                emit(
                    Resource.Success(
                        mockInventoryResponse().copy(data = null)
                    )
                )
            }
        }
    }

    @Test
    fun `on init should load the list inventory`(): Unit = runTest {
        standardListUseCaseResponse()
        val viewState = sut.viewState
        assertThat(viewState.value.data).isEqualTo(listOf<InventoryItem>())
        assertThat(true).isEqualTo(viewState.value.isLoading)

        viewState.testFlow(this) {
            assertThat(viewState.value.isLoading).isEqualTo(false)
            assertThat(viewState.value.data).isEqualTo(mockInventoryResponse().data)
        }
    }

    @Test
    fun `on list error should trigger single event`(): Unit = runTest {
        standardListUseCaseResponse(ListUseCaseResponses.Error)
        val viewState = sut.viewState
        assertThat(viewState.value.data).isEqualTo(listOf<InventoryItem>())
        assertThat(true).isEqualTo(viewState.value.isLoading)

        viewState.testFlow(this) {
            assertThat(viewState.value.isLoading).isEqualTo(false)
            assertThat(sut.submitSingleEvent(mockk())).isEqualTo(Unit)
        }
    }

    @Test
    fun `on call Intent OnCloseSearchClick should set searchState to Closed`(): Unit = runTest {
        val viewState = sut.viewState
        standardListUseCaseResponse()
        sut.submitIntent(ListIntent.OnCloseSearchClick)

        viewState.testFlow(this) {
            assertThat(viewState.value.searchState).isEqualTo(SearchWidgetState.CLOSED)
        }
    }

    @Test
    fun `on call Intent OnSearch should hide progress`(): Unit = runTest {
        val viewState = sut.viewState
        standardListUseCaseResponse()
        sut.submitIntent(ListIntent.OnSearch(""))

        viewState.testFlow(this) {
            assertThat(viewState.value.isLoading).isEqualTo(false)
        }
    }


    @Test
    fun `on call Intent OnSearchClicked should set searchState to Opened`(): Unit = runTest {
        val viewState = sut.viewState
        standardListUseCaseResponse()
        sut.submitIntent(ListIntent.OnSearchClicked)

        viewState.testFlow(this) {
            assertThat(viewState.value.searchState).isEqualTo(SearchWidgetState.OPENED)
        }
    }

    @Test
    fun `on call Intent OnTypeSearch should set searchText`(): Unit = runTest {
        val viewState = sut.viewState
        standardListUseCaseResponse()
        sut.submitIntent(ListIntent.OnTypeSearch("test"))

        viewState.testFlow(this) {
            assertThat(viewState.value.searchText).isEqualTo("test")
        }
    }


    @Test
    fun `on call Intent InventoryClick should trigger single event`(): Unit = runTest {
        val viewState = sut.viewState
        standardListUseCaseResponse()
        sut.submitIntent(ListIntent.InventoryClick(3))

        viewState.testFlow(this) {
            assertThat(viewState.value.isLoading).isEqualTo(false)
            assertThat(sut.submitSingleEvent(mockk())).isEqualTo(Unit)
        }
    }

    @Test
    fun `on call Intent AddInventory should trigger single event`(): Unit = runTest {
        val viewState = sut.viewState
        standardListUseCaseResponse()
        sut.submitIntent(ListIntent.AddInventory)

        viewState.testFlow(this) {
            assertThat(viewState.value.isLoading).isEqualTo(false)
            assertThat(sut.submitSingleEvent(mockk())).isEqualTo(Unit)
        }
    }

    @Test
    fun `on init when usecase returns null state empty should be true`(): Unit = runTest {
        val viewState = sut.viewState
        standardListUseCaseResponse(ListUseCaseResponses.Empty)

        viewState.testFlow(this) {
            assertThat(viewState.value.isLoading).isEqualTo(false)
            assertThat(viewState.value.data.isEmpty()).isEqualTo(true)
        }
    }

}

