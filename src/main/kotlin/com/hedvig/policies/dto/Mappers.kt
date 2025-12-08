package com.hedvig.policies.dto

import com.hedvig.policies.model.Insurance
import com.hedvig.policies.model.Policy

fun Policy.toDto() = PolicyDto(
    id = id,
    address = address,
    postalCode = postalCode,
    startDate = startDate,
    endDate = endDate,
    premium = premium
)

fun Insurance.toDto() = InsuranceDto(
    id = id,
    personalNumber = personalNumber,
    policies = policies.map { it.toDto() }
)
