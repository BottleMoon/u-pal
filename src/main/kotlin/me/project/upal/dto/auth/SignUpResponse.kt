package me.project.upal.dto.auth

import me.project.upal.entity.Member
import java.util.*

data class SignUpResponse(
        var id: UUID,
        var email: String,
        var phoneNumber: String?,
        var nickName: String,
        var age: Int
) {

    constructor(member: Member) : this(
            id = member.id,
            email = member.email,
            phoneNumber = member.phoneNumber,
            nickName = member.nickName,
            age = member.age
    )

}