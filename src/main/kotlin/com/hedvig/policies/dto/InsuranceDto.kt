package com.hedvig.policies.dto

import java.time.LocalDate

data class PolicyDto(
    val id: Long,
    val address: String,
    val postalCode: String,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val premium: Double
)

data class InsuranceDto(
    val id: Long,
    val personalNumber: String,
    val policies: List<PolicyDto>
)