package com.dralsoft.inventory.detail.domain

import com.dralsoft.inventory.core.domain.ValidationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidateName @Inject constructor() {

    operator fun invoke(name: String): Flow<ValidationResult> = flow {
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