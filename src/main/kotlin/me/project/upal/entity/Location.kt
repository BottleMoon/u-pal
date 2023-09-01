package me.project.upal.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
class Location(
        latitude: Double,
        longitude: Double,
) {
    @Id
    val id: UUID = UUID.randomUUID();

    var latitude: Double = latitude
        private set
    var longitude: Double = longitude
        private set
}