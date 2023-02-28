package com.dralsoft.inventory.list.ui

import com.dralsoft.inventory.BaseTest
import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.core.data.ErrorResponse
import com.dralsoft.inventory.core.ui.SearchWidgetState
import com.dralsoft.inventory.list.data.local.mockInventoryResponse
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
    private lateinit var viewModel: ListInventoryViewModel
    val errorResponse = ErrorResponse()

    //sut = system under test
    @Before
    fun setUp() {
        listUseCases = mockk()
        viewModel = ListInventoryViewModel(listUseCases)
    }


    private fun standardListUseCaseResponse(isError: Boolean = false) {
        if (isError)
            coEvery { listUseCases.listInventoryUseCase() } returns flow { emit(Resource.Error(errorResponse)) }
        else
            coEvery { listUseCases.listInventoryUseCase() } returns flow { emit(Resource.Success(mockInventoryResponse())) }
    }

    @Test
    fun `on init should load the list inventory`(): Unit = runTest {
        standardListUseCaseResponse()
        val viewState = viewModel.viewState
        assertThat(viewState.value.data).isEqualTo(listOf<InventoryItem>())
        assertThat(true).isEqualTo(viewState.value.isLoading)

        viewState.testFlow(this) {
            assertThat(viewState.value.isLoading).isEqualTo(false)
            assertThat(viewState.value.data).isEqualTo(mockInventoryResponse().data)
        }
    }

    @Test
    fun `on list error should trigger single event`(): Unit = runTest {
        standardListUseCaseResponse(true)
        val viewState = viewModel.viewState
        assertThat(viewState.value.data).isEqualTo(listOf<InventoryItem>())
        assertThat(true).isEqualTo(viewState.value.isLoading)

        viewState.testFlow(this) {
            assertThat(viewState.value.isLoading).isEqualTo(false)
            assertThat(viewModel.submitSingleEvent(mockk())).isEqualTo(Unit)
        }
    }

    @Test
    fun `on call Intent OnCloseSearchClick should set searchState to Closed`(): Unit = runTest {
        val viewState = viewModel.viewState

        viewModel.submitIntent(ListIntent.OnCloseSearchClick)

        viewState.testFlow(this) {
            assertThat(viewState.value.searchState).isEqualTo(SearchWidgetState.CLOSED)
        }
    }

    @Test
    fun `on call Intent OnSearch should hide progress`(): Unit = runTest {
        val viewState = viewModel.viewState
        standardListUseCaseResponse()
        viewModel.submitIntent(ListIntent.OnSearch(""))

        viewState.testFlow(this) {
            assertThat(viewState.value.isLoading).isEqualTo(false)
        }
    }


    @Test
    fun `on call Intent OnSearchClicked should set searchState to Opened`(): Unit = runTest {
        val viewState = viewModel.viewState
        standardListUseCaseResponse()
        viewModel.submitIntent(ListIntent.OnSearchClicked)

        viewState.testFlow(this) {
            assertThat(viewState.value.searchState).isEqualTo(SearchWidgetState.OPENED)
        }
    }

    @Test
    fun `on call Intent OnTypeSearch should set searchText`(): Unit = runTest {
        val viewState = viewModel.viewState
        standardListUseCaseResponse()
        viewModel.submitIntent(ListIntent.OnTypeSearch("test"))

        viewState.testFlow(this) {
            assertThat(viewState.value.searchText).isEqualTo("test")
        }
    }


    @Test
    fun `on call Intent InventoryClick should trigger single event`(): Unit = runTest {
        val viewState = viewModel.viewState
        standardListUseCaseResponse()
        viewModel.submitIntent(ListIntent.InventoryClick(3))

        viewState.testFlow(this) {
            assertThat(viewState.value.isLoading).isEqualTo(false)
            assertThat(viewModel.submitSingleEvent(mockk())).isEqualTo(Unit)
        }
    }

    @Test
    fun `on call Intent AddInventory should trigger single event`(): Unit = runTest {
        val viewState = viewModel.viewState
        standardListUseCaseResponse()
        viewModel.submitIntent(ListIntent.AddInventory)

        viewState.testFlow(this) {
            assertThat(viewState.value.isLoading).isEqualTo(false)
            assertThat(viewModel.submitSingleEvent(mockk())).isEqualTo(Unit)
        }
    }

}

