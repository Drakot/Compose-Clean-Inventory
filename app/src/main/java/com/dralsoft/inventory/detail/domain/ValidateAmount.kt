package com.dralsoft.inventory.detail.domain

import androidx.core.text.isDigitsOnly
import com.dralsoft.inventory.core.domain.ValidationResult
import javax.inject.Inject

class ValidateAmount @Inject constructor() {

    fun execute(amountStr: String): ValidationResult {
        val ok = amountStr.isDigitsOnly()

        if (!ok) {
            return ValidationResult(false, "Amount must be a number")
        }

        return ValidationResult(true)
    }

}