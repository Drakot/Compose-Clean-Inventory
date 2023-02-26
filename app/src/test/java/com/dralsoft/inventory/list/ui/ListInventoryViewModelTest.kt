package com.dralsoft.inventory.list.ui

import app.cash.turbine.test
import com.dralsoft.inventory.BaseTest
import com.dralsoft.inventory.MockRepository
import com.dralsoft.inventory.list.data.local.mockInventoryResponse
import com.dralsoft.inventory.list.data.response.InventoryItem
import com.dralsoft.inventory.list.domain.DeleteInventoryUseCase
import com.dralsoft.inventory.list.domain.ListInventoryUseCase
import com.dralsoft.inventory.list.domain.ListUseCases
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class ListInventoryViewModelTest : BaseTest() {
    lateinit var listUseCases: ListUseCases
    private lateinit var viewModel: ListInventoryViewModel

    val successResponse = mockInventoryResponse()

    @Before
    fun setUp() {
        listUseCases = ListUseCases(
            listInventoryUseCase = ListInventoryUseCase(MockRepository()),
            deleteInventoryUseCase = mock(DeleteInventoryUseCase::class.java)
        )
        viewModel = ListInventoryViewModel(listUseCases)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `on init should load the list inventory`(): Unit = runTest {
        val viewState = viewModel.viewState
        assertThat(viewState.value.data).isEqualTo(listOf<InventoryItem>())
        assertThat(true).isEqualTo(viewState.value.isLoading)
        viewState.test {
            testDispatcher.scheduler.apply {
                advanceTimeBy(1000)
                assertThat(viewState.value.isLoading).isEqualTo(false)
                assertThat(viewState.value.data).isEqualTo(successResponse.data)
            }
            cancelAndConsumeRemainingEvents()
        }
    }

}