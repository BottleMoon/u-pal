package me.project.upal.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
class Tag(
        tag: String
) {
    @Id
    val id: UUID = UUID.randomUUID()

    @Column
    var tag: String = tag
        private set
}