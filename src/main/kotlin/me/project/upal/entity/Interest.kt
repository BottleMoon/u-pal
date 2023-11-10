package me.project.upal.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import java.util.*

@Entity
class Interest(
        member: Member
) {
    @Id
    val uuid: UUID = UUID.randomUUID()

    @OneToOne
    var memeber: Member = member
}