package com.hedvig.policies.repository

import com.hedvig.policies.model.Policy
import org.springframework.data.jpa.repository.JpaRepository

interface PolicyRepository : JpaRepository<Policy, Long>