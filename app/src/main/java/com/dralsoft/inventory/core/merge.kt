package com.dralsoft.inventory.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.merge(otherFlow: Flow<T>) = flow {
    this@merge.collect { value ->
        emit(value)
    }
    otherFlow.collect { value ->
        emit(value)
    }
}