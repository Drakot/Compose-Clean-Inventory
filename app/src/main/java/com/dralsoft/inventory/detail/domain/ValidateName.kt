package com.dralsoft.inventory.detail.domain

import androidx.core.text.isDigitsOnly
import com.dralsoft.inventory.core.domain.ValidationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidateName @Inject constructor() {

    fun execute(name: String): Flow<ValidationResult> = flow {
        var result: ValidationResult? = null
        if (name.isBlank()) {
            result = ValidationResult(false, "Name cannot be empty")
        }

        if (name.length < 2) {
            result = ValidationResult(false, "Name must have at least 2 chars")
        }

        if (result == null) {
            emit(ValidationResult(true))
        } else {
            emit(result)
        }
    }

}