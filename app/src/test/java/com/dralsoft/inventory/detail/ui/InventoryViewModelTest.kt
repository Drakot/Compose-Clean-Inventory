package com.dralsoft.inventory.detail.ui

import com.dralsoft.inventory.BaseTest
import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.core.data.local.mockInventoryResponse
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.detail.domain.DetailUseCases
import com.dralsoft.inventory.testFlow
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class InventoryViewModelTest : BaseTest() {
    private lateinit var sut: InventoryViewModel
    lateinit var useCases: DetailUseCases

    @Before
    fun setUp() {
        useCases = mockk()
        sut = InventoryViewModel(useCases)
    }

    @Test
    fun `on init with item id != 0 should call return ItemResponse`(): Unit = runTest {
        coEvery { useCases.inventoryUseCase(any()) } returns flow {
            emit(
                Resource.Success(
                    InventoryResponse(
                        mockInventoryResponse().data?.get(0)?.copy(id = 1)
                    )
                )
            )
        }

        val viewState = sut.viewState

        sut.submitIntent(InventoryIntent.Load(1))

        viewState.testFlow(this) {
            Truth.assertThat(viewState.value.isLoading).isEqualTo(false)
            Truth.assertThat(viewState.value.id).isEqualTo(1)
        }

    }
}