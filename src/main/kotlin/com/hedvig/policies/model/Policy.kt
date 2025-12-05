package com.hedvig.policies.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class Policy(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "insurance_id")
    val insurance: Insurance,

    val address: String,
    val postalCode: String,

    val startDate: LocalDate,
    var endDate: LocalDate? = null,

    val premium: Int
)