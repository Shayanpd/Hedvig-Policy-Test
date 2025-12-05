package com.hedvig.policies.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
data class Insurance(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val personalNumber: String,
    @JsonManagedReference
    @OneToMany(mappedBy = "insurance", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val policies: MutableList<Policy> = mutableListOf()
)
