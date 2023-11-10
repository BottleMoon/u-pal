package me.project.upal.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.util.*

@Entity
class InterestTag(
        tag: Tag,
        interest: Interest
) {
    @Id
    val uuid: UUID = UUID.randomUUID()

    @ManyToOne(fetch = FetchType.LAZY)
    var tag: Tag = tag
        private set

    @ManyToOne(fetch = FetchType.LAZY)
    var interest: Interest = interest
        private set
}