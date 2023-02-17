package com.dralsoft.inventory.detail.domain

import androidx.core.text.isDigitsOnly
import com.dralsoft.inventory.core.domain.ValidationResult
import javax.inject.Inject

class ValidateName @Inject constructor() {

    fun execute(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(false, "Name cannot be empty")
        }

        if (name.length < 2) {
            return ValidationResult(false, "Name must have at least 2 chars")
        }

        return ValidationResult(true)
    }

}