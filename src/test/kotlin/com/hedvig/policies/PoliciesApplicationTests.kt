package com.hedvig.policies

import com.hedvig.policies.dto.CreateInsuranceRequest
import com.hedvig.policies.model.Policy
import com.hedvig.policies.service.InsuranceService
import com.hedvig.policies.service.PremiumCalculator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import org.mockito.Mockito.`when`

@SpringBootTest(properties = [
	"spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
	"spring.datasource.driverClassName=org.h2.Driver",
	"spring.datasource.username=sa",
	"spring.datasource.password=",
	"spring.jpa.hibernate.ddl-auto=create-drop",
	"spring.liquibase.enabled=false"
])
@Transactional
class InsuranceServiceTests {

	@Autowired
	private lateinit var insuranceService: InsuranceService

	@MockBean
	private lateinit var premiumCalculator: PremiumCalculator

	@Test
	fun `create insurance successfully`() {
		`when`(premiumCalculator.calculateMonthly("11135")).thenReturn(100.0)

		val request = CreateInsuranceRequest(
			personalNumber = "19900101-1234",
			address = "Kungsgatan 16",
			postalCode = "11135",
			startDate = LocalDate.now()
		)

		val insurance = insuranceService.createInsurance(request)

		assertNotNull(insurance.id)
		assertEquals("19900101-1234", insurance.personalNumber)
		assertEquals(1, insurance.policies.size)
		assertEquals(100.0, insurance.policies.first().premium)
	}

	@Test
	fun `get policy on specific date`() {
		`when`(premiumCalculator.calculateMonthly("11135")).thenReturn(100.0)

		val startDate = LocalDate.now().minusDays(5)
		val request = CreateInsuranceRequest(
			personalNumber = "19900101-1234",
			address = "Kungsgatan 16",
			postalCode = "11135",
			startDate = startDate
		)
		val insurance = insuranceService.createInsurance(request)

		val policy = insuranceService.getPolicyOnDate(insurance.id!!, startDate.plusDays(2))
		assertNotNull(policy)
		assertEquals(startDate, policy!!.startDate)
	}

	@Test
	fun `list insurances by personal number`() {
		`when`(premiumCalculator.calculateMonthly("11135")).thenReturn(100.0)

		val request = CreateInsuranceRequest(
			personalNumber = "19900101-1234",
			address = "Kungsgatan 16",
			postalCode = "11135",
			startDate = LocalDate.now()
		)
		insuranceService.createInsurance(request)

		val insurances = insuranceService.listInsurances("19900101-1234")
		assertEquals(1, insurances.size)
		assertEquals("19900101-1234", insurances.first().personalNumber)
	}

	@Test
	fun `delete insurance removes it from database`() {
		`when`(premiumCalculator.calculateMonthly("11135")).thenReturn(100.0)

		val request = CreateInsuranceRequest(
			personalNumber = "19900101-1234",
			address = "Kungsgatan 16",
			postalCode = "11135",
			startDate = LocalDate.now()
		)
		val insurance = insuranceService.createInsurance(request)

		// Delete the insurance
		insuranceService.deleteInsurance(insurance.id!!)

		// Check that the deleted insurance no longer exists
		val deletedInsurance = insuranceService.getInsuranceById(insurance.id!!)
		assertNull(deletedInsurance)
	}
}
