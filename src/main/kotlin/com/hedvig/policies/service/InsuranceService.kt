package com.hedvig.policies.service

import com.hedvig.policies.dto.CreatePolicyRequest
import com.hedvig.policies.model.Insurance
import com.hedvig.policies.model.Policy
import com.hedvig.policies.repository.InsuranceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class InsuranceService(
    private val insuranceRepository: InsuranceRepository,
    private val premiumCalculator: PremiumCalculator
) {

    @Transactional
    fun createInsurance(req: CreatePolicyRequest): Insurance {
        val insurance = Insurance(personalNumber = req.personalNumber)
        val premium = premiumCalculator.calculateMonthly(req.postalCode)
        val policy = Policy(
            insurance = insurance,
            address = req.address,
            postalCode = req.postalCode,
            startDate = req.startDate,
            premium = premium
        )
        insurance.policies.add(policy)
        return insuranceRepository.save(insurance)
    }

    fun getAllInsurances(): List<Insurance> = insuranceRepository.findAll()

    fun getInsuranceById(id: Long): Insurance? = insuranceRepository.findById(id).orElse(null)

    fun deleteInsurance(id: Long) {
        val insurance = insuranceRepository.findById(id)
            .orElseThrow { RuntimeException("Insurance not found with id $id") }
        insuranceRepository.delete(insurance)
    }

    @Transactional
    fun addPolicy(insuranceId: Long, req: CreatePolicyRequest): Policy {
        val insurance = insuranceRepository.findById(insuranceId)
            .orElseThrow { RuntimeException("Insurance not found") }

        // End current active policy
        val activePolicy = insurance.policies.find { it.endDate == null }
        activePolicy?.endDate = req.startDate.minusDays(1)

        val premium = premiumCalculator.calculateMonthly(req.postalCode)
        val newPolicy = Policy(
            insurance = insurance,
            address = req.address,
            postalCode = req.postalCode,
            startDate = req.startDate,
            premium = premium
        )
        insurance.policies.add(newPolicy)
        insuranceRepository.save(insurance)
        return newPolicy
    }

    fun listInsurances(personalNumber: String): List<Insurance> =
        insuranceRepository.findByPersonalNumber(personalNumber)

    fun getPolicyOnDate(insuranceId: Long, date: LocalDate): Policy? {
        val insurance = insuranceRepository.findById(insuranceId)
            .orElseThrow { RuntimeException("Insurance not found") }

        return insurance.policies.find { policy ->
            val afterStart = !date.isBefore(policy.startDate)
            val beforeEnd = policy.endDate == null || !date.isAfter(policy.endDate)
            afterStart && beforeEnd
        }
    }
}
