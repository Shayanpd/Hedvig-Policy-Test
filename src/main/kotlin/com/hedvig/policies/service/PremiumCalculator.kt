package com.hedvig.policies.service

import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class PremiumCalculator {

    // Simple deterministic strategy: base 500 + last digit of postal code * 10
    fun calculateMonthly(postalCode: String): Double {
        val lastDigit = postalCode.last().digitToIntOrNull() ?: 0
        return (500 + lastDigit * 10).toDouble()
    }
}