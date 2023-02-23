package com.dralsoft.inventory.detail.domain

import androidx.core.text.isDigitsOnly
import com.dralsoft.inventory.core.domain.ValidationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidateAmount @Inject constructor() {

    operator fun invoke(amountStr: String): Flow<ValidationResult> = flow {
        val ok = amountStr.isDigitsOnly() && amountStr.isNotEmpty()

        if (!ok) {
            emit(ValidationResult(false, "Amount must be a number"))
        } else {
            emit(ValidationResult(true))
        }

    }

}