package com.dralsoft.inventory.detail.ui

import com.dralsoft.inventory.BaseTest
import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.core.data.ErrorResponse
import com.dralsoft.inventory.core.data.local.mockInventoryResponse
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.detail.domain.DetailUseCases
import com.dralsoft.inventory.testFlow
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any

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

    @Test
    fun `on save must submit OnSaveSuccess & hide loading`(): Unit = runTest {
        coEvery { useCases.saveInventoryUseCase(any()) } returns flow {
            emit(
                Resource.Success(
                    InventoryResponse(
                        any()
                    )
                )
            )
        }

        val viewState = sut.viewState

        sut.submitIntent(InventoryIntent.Save)

        viewState.testFlow(this) {
            assertThat(viewState.value.isLoading).isEqualTo(false)
          //assertThat(sut.submitSingleEvent(InventorySingleEvent.OnLoadSuccess(mockk()))).isEqualTo(Unit)
            assertThat(sut.submitSingleEvent(InventorySingleEvent.OnLoadSuccess(mockk()))).isEqualTo(Unit)

            coVerify { useCases.saveInventoryUseCase(any())}
            //verify { sut.submitSingleEvent(ofType(InventorySingleEvent.OnSaveSuccess::class))}
        }
    }

    @Test
    fun `on save must submit singleEvent Error & hide loading`(): Unit = runTest {
        val errorResponse = ErrorResponse(
            any()
        )
        coEvery { useCases.saveInventoryUseCase(any()) } returns flow {
            emit(
                Resource.Error(errorResponse)
            )
        }

        val viewState = sut.viewState

        sut.submitIntent(InventoryIntent.Save)

        viewState.testFlow(this) {
            assertThat(viewState.value.isLoading).isEqualTo(false)
            assertThat(sut.submitSingleEvent(InventorySingleEvent.Error("any()"))).isEqualTo(Unit)
        }
    }
}