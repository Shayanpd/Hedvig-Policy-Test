package com.hedvig.policies.controller

import com.hedvig.policies.dto.*
import com.hedvig.policies.model.Insurance
import com.hedvig.policies.model.Policy
import com.hedvig.policies.service.InsuranceService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api")
class InsuranceController(private val service: InsuranceService) {

    @PostMapping("/insurances")
    fun startInsurance(@Valid @RequestBody req: CreateInsuranceRequest): ResponseEntity<InsuranceDto> {
        val insurance = service.createInsurance(req).toDto()
        return ResponseEntity.status(HttpStatus.CREATED).body(insurance)
    }

    @PostMapping("/insurances/{id}/policies")
    fun addPolicy(
        @PathVariable id: Long,
        @Valid @RequestBody req: UpdatePolicyRequest
    ): ResponseEntity<PolicyDto> {
        val policy = service.addPolicy(id, req).toDto()
        return ResponseEntity.status(HttpStatus.CREATED).body(policy)
    }

    @GetMapping("/insurances")
    fun getInsurances(@RequestParam personalNumber: String): ResponseEntity<List<InsuranceDto>> {
        val insurances = service.listInsurances(personalNumber).map { it.toDto() }
        return if (insurances.isEmpty()) ResponseEntity.noContent().build()
        else ResponseEntity.ok(insurances)
    }

    @GetMapping("/insurances/all")
    fun getAllInsurances(): ResponseEntity<List<InsuranceDto>> {
        val insurances = service.getAllInsurances().map { it.toDto() }
        return if (insurances.isEmpty()) ResponseEntity.noContent().build()
        else ResponseEntity.ok(insurances)
    }

    @GetMapping("/insurances/{id}")
    fun getInsuranceById(@PathVariable id: Long): ResponseEntity<InsuranceDto> {
        val insurance = service.getInsuranceById(id)?.toDto()
            ?: return ResponseEntity.notFound().build() // 404 if null
        return ResponseEntity.ok(insurance) // 200 OK
    }

    @DeleteMapping("/insurances/{id}")
    fun deleteInsurance(@PathVariable id: Long): ResponseEntity<Void> {
        service.deleteInsurance(id)
        return ResponseEntity.noContent().build() // 204 No Content
    }

    @GetMapping("/insurances/{id}/policy")
    fun getPolicyOnDate(@PathVariable id: Long, @RequestParam date: LocalDate): ResponseEntity<PolicyDto> {
        val policy = service.getPolicyOnDate(id, date)?.toDto()
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(policy)
    }
}

