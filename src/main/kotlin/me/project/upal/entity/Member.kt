package me.project.upal.entity

import jakarta.persistence.*
import java.util.*


@Entity
class Member(
        email: String,
        password: String,
        phoneNumber: String?,
        nickName: String,
        age: Int,
        country: Country
) {
    @Id
    @Column
    val id: UUID = UUID.randomUUID();

    @Column(nullable = false, unique = true)
    var email: String = email
        private set

    @Column
    var password: String = password
        private set

    @Column(unique = true)
    var phoneNumber: String? = phoneNumber
        private set

    @Column(nullable = false)
    var nickName: String = nickName
        private set

    @Column
    var age: Int = age
        private set

    @ManyToOne
    var country: Country = country
        private set

    @OneToOne
    lateinit var location: Location


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "member_role",
            joinColumns = [JoinColumn(name = "member_id")],
            inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Role> = mutableSetOf()
        private set
}