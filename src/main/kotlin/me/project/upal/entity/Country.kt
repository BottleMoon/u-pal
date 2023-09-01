package me.project.upal.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Country
(id: String,
 name: String) {
    @Id
    var id: String = id
        private set

    @Column
    var name: String = name
        private set
}