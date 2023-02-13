package com.dralsoft.inventory.core.ui

sealed class UiState<out T : Any> {
    object Empty : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Error(val errorMessage: String) : UiState<Nothing>()
    data class Success<T : Any>(val data: T) : UiState<T>()
}