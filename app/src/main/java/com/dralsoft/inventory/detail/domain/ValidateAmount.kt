package com.dralsoft.inventory.detail.domain

import com.dralsoft.inventory.core.domain.ValidationResult
import javax.inject.Inject

class ValidateAmount @Inject constructor() {

    fun execute(amountStr: String): ValidationResult {
        return ValidationResult(false, "")
    }
}