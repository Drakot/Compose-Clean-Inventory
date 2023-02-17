package com.dralsoft.inventory.core.domain

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)