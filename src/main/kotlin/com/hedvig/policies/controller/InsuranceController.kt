package com.hedvig.policies.controller

import com.hedvig.policies.dto.CreatePolicyRequest
import com.hedvig.policies.model.Insurance
import com.hedvig.policies.model.Policy
import com.hedvig.policies.service.InsuranceService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api")
class InsuranceController(private val service: InsuranceService) {

    @PostMapping("/insurances")
    fun startInsurance(@RequestBody req: CreatePolicyRequest): Insurance {
        return service.createInsurance(req)
    }

    @PostMapping("/insurances/{id}/policies")
    fun addPolicy(@PathVariable id: Long, @RequestBody req: CreatePolicyRequest): Policy {
        return service.addPolicy(id, req)
    }

    @GetMapping("/insurances")
    fun getInsurances(@RequestParam personalNumber: String): List<Insurance> =
        service.listInsurances(personalNumber)

    @GetMapping("/insurances/all")
    fun getAllInsurances(): List<Insurance> = service.getAllInsurances()

    @GetMapping("/insurances/{id}")
    fun getInsuranceById(@PathVariable id: Long): Insurance? = service.getInsuranceById(id)

    @DeleteMapping("/insurances/{id}")
    fun deleteInsurance(@PathVariable id: Long) = service.deleteInsurance(id)

    @GetMapping("/insurances/{id}/policy")
    fun getPolicyOnDate(@PathVariable id: Long, @RequestParam date: LocalDate): Policy? =
        service.getPolicyOnDate(id, date)
}
