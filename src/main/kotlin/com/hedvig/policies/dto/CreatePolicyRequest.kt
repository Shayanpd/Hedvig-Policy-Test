package com.hedvig.policies.dto

import java.time.LocalDate

data class CreatePolicyRequest(
    val personalNumber: String,
    val address: String,
    val postalCode: String,
    val startDate: LocalDate
)