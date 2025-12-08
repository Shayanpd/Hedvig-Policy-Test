package com.hedvig.policies.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class CreateInsuranceRequest(
    @field:NotBlank
    val personalNumber: String,

    @field:NotBlank
    val address: String,

    @field:NotBlank
    @field:Pattern(regexp = "\\d{5}", message = "Postal code must be 5 digits")
    val postalCode: String,

    @field:NotNull
    val startDate: LocalDate
)