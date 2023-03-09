package com.dralsoft.inventory.detail.domain

import com.dralsoft.inventory.BaseTest
import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.core.domain.Repository
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.response.InventoryItem
import io.mockk.coEvery
import io.mockk.coVerifyAll
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SaveInventoryUseCaseTest : BaseTest() {
    private lateinit var flowResponse: Flow<Resource.Success<InventoryResponse>>
    private lateinit var sut: SaveInventoryUseCase
    lateinit var repository: Repository

    @Before
    fun setUp() {
        repository = mockk()
        sut = SaveInventoryUseCase(repository)
        flowResponse = flow {
            emit(
                Resource.Success(
                    InventoryResponse()
                )
            )
        }

    }

    @Test
    fun `on invoke item with id = 0 should call createInventory`(): Unit = runTest {
        val item = mockk<InventoryItem>()
        every { item.id } returns 0L
        coEvery { repository.createInventory(any()) } returns flowResponse

        sut(item)

        coVerifyAll {
            repository.createInventory(any())
        }
    }

    @Test
    fun `on invoke item with id greater than 0 should call saveInventory`(): Unit = runTest {
        val item = mockk<InventoryItem>()
        every { item.id } returns 3L

        coEvery { repository.saveInventory(any()) } returns flowResponse

        sut(item)

        coVerifyAll {
            repository.saveInventory(any())
        }

    }
}