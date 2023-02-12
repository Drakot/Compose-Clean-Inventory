package com.dralsoft.inventory.list.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dralsoft.inventory.list.domain.ListInventoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListInventoryViewModel @Inject constructor(private val useCase: ListInventoryUseCase) : ViewModel() {

    private val _state = mutableStateOf(ListState())
    val state: State<ListState> = _state

    init {

    }

    fun onRefresh() {

    }

    fun onAdd() {

    }

    fun onEdit() {

    }
}
