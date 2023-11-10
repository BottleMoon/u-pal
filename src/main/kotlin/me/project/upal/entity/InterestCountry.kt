package me.project.upal.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.util.*

@Entity
class InterestCountry(
        country: Country,
        interest: Interest
) {
    @Id
    val uuid: UUID = UUID.randomUUID()

    @ManyToOne(fetch = FetchType.LAZY)
    var country: Country = country
        private set

    @ManyToOne(fetch = FetchType.LAZY)
    var interest: Interest = interest
        private set
}