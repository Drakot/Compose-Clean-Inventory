package com.dralsoft.inventory.list.ui

import com.dralsoft.inventory.BaseTest
import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.list.data.local.mockInventoryResponse
import com.dralsoft.inventory.list.domain.DeleteInventoryUseCase
import com.dralsoft.inventory.list.domain.ListInventoryUseCase
import com.dralsoft.inventory.list.domain.ListUseCases
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class ListInventoryViewModelTest : BaseTest() {

    lateinit var listUseCases: ListUseCases
    private lateinit var viewModel: ListInventoryViewModel

    val successResponse = mockInventoryResponse()

    private val listInventoryUseCase: ListInventoryUseCase = mock {
        onBlocking { invoke("") } doReturn flow { Resource.Success(successResponse) }
    }

    @Before
    fun setUp() {
        listUseCases = ListUseCases(
            listInventoryUseCase = listInventoryUseCase,
            deleteInventoryUseCase = mock(DeleteInventoryUseCase::class.java)
        )
        viewModel = ListInventoryViewModel(listUseCases)
    }

    @Test
    fun `on init should load the list inventory`(): Unit = runBlocking {
        verify(listUseCases.listInventoryUseCase, times(1)).invoke("")
        assertThat(viewModel.viewState.value.data).isEqualTo(successResponse.data)
    }

}