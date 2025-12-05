package com.hedvig.policies.controller

import com.hedvig.policies.dto.CreateInsuranceRequest
import com.hedvig.policies.dto.UpdatePolicyRequest
import com.hedvig.policies.dto.toDto
import com.hedvig.policies.model.Insurance
import com.hedvig.policies.model.Policy
import com.hedvig.policies.service.InsuranceService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api")
class InsuranceController(private val service: InsuranceService) {

    @PostMapping("/insurances")
    fun startInsurance(@Valid @RequestBody req: CreateInsuranceRequest) =
        service.createInsurance(req).toDto()

    @PostMapping("/insurances/{id}/policies")
    fun addPolicy(
        @PathVariable id: Long,
        @Valid @RequestBody req: UpdatePolicyRequest
    ) = service.addPolicy(id, req).toDto()

    @GetMapping("/insurances")
    fun getInsurances(@RequestParam personalNumber: String) =
        service.listInsurances(personalNumber).map { it.toDto() }

    @GetMapping("/insurances/all")
    fun getAllInsurances() =
        service.getAllInsurances().map { it.toDto() }

    @GetMapping("/insurances/{id}")
    fun getInsuranceById(@PathVariable id: Long) =
        service.getInsuranceById(id)?.toDto()

    @DeleteMapping("/insurances/{id}")
    fun deleteInsurance(@PathVariable id: Long) =
        service.deleteInsurance(id)

    @GetMapping("/insurances/{id}/policy")
    fun getPolicyOnDate(@PathVariable id: Long, @RequestParam date: LocalDate) =
        service.getPolicyOnDate(id, date)?.toDto()
}

